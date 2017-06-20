package com.alexvasilkov.foldablelayout.sample.utils;

import android.widget.ImageView;

import com.alexvasilkov.foldablelayout.sample.items.Person;
import com.bumptech.glide.Glide;

public class GlideHelper {

	private GlideHelper() {
	}

	public static void loadImage(ImageView image, Person person) {
		if (person == null) {
			return;
		}
		Glide.with(image.getContext().getApplicationContext())
				.load(person.getImageId())
				.into(image);
	}

}
