package fr.elephantasia.activities.showDocument;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.elephantasia.R;

public class ShowDocumentActivity extends AppCompatActivity {

	private static final String EXTRA_PATH = "path";
	private static final String EXTRA_TITLE = "title";

	@BindView(R.id.toolbar) Toolbar toolbar;
	@BindView(R.id.image) SubsamplingScaleImageView imageView;

	static public void setExtraPath(Intent intent, String path) {
		intent.putExtra(EXTRA_PATH, path);
	}

	static public void setExtraTitle(Intent intent, String title) {
		intent.putExtra(EXTRA_TITLE, title);
	}

	static public String getExtraPath(Intent intent) {
		return intent.getStringExtra(EXTRA_PATH);
	}

	static public String getExtraTitle(Intent intent) {
		return intent.getStringExtra(EXTRA_TITLE);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_document_activity);
		ButterKnife.bind(this);

		setSupportActionBar(toolbar);
		if (getSupportActionBar() != null) {
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}

		setupTitle();
		refreshImage();
	}

	@Override
	public boolean onSupportNavigateUp() {
		finish();
		overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
		return true;
	}

	private void setupTitle() {
		TextView titleView = ButterKnife.findById(toolbar, R.id.title);

		String title = getExtraTitle(getIntent());
		titleView.setText((title != null) ? title : "Show document");
	}

	private void refreshImage() {
		String path = getExtraPath(getIntent());
		File file = new File(path);
		if (file.exists()) {
			imageView.setImageUri(path);
		}
	}
}
