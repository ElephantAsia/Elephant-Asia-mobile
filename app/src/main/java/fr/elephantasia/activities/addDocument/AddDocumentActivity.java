package fr.elephantasia.activities.addDocument;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.elephantasia.R;
import fr.elephantasia.dialogs.PickImageDialog;
import fr.elephantasia.dialogs.PickImageDialogBuilder;
import fr.elephantasia.utils.ImageUtil;

public class AddDocumentActivity extends AppCompatActivity {

	private static final String EXTRA_PATH = "path";
	private static final String EXTRA_TITLE = "title";
	private static final String EXTRA_TYPE = "rtype";

	private static final int REQUEST_CAPTURE_PHOTO = 8;
	private static final int REQUEST_IMPORT_PHOTO = 9;

	public static void setExtraPath(Intent intent, String path) {
		intent.putExtra(EXTRA_PATH, path);
	}

	public static void setExtraTitle(Intent intent, String title) {
		intent.putExtra(EXTRA_TITLE, title);
	}

	public static void setExtraType(Intent intent, String type) {
		intent.putExtra(EXTRA_TYPE, type);
	}

	public static String getExtraPath(Intent intent) { return intent.getStringExtra(EXTRA_PATH); }

	public static String getExtraTitle(Intent intent) {
			return intent.getStringExtra(EXTRA_TITLE);
	}

	public static String getExtraType(Intent intent) {
			return intent.getStringExtra(EXTRA_TYPE);
	}

	@BindView(R.id.toolbar) Toolbar toolbar;
	@BindView(R.id.image) ImageView imageView;
	@BindView(R.id.title) EditText title;
	@BindView(R.id.type) EditText type;

	private PickImageDialog pickImageDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_document_activity);
		ButterKnife.bind(this);

		setSupportActionBar(toolbar);
		if (getSupportActionBar() != null) {
				getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onSupportNavigateUp() {
		setResult(RESULT_CANCELED);
		finish();
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.add_document_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.validate && checkMandatoryFields()) {
			validate();
			return true;
		}
		return false;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			switch (requestCode) {
				case REQUEST_IMPORT_PHOTO:
					if (data != null && data.getData() != null) {
						Uri uri = data.getData();
						String path = ImageUtil.createImageFileFromUri(this, uri);
						refreshImagePreview(path);
					}
					break;
				case REQUEST_CAPTURE_PHOTO:
					Uri uri = Uri.fromFile(ImageUtil.getCapturePhotoFile(this));
					String path = ImageUtil.createImageFileFromUri(this, uri);
					refreshImagePreview(path);
					break;
			}
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
		if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
			startActivityForResult(pickImageDialog.getIntent(0), pickImageDialog.getRequestCode(0));
		}
	}

	@OnClick(R.id.image)
	public void onImageClick() {
		pickImageDialog = new PickImageDialogBuilder(this)
			.build()
			.setCaptureCode(REQUEST_CAPTURE_PHOTO)
			.setImportCode(REQUEST_IMPORT_PHOTO)
			.setListener(new PickImageDialog.Listener() {
				@Override
				public void execute(Intent intent, int requestCode) {
					if (requestCode == REQUEST_CAPTURE_PHOTO) {
						if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
							ActivityCompat.requestPermissions(AddDocumentActivity.this, new String[]{Manifest.permission.CAMERA}, 0);
						} else {
							startActivityForResult(intent, requestCode);
						}
					} else {
						startActivityForResult(intent, requestCode);
					}
				}
			})
			.load();
		pickImageDialog.show();
	}

	private void validate() {
		if (title.getText().toString().trim().length() == 0
				|| type.getText().toString().trim().length() == 0
				|| getExtraPath(getIntent()).length() == 0) {
			return ;
		}

		Intent data = new Intent();
		setExtraPath(data, getExtraPath(getIntent()));
		setExtraTitle(data, title.getText().toString());
		setExtraType(data, type.getText().toString());

		setResult(RESULT_OK, data);
		finish();
	}

	private boolean checkMandatoryFields() {
		return title.getText().toString().trim().length() > 0
						&& type.getText().toString().trim().length() > 0;
	}

	private void refreshImagePreview(String path) {
		Log.i("preview", "path: " + path);

		File file = new File(path);
		if (file.exists()) {
			setExtraPath(getIntent(), path);
			Bitmap bitmap = BitmapFactory.decodeFile(path);
			imageView.setImageBitmap(bitmap);
		}
	}
}
