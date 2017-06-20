package com.alexvasilkov.foldablelayout.sample.items;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexvasilkov.android.commons.adapters.ItemsAdapter;
import com.alexvasilkov.android.commons.utils.Views;
import com.alexvasilkov.foldablelayout.sample.R;
import com.alexvasilkov.foldablelayout.sample.activities.FoldableListActivity;
import com.alexvasilkov.foldablelayout.sample.utils.GlideHelper;

import java.util.Arrays;

public class PersonAdapter extends ItemsAdapter<Person> {

	private static final int INTRO = 0;
	private static final int BODY  = 1;
	private final boolean mIsDetail;

	private Context mContext;

	public PersonAdapter(Context context, boolean isDetail) {
		super(context);
		mContext = context;
		mIsDetail = isDetail;
		setItemsList(Arrays.asList(Person.getAllPaintingsForFoldableListActivity(context.getResources())));
	}

	@Override
	public int getItemViewType(final int position) {
		if (position == 0) {
			return INTRO;
		} else {
			return BODY;
		}
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	protected View createView(Person item, int pos, ViewGroup parent, LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.list_item, parent, false);
		ViewHolder vh = new ViewHolder();
		vh.image = Views.find(view, R.id.list_item_image);
		vh.title = Views.find(view, R.id.list_item_title);
		view.setTag(vh);

		return view;
	}

	@Override
	protected void bindView(Person item, final int pos, View convertView) {
		ViewHolder holder = (ViewHolder) convertView.getTag();

		if (!mIsDetail) {
			switch (getItemViewType(pos)) {
				case INTRO:
					item = new Person(R.drawable.home, "Swipe up", "");
					break;
				case BODY:
					item = new Person(R.drawable.citytrip2, "Click", "");
					break;
			}
		}

		holder.image.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {
				if ((v.getContext() instanceof FoldableListActivity) && pos > 0) {
					Intent intent = new Intent();
					intent.setComponent(new ComponentName(mContext, "com.alexvasilkov.foldablelayout.sample.activities.UnfoldableDetailsActivity"));
					mContext.startActivity(intent);
				}
			}
		});

		holder.title.setText(item.getTitle());

		holder.image.setTag(R.id.list_item_image, item);
		GlideHelper.loadImage(holder.image, item);
	}

	private static class ViewHolder {
		ImageView image;
		TextView  title;
	}
}
