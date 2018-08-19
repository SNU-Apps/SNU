package com.androidapp.snu.activities.wishes.createWish;

public class CurrentWish {
	private static String photoPath;
	private static String description;

	public static void clear() {
		photoPath = null;
		description = null;
	}

	public static String getPhotoPath() {
		return photoPath;
	}

	public static void setPhotoPath(String photoPath) {
		CurrentWish.photoPath = photoPath;
	}

	public static String getDescription() {
		return description;
	}

	public static void setDescription(String description) {
		CurrentWish.description = description;
	}

	public static boolean hasPhoto() {
		return photoPath != null;
	}
}
