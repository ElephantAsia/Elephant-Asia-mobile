package fr.elephantasia.elephantasia.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.StackingBehavior;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import fr.elephantasia.elephantasia.R;
import fr.elephantasia.elephantasia.adapter.ViewPagerAdapter;
import fr.elephantasia.elephantasia.database.Database;
import fr.elephantasia.elephantasia.dialogs.PickImageDialog;
import fr.elephantasia.elephantasia.dialogs.PickImageDialogBuilder;
import fr.elephantasia.elephantasia.databinding.AddElephantLocationDialogFragmentBinding;
import fr.elephantasia.elephantasia.fragment.addElephant.AddElephantDescriptionFragment;
import fr.elephantasia.elephantasia.fragment.addElephant.AddElephantDocumentFragment;
import fr.elephantasia.elephantasia.fragment.addElephant.AddElephantLocationFragment;
import fr.elephantasia.elephantasia.fragment.addElephant.AddElephantOwnershipFragment;
import fr.elephantasia.elephantasia.fragment.addElephant.AddElephantParentageFragment;
import fr.elephantasia.elephantasia.fragment.addElephant.AddElephantProfilFragment;
import fr.elephantasia.elephantasia.fragment.addElephant.AddElephantRegistrationFragment;
import fr.elephantasia.elephantasia.interfaces.AddElephantInterface;
import fr.elephantasia.elephantasia.utils.ElephantInfo;
import fr.elephantasia.elephantasia.utils.ImageUtil;
import fr.elephantasia.elephantasia.utils.StaticTools;
import fr.elephantasia.elephantasia.utils.UserInfo;

import static android.content.ContentValues.TAG;

public class AddElephantActivity extends AppCompatActivity implements AddElephantInterface {

    private static final int REQUEST_ADD_OWNER = 1;
    private static final int REQUEST_SET_FATHER = 2;
    private static final int REQUEST_SET_MOTHER = 3;
    private static final int REQUEST_ADD_CHILDREN = 4;
    private static final int REQUEST_CAPTURE_PHOTO = 5;
    private static final int REQUEST_IMPORT_PHOTO = 6;
    private static final int REQUEST_PLACE_PICKER_PROFIL = 7;
    private static final int REQUEST_PLACE_PICKER_REGISTRATION = 8;

    // Result code
    public static final int RESULT_DRAFT = 2;
    public static final int RESULT_VALIDATE = 3;

    // Fragment index
    private static final int FRAGMENT_OWNERSHIP_IDX = 3;
    private static final int FRAGMENT_PARENTAGE_IDX = 4;
    private static final int FRAGMENT_DOCUMENTS_IDX = 5;

    //Fragment
    private AddElephantProfilFragment profilFragment;
    private AddElephantRegistrationFragment registrationFragment;

    private Database database;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton fabNext;

    private ViewPagerAdapter adapter;

    private ElephantInfo elephantInfo;

    private PickImageDialog pickImageDialog;

    public AddElephantActivity() {
        elephantInfo = new ElephantInfo();
        profilFragment = new AddElephantProfilFragment();
        registrationFragment = new AddElephantRegistrationFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_elephant_activity);

        final View activityRootView = findViewById(R.id.add_elephant_activity);

        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                boolean keyboardIsUp = StaticTools.keyboardIsDisplay(activityRootView);
                FloatingActionButton button = (FloatingActionButton) findViewById(R.id.add_elephant_fab);

                if (button != null && keyboardIsUp) {
                    button.hide();
                } else if (button != null) {
                    button.show();
                }
            }
        });

        fabNext = (FloatingActionButton)findViewById(R.id.add_elephant_fab);
        fabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextPage();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        database = new Database(this);
        database.open();
    }

    @Override
    public boolean onSupportNavigateUp() {
        confirmFinish();
        return true;
    }

    @Override
    public void onBackPressed() {
        confirmFinish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ADD_OWNER) {
            if (resultCode == RESULT_OK && data != null) {
                UserInfo user = data.getParcelableExtra(SelectOwnerActivity.EXTRA_RESULT_USER_SELECTED);
                refreshOwner(user);
            }
        } else if (requestCode == REQUEST_SET_FATHER) {
            if (resultCode == RESULT_OK && data != null) {
                ElephantInfo info = data.getParcelableExtra(SearchActivity.EXTRA_RESULT);
                refreshFather(info);
            }
        } else if (requestCode == REQUEST_SET_MOTHER) {
            if (resultCode == RESULT_OK && data != null) {
                ElephantInfo info = data.getParcelableExtra(SearchActivity.EXTRA_RESULT);
                refreshMother(info);
            }
        } else if (requestCode == REQUEST_ADD_CHILDREN) {
            if (resultCode == RESULT_OK && data != null) {
                ElephantInfo info = data.getParcelableExtra(SearchActivity.EXTRA_RESULT);
                refreshChildren(info);
            }
        } else if (requestCode == REQUEST_CAPTURE_PHOTO) {
            if (resultCode == RESULT_OK) {
                addDocument(Uri.fromFile(ImageUtil.getCapturePhotoFile(this)));
            }
        } else if (requestCode == REQUEST_IMPORT_PHOTO) {
            if (resultCode == RESULT_OK && data != null) {
                addDocument(data.getData());
        } else if (requestCode == REQUEST_PLACE_PICKER_PROFIL) {
            if (resultCode == RESULT_OK) {
                //TODO: recuperer la province, le district et la ville
                Place selectedPlace = PlacePicker.getPlace(this, data);
                profilFragment.setLocation(selectedPlace.getAddress().toString());
            }
        } else if (requestCode == REQUEST_PLACE_PICKER_REGISTRATION) {
            if (resultCode == RESULT_OK) {
                //TODO: recuperer la province, le district et la ville
                Place selectedPlace = PlacePicker.getPlace(this, data);
                registrationFragment.setLocation(selectedPlace.getAddress().toString());
            }
        }
    }

    @Override
    public void nextPage() {
        if (viewPager.getCurrentItem() == viewPager.getAdapter().getCount() - 1) {
            viewPager.setCurrentItem(0);
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (pickImageDialog != null) {
                    startActivityForResult(pickImageDialog.getIntent(0), pickImageDialog.getRequestCode(0));
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_elephant_more_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_elephant_menu_draft) {
            saveElephant(ElephantInfo.State.DRAFT);
            return true;
        } else if (item.getItemId() == R.id.add_elephant_menu_validate) {
            saveElephant(ElephantInfo.State.LOCAL);
            return true;
        }
        return false;
    }

    private boolean checkMandatoryFields() {
        if (elephantInfo.name.isEmpty()) {
            profilFragment.setNameError();
            Toast.makeText(this, R.string.name_required, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (elephantInfo.sex == ElephantInfo.Gender.UNKNOWN) {
            profilFragment.setSexError();
            Toast.makeText(this, R.string.sex_required, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    public void saveElephant(ElephantInfo.State state) {
        if (checkMandatoryFields()) {
            int result = state == ElephantInfo.State.DRAFT ? RESULT_DRAFT : RESULT_VALIDATE;
            elephantInfo.state = state;
            elephantInfo.displayAttr();
            database.insert(elephantInfo);
            setResult(result);
            finish();
        }
    }

    public ElephantInfo getElephantInfo() {
        return this.elephantInfo;
    }

    /**
     * Prevent user to cancel activity without saving his current Elephant.
     * Happens only if some data have been set.
     * Recursive function to avoid duplicated code.
     * @param confirm Show the pop up
     */
    private void confirmFinish() {

        if (elephantInfo.isEmpty()) {
            setResult(RESULT_CANCELED);
            finish();
        } else {
            new AlertDialog.Builder(this)
                    .setMessage(R.string.put_drafts)
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            setResult(RESULT_CANCELED);
                            finish();
                        }
                    })
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (checkMandatoryFields()) {
                                elephantInfo.state = ElephantInfo.State.DRAFT;
                                database.insert(elephantInfo);
                                setResult(RESULT_DRAFT);
                                finish();
                            }
                        }
                    })
                    .show();
        }
    }

    @Override
    public void addOwner() {
        Intent intent = new Intent(this, SelectOwnerActivity.class);
        startActivityForResult(intent, REQUEST_ADD_OWNER);
    }

    @Override
    public void setFather() {
        Intent intent = new Intent(this, SearchActivity.class);
        SearchActivity.setMode(intent, SearchActivity.Mode.PICK_RESULT);
        startActivityForResult(intent, REQUEST_SET_FATHER);
    }

    @Override
    public void setMother() {
        Intent intent = new Intent(this, SearchActivity.class);
        SearchActivity.setMode(intent, SearchActivity.Mode.PICK_RESULT);
        startActivityForResult(intent, REQUEST_SET_MOTHER);
    }

    @Override
    public void addChildren() {
        Intent intent = new Intent(this, SearchActivity.class);
        SearchActivity.setMode(intent, SearchActivity.Mode.PICK_RESULT);
        startActivityForResult(intent, REQUEST_ADD_CHILDREN);
    }

    @Override
    public void onElephantClick(ElephantInfo elephant) {
        Intent intent = new Intent(this, ConsultationActivity.class);
        ConsultationActivity.setElephant(intent, elephant);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void onAddDocumentClick() {
        buildPickDocumentDialog();
    }

    private void refreshOwner(UserInfo user) {
        AddElephantOwnershipFragment fragment = (AddElephantOwnershipFragment)adapter.getItem(FRAGMENT_OWNERSHIP_IDX);

        elephantInfo.addOwner(user.id);
        fragment.refreshOwner(user);
    }

    private void refreshFather(ElephantInfo info) {
        AddElephantParentageFragment fragment = (AddElephantParentageFragment)adapter.getItem(FRAGMENT_PARENTAGE_IDX);

        elephantInfo.father = info.id.toString();
        fragment.refreshFather(info);
    }

    private void refreshMother(ElephantInfo info) {
        AddElephantParentageFragment fragment = (AddElephantParentageFragment)adapter.getItem(FRAGMENT_PARENTAGE_IDX);

        elephantInfo.mother = info.id.toString();
        fragment.refreshMother(info);
    }

    private void refreshChildren(ElephantInfo info) {
        AddElephantParentageFragment fragment = (AddElephantParentageFragment)adapter.getItem(FRAGMENT_PARENTAGE_IDX);

        elephantInfo.addChildren(info.id);
        fragment.refreshChildren(info);
    }

    private void addDocument(Uri uri) {
        AddElephantDocumentFragment fragment = (AddElephantDocumentFragment)adapter.getItem(FRAGMENT_DOCUMENTS_IDX);
        String path = ImageUtil.createImageFileFromUri(this, uri);

        if (path != null) {
            Log.i("add_photo", "aucune erreur");
            fragment.addDocument(path);
        } else {
            Toast.makeText(getApplicationContext(), "Error on adding document", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(profilFragment, getString(R.string.profil));
        adapter.addFragment(registrationFragment, getString(R.string.registration));
        adapter.addFragment(new AddElephantDescriptionFragment(), getString(R.string.description));
        adapter.addFragment(new AddElephantOwnershipFragment(), getString(R.string.ownership));
        adapter.addFragment(new AddElephantParentageFragment(), getString(R.string.parentage));
        adapter.addFragment(new AddElephantDocumentFragment(), getString(R.string.documents));
        adapter.addFragment(new AddElephantLocationFragment(), getString(R.string.location));
        viewPager.setAdapter(adapter);
    }

    public void showDialogFragment(DialogFragment dialog) {
        dialog.show(getSupportFragmentManager(), "Date");
    }

    private void buildPickDocumentDialog() {
        pickImageDialog = new PickImageDialogBuilder(this)
                .build()
                .setListener(new PickImageDialog.Listener() {
                    @Override
                    public void execute(Intent intent, int requestCode) {
                        if (requestCode == REQUEST_CAPTURE_PHOTO) {
                            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(AddElephantActivity.this, new String[]{Manifest.permission.CAMERA}, 0);
                            } else {
                                startActivityForResult(intent, requestCode);
                            }
                        } else {
                            startActivityForResult(intent, requestCode);
                        }
                    }
                })
                .setCaptureCode(REQUEST_CAPTURE_PHOTO)
                .setImportCode(REQUEST_IMPORT_PHOTO)
                .load();
        pickImageDialog.show();
    }

    public void showLocationDialog(final int fragmentName) {
        new MaterialDialog.Builder(this)
                .title(R.string.set_location_from_map)
                .positiveText(R.string.FROM_MAP)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        showLocationPicker(fragmentName);
                    }
                })
                .negativeText(R.string.MANUALLY)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        showLocationInputs(fragmentName);
                    }
                })
                .stackingBehavior(StackingBehavior.ALWAYS)
                .show();
    }

    private void showLocationPicker(final int fragmentName) {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        LatLng burmaNorth = new LatLng(16.193669, 95.229859);
        LatLng burmaSouth = new LatLng(28.279449, 97.576320);
        LatLngBounds bound = new LatLngBounds(burmaNorth, burmaSouth);

        builder.setLatLngBounds(bound);

        try {
            if (fragmentName == R.string.profil) {
                startActivityForResult(builder.build(this), REQUEST_PLACE_PICKER_PROFIL);
            } else if (fragmentName == R.string.registration) {
                startActivityForResult(builder.build(this), REQUEST_PLACE_PICKER_REGISTRATION);
            }
        } catch(Exception e){
            Log.e(TAG, e.getMessage());
        }
    }

    private void showLocationInputs(final int fragmentName) {
        AddElephantLocationDialogFragmentBinding binding = DataBindingUtil.inflate(
                getLayoutInflater(),
                R.layout.add_elephant_location_dialog_fragment,
                ((ViewGroup) getWindow().getDecorView().getRootView()),
                false);

        final View view = binding.getRoot();
        String title = "";

        if (fragmentName == R.string.profil) {
            binding.setProvince(elephantInfo.birthProvince);
            binding.setDistrict(elephantInfo.birthDistrict);
            binding.setCity(elephantInfo.birthCity);
            title = getString(R.string.set_birth_location);
        } else if (fragmentName == R.string.registration) {
            binding.setProvince(elephantInfo.regProvince);
            binding.setDistrict(elephantInfo.regDistrict);
            binding.setCity(elephantInfo.regCity);
            title = getString(R.string.set_registration_location);
        }

        StaticTools.setupHideKeyboardListener(view, this);
        new MaterialDialog.Builder(this)
                .title(title)
                .positiveText(R.string.OK)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        String province = ((EditText) view.findViewById(R.id.province)).getText().toString();
                        String district = ((EditText) view.findViewById(R.id.district)).getText().toString();
                        String city = ((EditText) view.findViewById(R.id.city)).getText().toString();

                        if (fragmentName == R.string.profil) {
                            profilFragment.setLocation(province, district, city);
                        } else if (fragmentName == R.string.registration) {
                            registrationFragment.setLocation(province, district, city);
                        }
                    }
                })
                .negativeText(R.string.CANCEL)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {}
                })
                .customView(view, true)
                .show();

    }
}
