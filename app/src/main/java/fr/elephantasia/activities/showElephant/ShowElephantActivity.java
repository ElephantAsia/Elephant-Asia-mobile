package fr.elephantasia.activities.showElephant;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.StackingBehavior;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.elephantasia.R;
import fr.elephantasia.activities.addDocument.AddDocumentActivity;
import fr.elephantasia.activities.manageElephant.ManageElephantActivity;
import fr.elephantasia.activities.showDocument.ShowDocumentActivity;
import fr.elephantasia.activities.showElephant.fragment.ShowChildrenFragment;
import fr.elephantasia.activities.showElephant.fragment.ShowDocumentFragment;
import fr.elephantasia.activities.showElephant.fragment.ShowOverviewFragment;
import fr.elephantasia.activities.showElephant.fragment.ShowParentageFragment;
import fr.elephantasia.adapter.DocumentAdapter;
import fr.elephantasia.adapter.ViewPagerAdapter;
import fr.elephantasia.database.RealmDB;
import fr.elephantasia.database.model.Document;
import fr.elephantasia.database.model.Elephant;
import fr.elephantasia.databinding.ShowElephantActivityBinding;
import io.realm.Realm;

import static fr.elephantasia.activities.manageElephant.ManageElephantActivity.RESULT_DRAFT;
import static fr.elephantasia.activities.manageElephant.ManageElephantActivity.RESULT_VALIDATE;
import static fr.elephantasia.activities.searchElephant.SearchElephantActivity.EXTRA_ELEPHANT_ID;
import static fr.elephantasia.database.model.Elephant.ID;

/**
 ** STEPH NOTE : That would be nice if we make a class pour the fab menu. I will do that if we use fab menu again in an other part of the app.
 **/
public class ShowElephantActivity extends AppCompatActivity implements DocumentAdapter.Listener {

  public static final String EXTRA_EDIT_ELEPHANT_ID = "EXTRA_EDIT_ELEPHANT_ID";

  private static final int REQUEST_ELEPHANT_EDITED = 0;
	private static final int REQUEST_ADD_DOCUMENT = 1;

  // View Binding
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.parent_layout) LinearLayout parentLayout;
  @BindView(R.id.tabs) TabLayout tabLayout;
  @BindView(R.id.viewpager) ViewPager viewPager;
  @BindView(R.id.toolbar_title) TextView toolbarTitle;
  @BindView(R.id.fabMenuTrigger) FloatingActionButton fabMenuTrigger;

	@BindView(R.id.fabMenuBackgroundMask) View fabMenuBackgroundMask;
  @BindView(R.id.minifab_menu) LinearLayout fabMenu;
	@BindView(R.id.minifab_edit) FloatingActionButton fabEdit;
	@BindView(R.id.minifab_adddocument) FloatingActionButton fabAddDocument;
	@BindView(R.id.minifab_addnote) FloatingActionButton fabAddNote;
	@BindView(R.id.minifab_addlocation) FloatingActionButton fabAddLocation;
	@BindView(R.id.minifab_addconsultation) FloatingActionButton fabAddConsultation;
	@BindView(R.id.minifab_delete) FloatingActionButton fabDelete;

	private Animation miniFabOpenAnimation;
  private Animation miniFabCloseAnimation;
  private AlphaAnimation backgroundInAnimation;
  private AlphaAnimation backgroundOutAnimation;
  private boolean fabIsOpen = false;

  private ShowDocumentFragment showDocumentFragment;

  // Attr
  private Elephant elephant;
  private Realm realm;
	private ViewPagerAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ShowElephantActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.show_elephant_activity);

    realm = Realm.getDefaultInstance();
    elephant = getExtraElephant();

    RealmDB.updateLastVisitedDate(elephant.id);

    binding.setE(elephant);
    ButterKnife.bind(this);

    initIcon();
    setSupportActionBar(toolbar);
    toolbarTitle.setMaxLines(1);
    toolbarTitle.setHorizontallyScrolling(true);
    toolbarTitle.setEllipsize(TextUtils.TruncateAt.END);
    toolbarTitle.setText(String.format(getString(R.string.elephant_show_title), elephant.name));
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    setupViewPager(viewPager);
    tabLayout.setupWithViewPager(viewPager);

    /* set mini fab menu open/close animations */
    miniFabOpenAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_in);
    miniFabCloseAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_out);

    miniFabOpenAnimation.setAnimationListener(new Animation.AnimationListener() {
			@Override public void onAnimationStart(Animation animation) { fabMenu.setVisibility(View.VISIBLE); }
			@Override public void onAnimationEnd(Animation animation) {}
			@Override public void onAnimationRepeat(Animation animation) {}
		});

    miniFabCloseAnimation.setAnimationListener(new Animation.AnimationListener() {
			@Override public void onAnimationStart(Animation animation) {}
			@Override public void onAnimationEnd(Animation animation) { fabMenu.setVisibility(View.GONE); }
			@Override public void onAnimationRepeat(Animation animation) {}
		});

    backgroundInAnimation = new AlphaAnimation(0f, 1f);
    backgroundInAnimation.setInterpolator(new LinearInterpolator());
    backgroundInAnimation.setDuration(400);

    backgroundOutAnimation = new AlphaAnimation(1f, 0f);
		backgroundOutAnimation.setInterpolator(new LinearInterpolator());
		backgroundOutAnimation.setDuration(300);

    backgroundInAnimation.setAnimationListener(new Animation.AnimationListener() {
			@Override public void onAnimationStart(Animation animation) { fabMenuBackgroundMask.setVisibility(View.VISIBLE); }
			@Override public void onAnimationEnd(Animation animation) {}
			@Override public void onAnimationRepeat(Animation animation) {}
		});
		backgroundOutAnimation.setAnimationListener(new Animation.AnimationListener() {
			@Override public void onAnimationStart(Animation animation) {}
			@Override public void onAnimationEnd(Animation animation) { fabMenuBackgroundMask.setVisibility(View.GONE); }
			@Override public void onAnimationRepeat(Animation animation) {}
		});
  }

  private void initIcon() {
		fabMenuTrigger.setImageDrawable(new IconicsDrawable(this)
        .icon(MaterialDesignIconic.Icon.gmi_plus)
        .color(Color.WHITE)
        .sizeDp(24)
		);

		fabEdit.setImageDrawable(new IconicsDrawable(this)
			.icon(MaterialDesignIconic.Icon.gmi_edit)
			.color(Color.WHITE)
			.sizeDp(16)
		);

		fabAddDocument.setImageDrawable(new IconicsDrawable(this)
			.icon(MaterialDesignIconic.Icon.gmi_filter_frames)
			.color(Color.WHITE)
			.sizeDp(16)
		);

		fabDelete.setImageDrawable(new IconicsDrawable(this)
			.icon(MaterialDesignIconic.Icon.gmi_delete)
			.color(Color.WHITE)
			.sizeDp(16)
		);

		fabAddNote.setImageDrawable(new IconicsDrawable(this)
			.icon(MaterialDesignIconic.Icon.gmi_calendar_note)
			.color(Color.WHITE)
			.sizeDp(16)
		);

		fabAddLocation.setImageDrawable(new IconicsDrawable(this)
			.icon(MaterialDesignIconic.Icon.gmi_my_location)
			.color(Color.WHITE)
			.sizeDp(16)
		);

		fabAddConsultation.setImageDrawable(new IconicsDrawable(this)
			.icon(MaterialDesignIconic.Icon.gmi_file)
			.color(Color.WHITE)
			.sizeDp(16)
		);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    realm.close();
  }

  @Override
  public boolean onSupportNavigateUp() {
  	if (fabIsOpen) {
  		onFabMenuTriggered();
  		return false;
		} else {
			setResult(RESULT_OK);
			finish();
			overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
			return true;
		}
  }

	@Override
	public void onBackPressed() {
		onSupportNavigateUp();
	}

  /* @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.show_elephant_options_menu, menu);
    menu.findItem(R.id.edit_elephant).setIcon(editIcon);
    menu.findItem(R.id.delete_elephant).setIcon(deleteIcon);
    return true;
  } */

  /* @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.edit_elephant) {
      Intent intent = new Intent(this, ManageElephantActivity.class);
      intent.putExtra(EXTRA_ELEPHANT_ID, elephant.id);
      startActivityForResult(intent, REQUEST_ELEPHANT_EDITED);
      return true;
    } else if (item.getItemId() == R.id.delete_elephant) {
      new MaterialDialog.Builder(this)
          .title(R.string.delete_this_elephant_from_local_db)
          .positiveText(R.string.yes)
          .onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
              realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                  elephant.deleteFromRealm();
                }
              });
              finish();
            }
          })
          .negativeText(R.string.no)
          .stackingBehavior(StackingBehavior.ALWAYS)
          .show();
      return true;
    }
    return false;
  } */

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if ((resultCode == RESULT_DRAFT || resultCode == RESULT_VALIDATE) && requestCode == REQUEST_ELEPHANT_EDITED ) {
				Intent intent = getIntent();
				intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				finish();
				startActivity(intent);
    } else {
    	switch (requestCode) {
				case REQUEST_ADD_DOCUMENT:
					if (data != null) {
						Document document = new Document();
						document.title = AddDocumentActivity.getExtraTitle(data);
						document.path = AddDocumentActivity.getExtraPath(data);
						document.type = AddDocumentActivity.getExtraType(data);
						document.elephant_id = elephant.id;
						showDocumentFragment.addDocument(document);
						RealmDB.insertOrUpdateDocument(document);
					}
					break;
			}
		}
  }

  public Elephant getElephant() {
    return elephant;
  }

  private Elephant getExtraElephant() {
    Intent intent = getIntent();
    Integer id = intent.getIntExtra(EXTRA_ELEPHANT_ID, -1);
    if (id != -1) {
      return realm.where(Elephant.class).equalTo(ID, id).findFirst();
    }
    throw new RuntimeException("ShowElephantActivity:148: ID incorrect");
  }

  private void setupViewPager(ViewPager viewPager) {
  	showDocumentFragment = new ShowDocumentFragment();

    adapter = new ViewPagerAdapter(getSupportFragmentManager());
    adapter.addFragment(new ShowOverviewFragment(), getString(R.string.overview));
    adapter.addFragment(new ShowParentageFragment(), getString(R.string.parentage));
    adapter.addFragment(new ShowChildrenFragment(), getString(R.string.children));
    adapter.addFragment(showDocumentFragment, getString(R.string.documents));
    viewPager.setAdapter(adapter);
  }

  @Override
  public void onDocumentClick(Document document) {
    Intent intent = new Intent(this, ShowDocumentActivity.class);
    ShowDocumentActivity.setExtraTitle(intent, document.title);
    ShowDocumentActivity.setExtraPath(intent, document.path);
    startActivity(intent);
    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
  }

	@OnClick(R.id.fabMenuTrigger)
	public void onFabMenuTriggered() {
		fabIsOpen = !fabIsOpen;
		if (fabIsOpen) {
			/* open fab sub menu */
			fabMenuBackgroundMask.startAnimation(backgroundInAnimation);

			fabMenuTrigger.animate()
				.rotation(-135)
				.setInterpolator(new AccelerateDecelerateInterpolator())
				.setDuration(400);

			fabMenu.startAnimation(miniFabOpenAnimation);
		} else {
			/* close fab sub menu */
			fabMenuBackgroundMask.startAnimation(backgroundOutAnimation);

			fabMenuTrigger.animate()
				.rotation(0)
				.setInterpolator(new AccelerateDecelerateInterpolator())
				.setDuration(300);

			fabMenu.startAnimation(miniFabCloseAnimation);
		}
	}

	@OnClick(R.id.fabMenuBackgroundMask)
	public void onFabMenuBackgroundMaskClick() {
  	if (fabIsOpen) {
			onFabMenuTriggered();
		}
	}

	@OnClick(R.id.minifab_edit)
	public void onEditClick() {
		Intent intent = new Intent(this, ManageElephantActivity.class);
		intent.putExtra(EXTRA_ELEPHANT_ID, elephant.id);
		startActivityForResult(intent, REQUEST_ELEPHANT_EDITED);
	}

	@OnClick(R.id.minifab_delete)
	public void onDeleteClick() {
		new MaterialDialog.Builder(this)
			.title(R.string.delete_this_elephant_from_local_db)
			.positiveText(R.string.yes)
			.onPositive(new MaterialDialog.SingleButtonCallback() {
				@Override
				public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
					realm.executeTransaction(new Realm.Transaction() {
						@Override
						public void execute(Realm realm) {
							elephant.deleteFromRealm();
						}
					});
					finish();
				}
			})
			.negativeText(R.string.no)
			.stackingBehavior(StackingBehavior.ALWAYS)
			.show();
	}

	@OnClick(R.id.minifab_adddocument)
	public void onAddDocumentClick() {
		Intent intent = new Intent(this, AddDocumentActivity.class);
		startActivityForResult(intent, REQUEST_ADD_DOCUMENT);
		onFabMenuTriggered();
	}

}
