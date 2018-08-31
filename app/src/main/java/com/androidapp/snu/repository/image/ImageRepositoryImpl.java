package com.androidapp.snu.repository.image;

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
		return fileName != null
				? new File(context.getExternalFilesDir(null), getNormalizeFileName(fileName))
				: null;
	}

	@Override
	public Bitmap findAsBitmap(String fileName) {
		if (fileName == null) {
			return null;
		}
		File image = new File(context.getExternalFilesDir(null), getNormalizeFileName(fileName));
		return BitmapFactory.decodeFile(image.getAbsolutePath());
	}

	@Override
	public void delete(String fileName) {
		if (fileName == null) {
			return;
		}
		new File(context.getExternalFilesDir(null), getNormalizeFileName(fileName))
				.delete();
	}

	private File storeByteArrayToFileInternal(byte[] byteArray, String fileName) {
		File mFile = new File(context.getExternalFilesDir(null), getNormalizeFileName(fileName));
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

	private String getNormalizeFileName(String fileName) {
		if (!fileName.endsWith(".jpg")) {
			return fileName += ".jpg";
		}
		return fileName;
	}
}
