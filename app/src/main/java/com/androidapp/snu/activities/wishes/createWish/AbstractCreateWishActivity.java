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
import com.androidapp.snu.activities.home.AbstractHomeTransitionActivity;
import com.androidapp.snu.components.image.ImageRepository;
import com.androidapp.snu.components.polaroid.PhotoPolaroidThumbnail;
import com.androidapp.snu.components.utils.KeyboardUtils;

public abstract class AbstractCreateWishActivity extends AbstractHomeTransitionActivity {
	public static final String PHOTO_FILE_NAME = "detail:_photoFileName";
	public static final int ICON_IMAGE_ID = R.drawable.v1;
	public static final String HEADER_TEXT = "Neuen Wunsch...";
	private static final String fontPath = "fonts/handwrite.ttf";

	LinearLayout contentView;
	LinearLayout footerView;
	PhotoPolaroidThumbnail photoThumbnail;

	Wish currentWish = new Wish();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		currentWish.setPhotoFileName(getIntent().getStringExtra(PHOTO_FILE_NAME));
		contentView = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.activity_create_wish_content, null);
		footerView = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.activity_create_wish_footer, null);
		super.onCreate(savedInstanceState);
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
				currentWish.setDescription(charSequence.toString());
				footerView.setVisibility(currentWish.getDescription() != null && currentWish.getDescription().length() > 0
						? View.VISIBLE
						: View.INVISIBLE);
			}

			@Override
			public void afterTextChanged(Editable editable) {

			}
		});
	}

	private void initPhotoThumbnail() {
		final ImageRepository imageRepository = ImageRepository.withContext(this);
		photoThumbnail = contentView.findViewById(R.id.activity_create_wish_content_photo_thumbnail);
		photoThumbnail.setPhoto(this, imageRepository.findAsFile(currentWish.getPhotoFileName()));
	}

	private void initFooter() {
		Typeface typeface = Typeface.createFromAsset(getAssets(), fontPath);
		ImageView accept = footerView.findViewById(R.id.activity_create_wish_footer);
		footerView.setVisibility(View.INVISIBLE);
		accept.setOnClickListener(view -> finish());
	}
}