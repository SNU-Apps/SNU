package com.androidapp.snu.components.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.IOException;

public class ImagePicker {
	private static final String TEMP_IMAGE_NAME = "tempImage";

	public static Bitmap getImageFromResult(Context context, int resultCode, Intent imageReturnedIntent) {
		Bitmap bm = null;
		File imageFile = getTempFile(context);
		if (resultCode == Activity.RESULT_OK) {
			Uri selectedImage;
			boolean isCamera = (imageReturnedIntent == null ||
					imageReturnedIntent.getData() == null ||
					imageReturnedIntent.getData().toString().contains(imageFile.toString()));
			if (isCamera) {
				selectedImage = Uri.fromFile(imageFile);
			} else {
				selectedImage = imageReturnedIntent.getData();
			}

			bm = getImageFromUri(context, selectedImage);
			if (bm != null) {
				int rotation = getRotation(context, selectedImage, isCamera);
				bm = rotate(bm, rotation);
			}
		}
		return bm;
	}


	private static File getTempFile(Context context) {
		File imageFile = new File(context.getExternalCacheDir(), TEMP_IMAGE_NAME);
		imageFile.getParentFile().mkdirs();
		return imageFile;
	}

	private static Bitmap getImageFromUri(Context context, Uri selectedImage) {
		try {
			return MediaStore.Images.Media.getBitmap(context.getContentResolver(), selectedImage);
		} catch (IOException e) {
			return null;
		}
	}

	private static int getRotation(Context context, Uri imageUri, boolean isCamera) {
		int rotation;
		if (isCamera) {
			rotation = getRotationFromCamera(context, imageUri);
		} else {
			rotation = getRotationFromGallery(context, imageUri);
		}
		return rotation;
	}

	private static int getRotationFromCamera(Context context, Uri imageFile) {
		int rotate = 0;
		try {
			context.getContentResolver().notifyChange(imageFile, null);
			ExifInterface exif = new ExifInterface(imageFile.getPath());
			int orientation = exif.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);

			switch (orientation) {
				case ExifInterface.ORIENTATION_ROTATE_270:
					rotate = 270;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					rotate = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_90:
					rotate = 90;
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rotate;
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
