package com.androidapp.snu.components.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class BitmapUtils {
	public static Bitmap getRotatedBitmap(Bitmap bm, float rotation) {
		int width = bm.getWidth();
		int height = bm.getHeight();
		Matrix matrix = new Matrix();
		matrix.setRotate(rotation);
		return Bitmap.createBitmap(
				bm, 0, 0, width, height, matrix, false);
	}

	public static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
		int width = bm.getWidth();
		int height = bm.getHeight();
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		return Bitmap.createBitmap(
				bm, 0, 0, width, height, matrix, false);
	}
}
