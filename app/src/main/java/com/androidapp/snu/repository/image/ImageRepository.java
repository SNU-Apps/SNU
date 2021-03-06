package com.androidapp.snu.repository.image;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.File;

public interface ImageRepository {
	File store(final Bitmap bitmap, final String fileName);
	File storeCompressed(final Bitmap bitmap, final String fileName);

	File findAsFile(final String fileName);
	Bitmap findAsBitmap(final String fileName);

	void delete(final String fileName);

	static ImageRepository withContext(final Context context) {
		return new ImageRepositoryImpl(context);
	}
}
