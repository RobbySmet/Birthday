package com.alexvasilkov.foldablelayout.sample.items;

import android.content.res.Resources;
import android.content.res.TypedArray;

import com.alexvasilkov.foldablelayout.sample.R;

public class Person {

	private final int    imageId;
	private final String title;
	private final String location;

	public Person(int imageId, String title, String location) {
		this.imageId = imageId;
		this.title = title;
		this.location = location;
	}

	public int getImageId() {
		return imageId;
	}

	public String getTitle() {
		return title;
	}

	public String getLocation() {
		return location;
	}

	public static Person[] getAllPaintingsForFoldableListActivity(Resources res) {
		String[] titles = res.getStringArray(R.array.names);
		String[] locations = res.getStringArray(R.array.detail);
		TypedArray images = res.obtainTypedArray(R.array.images);

		int size = titles.length;
		Person[] persons = new Person[size];

		for (int i = 0; i < size; i++) {
			final int imageId = images.getResourceId(i, -1);
			persons[i] = new Person(imageId, titles[i], locations[i]);
		}

		images.recycle();

		return persons;
	}
}
