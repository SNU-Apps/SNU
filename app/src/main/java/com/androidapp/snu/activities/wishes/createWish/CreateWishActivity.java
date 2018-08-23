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
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidapp.snu.R;
import com.androidapp.snu.activities.home.AbstractHomeTransitionActivity;
import com.androidapp.snu.activities.wishes.createWish.dialog.PhotoModifyDialog;
import com.androidapp.snu.components.camera.PhotoPolaroidThumbnail;
import com.androidapp.snu.components.image.ImageRepository;
import com.androidapp.snu.components.utils.BitmapUtils;
import com.androidapp.snu.components.utils.KeyboardUtils;

import java.io.File;
import java.util.UUID;

public class CreateWishActivity extends AbstractHomeTransitionActivity {
	public static final int HEADER_IMAGE_ID = R.drawable.v1_1;
	public static final int ICON_IMAGE_ID = R.drawable.v1;
	public static final String HEADER_TEXT = "Neuen Wunsch...";
	private static final String fontPath = "fonts/handwrite.ttf";
	public static final int PICK_IMAGE_FROM_GALLERY = 1;
	public static final int PICK_IMAGE_FROM_CAMERA = 2;

	LinearLayout contentView;
	LinearLayout footerView;
	PhotoPolaroidThumbnail photoThumbnail;

	private Wish currentWish;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		currentWish = Wish.fromIntent(getIntent());
		contentView = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.activity_create_wish_content, null);
		footerView = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.activity_create_wish_footer, null);
		super.onCreate(savedInstanceState);
	}

	@Override
	protected int getHeaderImageId() {
		return HEADER_IMAGE_ID;
	}

	@Override
	protected int getIconImageId() {
		return ICON_IMAGE_ID;
	}

	@Override
	protected String getHeaderText() {
		return HEADER_TEXT;
	}

	@Override
	protected View getContent() {
		initHeadline();
		initDescription();
		initPhotoThumbnail();
		return contentView;
	}

	@Override
	protected View getFooter() {
		initFooter();
		return footerView;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == PICK_IMAGE_FROM_GALLERY) {
			handleImageFromGalleryReceived(resultCode, data);
		} else if (requestCode == PICK_IMAGE_FROM_CAMERA) {
			handleImageFromCameraReceived(resultCode);
		}
	}

	private void initHeadline() {
		TextView headline = contentView.findViewById(R.id.activity_create_wish_content_headline);
		Typeface typeface = Typeface.createFromAsset(getAssets(), fontPath);
		headline.setTypeface(typeface);
	}

	private void initDescription() {
		TextInputEditText description = contentView.findViewById(R.id.activity_create_wish_content_description);
		Typeface typeface = Typeface.createFromAsset(getAssets(), fontPath);
		description.setTypeface(typeface);
		KeyboardUtils.addKeyboardToggleListener(this, description::setCursorVisible);
		contentView.setOnClickListener(view -> KeyboardUtils.forceCloseKeyboard(contentView));
		description.setText(currentWish.getDescription());
		description.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				currentWish.setDescription(charSequence.toString());
			}

			@Override
			public void afterTextChanged(Editable editable) {

			}
		});
	}

	private void initPhotoThumbnail() {
		photoThumbnail = contentView.findViewById(R.id.activity_create_wish_content_photo_thumbnail);
		photoThumbnail.setPhoto(this, currentWish.getPhotoPath());
		photoThumbnail.setOnClickListener(view -> {
			showPhotoDialog();
		});
		if (currentWish.hasPhoto()) {
			showPhotoDialog();
		}
	}

	private void initFooter() {
		Typeface typeface = Typeface.createFromAsset(getAssets(), fontPath);
		TextView footerText = footerView.findViewById(R.id.activity_create_wish_footer);
		footerText.setTypeface(typeface);
		footerText.setOnClickListener(view -> finish());
	}

	private void getPictureFromGalery() {
		String[] mimeTypes = {"image/jpeg", "image/png"};
		Intent chooserIntent = new Intent(Intent.ACTION_GET_CONTENT)
				.setType("image/*")
				.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
		startActivityForResult(chooserIntent, PICK_IMAGE_FROM_GALLERY);
	}

	private void handleImageFromGalleryReceived(final int resultCode, final Intent data) {
		final Bitmap bitmap = CreateWishImagePicker.getImageFromResult(this, resultCode, data);
		final String fileName = UUID.randomUUID().toString();

		final File jpg =
				ImageRepository.withContext(this)
						.storeCompressed(bitmap, fileName);

		if (jpg != null) {
			currentWish.setPhotoPath(jpg.getPath());
			photoThumbnail.setPhoto(this, currentWish.getPhotoPath());
		}
	}

	private void handleImageFromCameraReceived(final int resultCode) {
		if (resultCode != RESULT_OK) {
			return;
		}

		ImageRepository imageRepository = ImageRepository.withContext(this);

		//load bitmap and delete file
		Bitmap bitmap = imageRepository.findAsBitmap("temp");
		imageRepository.delete("temp");

		Bitmap rotatedBitmap = BitmapUtils.getRotatedBitmap(bitmap, 90);
		final String fileName = UUID.randomUUID().toString();

		final File jpg =
				ImageRepository.withContext(this)
						.store(rotatedBitmap, fileName);

		if (jpg != null) {
			currentWish.setPhotoPath(jpg.getPath());
			photoThumbnail.setPhoto(this, currentWish.getPhotoPath());
		}
	}

	private void showPhotoDialog() {
		if (currentWish.hasPhoto()) {
			showModifyPhotoDialog();
		} else {
			showNewPhotoDialog();
		}
	}

	private void showModifyPhotoDialog() {
		Context context = this;
		PhotoModifyDialog dialog =
				new PhotoModifyDialog(this, currentWish);
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
				photoThumbnail.deletePhoto();
				currentWish.setPhotoPath(null);
				dialog.dismiss();
			}

			@Override
			public void onNew() {

			}

			@Override
			public void onOK() {
				dialog.dismiss();
			}

			private void rotate(float rotation) {
				ImageRepository imageRepository = ImageRepository.withContext(context);

				//load bitmap and delete file
				Bitmap bitmap = imageRepository.findAsBitmap(currentWish.getPhotoFileName());
				imageRepository.delete(currentWish.getPhotoFileName());

				Bitmap rotatedBitmap = BitmapUtils.getRotatedBitmap(bitmap, rotation);
				final String fileName = UUID.randomUUID().toString();

				final File newJpg =
						ImageRepository.withContext(context)
								.store(rotatedBitmap, fileName);

				photoThumbnail.setPhoto(context, newJpg.getPath());
				dialog.setPhoto(context, newJpg.getPath());
				currentWish.setPhotoPath(newJpg.getPath());
			}
		});
	}

	private void showNewPhotoDialog() {
		//todo check how this can be removed!
		StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
		StrictMode.setVmPolicy(builder.build());
		//end todo

		File tempFile = new File(getExternalFilesDir(null), "temp.jpg");
		Uri uri = Uri.fromFile(tempFile);
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		startActivityForResult(intent, PICK_IMAGE_FROM_CAMERA);
	}
}
