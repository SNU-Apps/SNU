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

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidapp.snu.R;
import com.androidapp.snu.activities.home.AbstractHomeTransitionActivity;
import com.androidapp.snu.components.camera.PhotoPolaroid;
import com.androidapp.snu.components.camera.PhotoPolaroidThumbnail;
import com.androidapp.snu.components.utils.BitmapUtils;
import com.androidapp.snu.components.utils.KeyboardUtils;

import java.io.File;

public class CreateWishActivity extends AbstractHomeTransitionActivity {
	public static final int HEADER_IMAGE_ID = R.drawable.v1_1;
	public static final int ICON_IMAGE_ID = R.drawable.v1;
	public static final String HEADER_TEXT = "Neuen Wunsch...";
	private static final String fontPath = "fonts/handwrite.ttf";
	public static final int PICK_IMAGE_FROM_GALLERY = 1;

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
	}

	private void initPhotoThumbnail() {
		photoThumbnail = contentView.findViewById(R.id.activity_create_wish_content_photo_thumbnail);
		photoThumbnail.setPhoto(this, currentWish.getPhotoPath());
		photoThumbnail.setOnClickListener(view -> {
			showPhotoDialog();
			//ActivityCompat.startActivity(this, new Intent(this, PhotoWishActivity.class), null);
			//getPictureFromGalery();
			//finish();
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
		final File jpg = BitmapUtils.storeAsJpg(bitmap, true, this);
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
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.dialog_modify_photo);

		PhotoPolaroid photo = dialog.findViewById(R.id.dialog_modify_photo_content_polaroid);
		photo.setPhoto(this, currentWish.getPhotoPath());

//		Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
//		// if button is clicked, close the custom dialog
//		dialogButton.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				dialog.dismiss();
//			}
//		});

		dialog.show();
	}

	private void showNewPhotoDialog() {
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.dialog_modify_photo);

		PhotoPolaroid photo = dialog.findViewById(R.id.dialog_modify_photo_content_polaroid);
		photo.setPhoto(this, currentWish.getPhotoPath());

//		Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
//		// if button is clicked, close the custom dialog
//		dialogButton.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				dialog.dismiss();
//			}
//		});

		dialog.show();
	}
}
