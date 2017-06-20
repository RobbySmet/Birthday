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
import com.alexvasilkov.foldablelayout.sample.activities.UnfoldableDetailsActivity;
import com.alexvasilkov.foldablelayout.sample.utils.GlideHelper;

import java.util.Arrays;

public class PaintingsAdapter extends ItemsAdapter<Painting> implements View.OnClickListener {

	private Context mContext;
	private boolean mIsDetail;

	public PaintingsAdapter(Context context, boolean isDetail) {
		super(context);
		mContext = context;
		mIsDetail = isDetail;
		setItemsList(Arrays.asList(Painting.getAllPaintingsForFoldableListActivity(context.getResources())));
	}

	@Override
	protected View createView(Painting item, int pos, ViewGroup parent, LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.list_item, parent, false);
		ViewHolder vh = new ViewHolder();
		vh.image = Views.find(view, R.id.list_item_image);
		vh.image.setOnClickListener(this);
		vh.title = Views.find(view, R.id.list_item_title);
		view.setTag(vh);

		return view;
	}

	@Override
	protected void bindView(Painting item, int pos, View convertView) {
		ViewHolder vh = (ViewHolder) convertView.getTag();

		if (item != null) {

			if (!mIsDetail) {
				if (pos == 0) {
					item = new Painting(R.drawable.wip, "Swipe up", "", "");
				} else {
					item = new Painting(R.drawable.content, "Click", "", "");
				}
			}


			vh.image.setTag(R.id.list_item_image, item);
			GlideHelper.loadPaintingImage(vh.image, item);
			vh.title.setText(item.getTitle());
		}
	}

	@Override
	public void onClick(View view) {
		Painting item = (Painting) view.getTag(R.id.list_item_image);
		if (view.getContext() instanceof UnfoldableDetailsActivity) {
			((UnfoldableDetailsActivity) view.getContext()).openDetails(view, item);
		} else if (view.getContext() instanceof FoldableListActivity) {
			Intent intent = new Intent();
			intent.setComponent(new ComponentName(mContext, "com.alexvasilkov.foldablelayout.sample.activities.UnfoldableDetailsActivity"));
			mContext.startActivity(intent);
		}
	}

	private static class ViewHolder {
		ImageView image;
		TextView  title;
	}

}
