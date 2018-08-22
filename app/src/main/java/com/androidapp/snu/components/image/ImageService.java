package com.androidapp.snu.components.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;

import java.io.File;

public interface ImageService {
	File store(final Bitmap bitmap, final String fileName);
	File store(final Image image, final String fileName);

	File findAsFile(final String fileName);
	Image findAsImage(final String fileName);
	Bitmap findAsBitmap(final String fileName);

	void delete(final String fileName);

	static ImageService getFor(final Bitmap.CompressFormat format, final Context context) {
		switch (format) {
			case JPEG:
				return new ImageServiceJpg();
			default:
				throw new IllegalArgumentException("Compressformat '" + format + "' not supported");
		}
	}
}
