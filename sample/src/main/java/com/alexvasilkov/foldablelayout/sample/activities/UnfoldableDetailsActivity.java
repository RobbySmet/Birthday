package com.alexvasilkov.foldablelayout.sample.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alexvasilkov.android.commons.texts.SpannableBuilder;
import com.alexvasilkov.android.commons.utils.Views;
import com.alexvasilkov.foldablelayout.UnfoldableView;
import com.alexvasilkov.foldablelayout.sample.R;
import com.alexvasilkov.foldablelayout.sample.items.Person;
import com.alexvasilkov.foldablelayout.sample.items.PersonAdapter;
import com.alexvasilkov.foldablelayout.sample.utils.GlideHelper;
import com.alexvasilkov.foldablelayout.shading.GlanceFoldShading;

public class UnfoldableDetailsActivity extends BaseActivity {

	private View           listTouchInterceptor;
	private View           detailsLayout;
	private UnfoldableView unfoldableView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_unfoldable_details);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setTitle("Sponsors");

		ListView listView = Views.find(this, R.id.list_view);
		listView.setAdapter(new PersonAdapter(this, true));

		listTouchInterceptor = Views.find(this, R.id.touch_interceptor_view);
		listTouchInterceptor.setClickable(false);

		detailsLayout = Views.find(this, R.id.details_layout);
		detailsLayout.setVisibility(View.INVISIBLE);

		unfoldableView = Views.find(this, R.id.unfoldable_view);

		Bitmap glance = BitmapFactory.decodeResource(getResources(), R.drawable.unfold_glance);
		unfoldableView.setFoldShading(new GlanceFoldShading(glance));

		unfoldableView.setOnFoldingListener(new UnfoldableView.SimpleFoldingListener() {
			@Override
			public void onUnfolding(UnfoldableView unfoldableView) {
				listTouchInterceptor.setClickable(true);
				detailsLayout.setVisibility(View.VISIBLE);
			}

			@Override
			public void onUnfolded(UnfoldableView unfoldableView) {
				listTouchInterceptor.setClickable(false);
			}

			@Override
			public void onFoldingBack(UnfoldableView unfoldableView) {
				listTouchInterceptor.setClickable(true);
			}

			@Override
			public void onFoldedBack(UnfoldableView unfoldableView) {
				listTouchInterceptor.setClickable(false);
				detailsLayout.setVisibility(View.INVISIBLE);
			}
		});
	}

	@Override
	public void onBackPressed() {
		if (unfoldableView != null
		    && (unfoldableView.isUnfolded() || unfoldableView.isUnfolding())) {
			unfoldableView.foldBack();
		} else {
			super.onBackPressed();
		}
	}

	public void openDetails(View coverView, final Person person) {
		final ImageView image = Views.find(detailsLayout, R.id.details_image);
		final TextView title = Views.find(detailsLayout, R.id.details_title);
		final TextView description = Views.find(detailsLayout, R.id.details_text);
		final Button confirm = Views.find(detailsLayout, R.id.button);

		GlideHelper.loadPaintingImage(image, person);
		title.setText(person.getTitle());

		SpannableBuilder builder = new SpannableBuilder(this);
		builder.append(person.getLocation());
		description.setText(builder.build());

		unfoldableView.unfold(coverView, detailsLayout);

		confirm.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View view) {
				sendEmail(person.getTitle());
			}
		});
	}

	private void sendEmail(String gift) {
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("message/rfc822");
		i.putExtra(Intent.EXTRA_EMAIL, new String[]{"marianne.de.turck@gmail.com"});
		i.putExtra(Intent.EXTRA_SUBJECT, "Ik heb mijn trouwcadeau gekozen!");
		i.putExtra(Intent.EXTRA_TEXT, "Dag patee, ik heb gekozen voor " + gift + ". Regel het maar :D");
		try {
			startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(this, "Mail sturen is gefaald. Graag zelf iets laten weten.", Toast.LENGTH_LONG).show();
		}
	}

}
