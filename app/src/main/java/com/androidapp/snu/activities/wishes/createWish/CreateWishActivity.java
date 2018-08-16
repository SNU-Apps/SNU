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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidapp.snu.R;
import com.androidapp.snu.activities.home.AbstractHomeTransitionActivity;
import com.androidapp.snu.activities.wishes.PhotoWishActivity;
import com.androidapp.snu.components.camera.PhotoThumbnail;

public class CreateWishActivity extends AbstractHomeTransitionActivity {
	public static final int HEADER_IMAGE_ID = R.drawable.v1_1;
	public static final int ICON_IMAGE_ID = R.drawable.v1;
	public static final String HEADER_TEXT = "Neuen Wunsch...";
	public static final String PHOTO_PATH = "detail:_photoId";

	LinearLayout contentView;
	LinearLayout footerView;
	PhotoThumbnail photoThumbnail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
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
		initHeadlineAndDescription();
		initPhotoThumbnail();
		return contentView;
	}

	@Override
	protected View getFooter() {
		initFooter();
		return footerView;
	}

	private void initHeadlineAndDescription() {
		TextView headline = contentView.findViewById(R.id.activity_create_wish_content_headline);
		EditText description = contentView.findViewById(R.id.activity_create_wish_content_description);
		Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/handwrite.ttf");
		headline.setTypeface(typeface);
		description.setTypeface(typeface);
	}

	private void initPhotoThumbnail() {
		photoThumbnail = contentView.findViewById(R.id.activity_create_wish_content_photo_thumbnail);
		photoThumbnail.setPhoto(this, getIntent().getStringExtra(PHOTO_PATH));
		photoThumbnail.setOnClickListener(view -> {
			PhotoWishActivity.start(this);
			//finish();
		});
	}

	private void initFooter() {
		Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/handwrite.ttf");
		TextView footerText = footerView.findViewById(R.id.activity_create_wish_footer);
		footerText.setTypeface(typeface);
		footerText.setOnClickListener(view -> finish());
	}
}
