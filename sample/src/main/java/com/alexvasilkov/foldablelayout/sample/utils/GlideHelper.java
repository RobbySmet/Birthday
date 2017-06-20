package com.alexvasilkov.foldablelayout.sample.utils;

import android.widget.ImageView;

import com.alexvasilkov.foldablelayout.sample.items.Person;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class GlideHelper {

	private GlideHelper() {
	}

	public static void loadPaintingImage(ImageView image, Person person) {
		if (person == null) {
			return;
		}
		Glide.with(image.getContext().getApplicationContext())
				.load(person.getImageId())
				.dontAnimate()
				.diskCacheStrategy(DiskCacheStrategy.NONE)
				.into(image);
	}

}
