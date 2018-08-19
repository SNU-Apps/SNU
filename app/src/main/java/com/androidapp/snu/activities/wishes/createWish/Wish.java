package com.androidapp.snu.activities.wishes.createWish;

import android.content.Intent;

public class Wish {
	public static final String PHOTO_PATH = "detail:_photoId";

	private String photoPath;
	private String description;

	static Wish fromIntent(final Intent intent) {
		Wish wish = new Wish();
		wish.photoPath = intent.getStringExtra(PHOTO_PATH);
		return wish;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean hasPhoto() {
		return photoPath != null;
	}
}
