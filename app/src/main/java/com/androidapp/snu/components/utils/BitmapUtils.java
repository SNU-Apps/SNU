package com.androidapp.snu.components.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

public class BitmapUtils {

	public static File storeAsJpg(Bitmap bitmap, boolean compress, final Context context) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		if (compress) {
			Bitmap bmp = BitmapUtils.getResizedBitmap(bitmap, bitmap.getWidth() / 3, bitmap.getHeight() / 3);
			bmp.compress(Bitmap.CompressFormat.JPEG, 30, stream);
		} else {
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
		}
		byte[] byteArray = stream.toByteArray();

		File mFile;
		try {
			mFile = new File(context.getExternalFilesDir(null), UUID.randomUUID() + ".jpg");
			FileOutputStream fos = new FileOutputStream(mFile);
			fos.write(byteArray);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			return null;
		}
		return mFile;
	}

	public static Bitmap getRotatedBitmap(Bitmap bm, float rotation) {
		int width = bm.getWidth();
		int height = bm.getHeight();

		Matrix matrix = new Matrix();
		matrix.postRotate(rotation);

		return Bitmap.createBitmap(
				bm, 0, 0, width, height, matrix, false);
	}

	private static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
		int width = bm.getWidth();
		int height = bm.getHeight();
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;

		// CREATE A MATRIX FOR THE MANIPULATION
		Matrix matrix = new Matrix();
		// RESIZE THE BIT MAP
		matrix.postScale(scaleWidth, scaleHeight);

		// "RECREATE" THE NEW BITMAP
		return Bitmap.createBitmap(
				bm, 0, 0, width, height, matrix, false);
	}
}
