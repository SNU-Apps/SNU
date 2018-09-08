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

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidapp.snu.R;
import com.androidapp.snu.activities.AbstractBaseActivity;
import com.androidapp.snu.activities.wishes.MyWishesActivity;
import com.androidapp.snu.components.polaroid.PhotoPolaroidThumbnail;
import com.androidapp.snu.components.utils.KeyboardUtils;
import com.androidapp.snu.repository.image.ImageRepository;
import com.androidapp.snu.repository.wish.Wish;
import com.androidapp.snu.repository.wish.MyWishesRepository;

import java.util.UUID;

public abstract class AbstractCreateWishActivity extends AbstractBaseActivity {
	public static final String PHOTO_FILE_NAME = "detail:_photoFileName";
	public static final String WISH_ID = "detail:_wishId";

	public static final int ICON_IMAGE_ID = R.drawable.v1;
	public static final String HEADER_TEXT = "Neuen Wunsch...";
	private static final String fontPath = "fonts/handwrite.ttf";

	LinearLayout contentView;
	LinearLayout footerView;
	PhotoPolaroidThumbnail photoThumbnail;

	Wish currentWish;
	Wish tempWish = new Wish();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		contentView = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.activity_create_wish_content, null);
		footerView = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.activity_create_wish_footer, null);
		inititalizeWish();
		super.onCreate(savedInstanceState);
	}

	@Override
	public int getIconImageId() {
		return ICON_IMAGE_ID;
	}

	@Override
	public String getHeaderText() {
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
	public void onBackPressed() {
		if (currentWish == null) {
			// create mode
			ImageRepository.withContext(this).delete(tempWish.getPhotoFileName());
		} else {
			// modify mode
			if (tempWish.hasPhoto() && !tempWish.getPhotoFileName().equals(currentWish.getPhotoFileName())) {
				ImageRepository.withContext(this).delete(tempWish.getPhotoFileName());
			}
		}
		super.onBackPressed();
	}

	private void inititalizeWish() {
		final String currentWishIdString = getIntent().getStringExtra(WISH_ID);
		UUID currentWishId = currentWishIdString != null ? UUID.fromString(currentWishIdString) : null;
		if (currentWishId != null) {
			// modify mode
			currentWish = MyWishesRepository.withContext(this).findById(currentWishId);
			tempWish.setPhotoFileName(currentWish.getPhotoFileName());
			tempWish.setDescription(currentWish.getDescription());
		} else {
			// create mode
			tempWish.setPhotoFileName(getIntent().getStringExtra(PHOTO_FILE_NAME));
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
		description.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				tempWish.setDescription(charSequence.toString());
				toggleFooterVisibility();
			}

			@Override
			public void afterTextChanged(Editable editable) {

			}
		});
		description.setText(tempWish.getDescription());
		toggleFooterVisibility();
	}

	private void toggleFooterVisibility() {
		footerView.setVisibility(tempWish.getDescription() != null && tempWish.getDescription().length() > 0
				? View.VISIBLE
				: View.INVISIBLE);
	}

	private void initPhotoThumbnail() {
		final ImageRepository imageRepository = ImageRepository.withContext(this);
		photoThumbnail = contentView.findViewById(R.id.activity_create_wish_content_photo_thumbnail);
		photoThumbnail.setPhoto(this, imageRepository.findAsFile(tempWish.getPhotoFileName()));
	}

	private void initFooter() {
		ImageView accept = footerView.findViewById(R.id.activity_create_wish_footer);
		accept.setOnClickListener(view -> {
			prepareWishToStrore();
			MyWishesRepository.withContext(this)
					.store(currentWish);

			Intent intent = new Intent(this, MyWishesActivity.class);
			startActivity(intent);
			finish();
		});
	}

	private void prepareWishToStrore() {
		if (currentWish == null) {
			// create mode
			currentWish = new Wish();
		} else {
			// modify mode
			if (!tempWish.hasPhoto() || !tempWish.getPhotoFileName().equals(currentWish.getPhotoFileName())) {
				ImageRepository.withContext(this)
						.delete(currentWish.getPhotoFileName());
			}
		}
		currentWish.setPhotoFileName(tempWish.getPhotoFileName());
		currentWish.setDescription(tempWish.getDescription());
	}
}
