package com.androidapp.snu.activities.wishes.createWish;

import android.content.Intent;

import java.util.UUID;

public class Wish {
	public static final String ID = "detail:_wishId";
	public static final String PHOTO_PATH = "detail:_photoId";
	public static final String DESCRIPTION = "detail:_description";

	private UUID wishId;
	private String photoPath;
	private String description;

	static Wish fromIntent(final Intent intent) {
		Wish wish = new Wish();
		wish.wishId = UUID.fromString(intent.getStringExtra(ID));
		wish.photoPath = intent.getStringExtra(PHOTO_PATH);
		wish.description = intent.getStringExtra(DESCRIPTION);
		return wish;
	}

	public UUID getWishId() {
		return wishId;
	}

	public void setWishId(UUID wishId) {
		this.wishId = wishId;
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
