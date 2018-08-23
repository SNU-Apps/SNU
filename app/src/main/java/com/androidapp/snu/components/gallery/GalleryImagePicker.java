package com.androidapp.snu.components.gallery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.IOException;

public class GalleryImagePicker {
	public static Bitmap getImageFromResult(Context context, int resultCode, Intent imageReturnedIntent) {
		Bitmap bm = null;
		if (resultCode == Activity.RESULT_OK) {
			Uri selectedImage = imageReturnedIntent.getData();
			bm = getImageFromUri(context, selectedImage);
			if (bm != null) {
				int rotation = getRotationFromGallery(context, selectedImage);
				bm = rotate(bm, rotation);
			}
		}
		return bm;
	}

	private static Bitmap getImageFromUri(Context context, Uri selectedImage) {
		try {
			return MediaStore.Images.Media.getBitmap(context.getContentResolver(), selectedImage);
		} catch (IOException e) {
			return null;
		}
	}

	private static int getRotationFromGallery(Context context, Uri imageUri) {
		int result = 0;
		String[] columns = {MediaStore.Images.Media.ORIENTATION};
		Cursor cursor = null;
		try {
			cursor = context.getContentResolver().query(imageUri, columns, null, null, null);
			if (cursor != null && cursor.moveToFirst()) {
				int orientationColumnIndex = cursor.getColumnIndex(columns[0]);
				result = cursor.getInt(orientationColumnIndex);
			}
		} catch (Exception e) {
			//Do nothing
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return result;
	}


	private static Bitmap rotate(Bitmap bm, int rotation) {
		if (rotation != 0) {
			Matrix matrix = new Matrix();
			matrix.postRotate(rotation);
			return Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
		}
		return bm;
	}
}
