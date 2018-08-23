package com.androidapp.snu.components.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.androidapp.snu.components.utils.BitmapUtils;

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
		try {
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
			return storeByteArrayToFileInternal(stream.toByteArray(), fileName);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public File storeCompressed(Bitmap bitmap, String fileName) {
		try {
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			Bitmap compressedBitmap = BitmapUtils.getResizedBitmap(bitmap, bitmap.getWidth() / 3, bitmap.getHeight() / 3);
			compressedBitmap.compress(Bitmap.CompressFormat.JPEG, 30, stream);
			return storeByteArrayToFileInternal(stream.toByteArray(), fileName);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public File findAsFile(String fileName) {
		return new File(context.getExternalFilesDir(null), fileName + ".jpg");
	}

	@Override
	public Bitmap findAsBitmap(String fileName) {
		File image = new File(context.getExternalFilesDir(null), fileName + ".jpg");
		return BitmapFactory.decodeFile(image.getAbsolutePath());
	}

	@Override
	public void delete(String fileName) {
		new File(context.getExternalFilesDir(null), fileName + ".jpg").delete();
	}

	private File storeByteArrayToFileInternal(byte[] byteArray, String fileName) {
		File mFile = new File(context.getExternalFilesDir(null), fileName + ".jpg");
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(mFile);
			fos.write(byteArray);
			fos.flush();
			fos.close();
			return mFile;
		} catch (Exception e) {
			return null;
		}
	}
}
