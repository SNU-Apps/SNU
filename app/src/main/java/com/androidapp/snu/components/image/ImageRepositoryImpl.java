package com.androidapp.snu.components.image;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

class ImageRepositoryImpl implements ImageRepository {
	private final Context context;

	ImageRepositoryImpl(final Context context) {
		this.context = context;
	}

	@Override
	public File store(Bitmap bitmap, String fileName) {
		File mFile;
		try {
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
			byte[] byteArray = stream.toByteArray();
			mFile = new File(context.getExternalFilesDir(null), fileName + ".jpg");
			FileOutputStream fos = new FileOutputStream(mFile);
			fos.write(byteArray);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			return null;
		}
		return mFile;
	}

	@Override
	public File findAsFile(String fileName) {
		return null;
	}

	@Override
	public Bitmap findAsBitmap(String fileName) {
		return null;
	}

	@Override
	public void delete(String fileName) {

	}
}
