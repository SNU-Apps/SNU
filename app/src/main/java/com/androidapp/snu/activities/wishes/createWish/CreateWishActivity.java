/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.androidapp.snu.activities.wishes.createWish;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.androidapp.snu.activities.wishes.createWish.dialog.PhotoModifyDialog;
import com.androidapp.snu.components.camera.CameraCaptureActivity;
import com.androidapp.snu.components.gallery.GalleryImagePicker;
import com.androidapp.snu.components.image.ImageRepository;
import com.androidapp.snu.components.utils.BitmapUtils;

import java.io.File;
import java.util.UUID;

public class CreateWishActivity extends AbstractCreateWishActivity {
	public static final int PICK_IMAGE_FROM_GALLERY = 1;
	public static final int PICK_IMAGE_FROM_CAMERA = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		photoThumbnail.setOnClickListener(view -> showPhotoDialog());
		if (currentWish.hasPhoto()) {
			final ImageRepository imageRepository = ImageRepository.withContext(this);
			setBackGroundImage(imageRepository.findAsBitmap(currentWish.getPhotoFileName()));
			showPhotoDialog();
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == PICK_IMAGE_FROM_GALLERY) {
			handleImageFromGalleryReceived(resultCode, data);
		} else if (requestCode == PICK_IMAGE_FROM_CAMERA) {
			handleImageFromCameraReceived(resultCode, data);
		}
	}

	private void showPhotoDialog() {
		if (currentWish.hasPhoto()) {
			showModifyPhotoDialog();
		} else {
			showNewPhotoDialog();
		}
	}

	private void getPictureFromGalery() {
		String[] mimeTypes = {"image/jpeg", "image/png"};
		Intent chooserIntent = new Intent(Intent.ACTION_GET_CONTENT)
				.setType("image/*")
				.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
		startActivityForResult(chooserIntent, PICK_IMAGE_FROM_GALLERY);
	}

	private void handleImageFromGalleryReceived(final int resultCode, final Intent data) {
		final ImageRepository imageRepository = ImageRepository.withContext(this);
		final Bitmap bitmap = GalleryImagePicker.getImageFromResult(this, resultCode, data);
		final String fileName = UUID.randomUUID().toString();

		final File jpg =
				ImageRepository.withContext(this)
						.storeCompressed(bitmap, fileName);

		if (jpg != null) {
			currentWish.setPhotoFileName(jpg.getName());
			setBackGroundImage(imageRepository.findAsBitmap(currentWish.getPhotoFileName()));
			photoThumbnail.setPhoto(this, jpg);
			showPhotoDialog();
		}
	}

	private void handleImageFromCameraReceived(final int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			final ImageRepository imageRepository = ImageRepository.withContext(this);
			final String fileName = data.getStringExtra(CreateWishActivity.PHOTO_FILE_NAME);
			final File jpg = imageRepository.findAsFile(fileName);

			if (jpg != null) {
				currentWish.setPhotoFileName(fileName);
				setBackGroundImage(imageRepository.findAsBitmap(currentWish.getPhotoFileName()));
				photoThumbnail.setPhoto(this, jpg);
				showPhotoDialog();
			}
		}
	}


	private void showModifyPhotoDialog() {
		final Context context = this;
		final ImageRepository imageRepository = ImageRepository.withContext(context);
		final File currentPhoto = imageRepository.findAsFile(currentWish.getPhotoFileName());
		final PhotoModifyDialog dialog = new PhotoModifyDialog(context, currentPhoto);

		dialog.show(new PhotoModifyDialog.ToolbarListener() {
			@Override
			public void onRotateLeft() {
				rotate(270);
			}

			@Override
			public void onRotateRight() {
				rotate(90);
			}

			@Override
			public void onDelete() {
				ImageRepository.withContext(context)
						.delete(currentWish.getPhotoFileName());
				setDefaultBackGroundImage();
				photoThumbnail.deletePhoto();
				currentWish.setPhotoFileName(null);
				dialog.dismiss();
			}

			@Override
			public void onNew() {
				onDelete();
				showPhotoDialog();
			}

			@Override
			public void onOK() {
				dialog.dismiss();
			}

			private void rotate(float rotation) {
				final Bitmap bitmap = imageRepository.findAsBitmap(currentWish.getPhotoFileName());
				final Bitmap rotatedBitmap = BitmapUtils.getRotatedBitmap(bitmap, rotation);
				imageRepository.delete(currentWish.getPhotoFileName());

				String newFileName = UUID.randomUUID().toString();
				final File newJpg =
						imageRepository
								.store(rotatedBitmap, newFileName);

				dialog.setPhoto(context, newJpg);
				photoThumbnail.setPhoto(context, newJpg);
				currentWish.setPhotoFileName(newJpg.getName());
			}
		});
	}

	private void showNewPhotoDialog() {
		Intent intent = new Intent(this, CameraCaptureActivity.class);
		startActivityForResult(intent, CreateWishActivity.PICK_IMAGE_FROM_CAMERA);
	}
}
