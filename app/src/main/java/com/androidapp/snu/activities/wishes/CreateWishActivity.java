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

package com.androidapp.snu.activities.wishes;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidapp.snu.R;
import com.androidapp.snu.activities.home.AbstractHomeTransitionActivity;
import com.androidapp.snu.components.camera.PhotoThumbnail;

public class CreateWishActivity extends AbstractHomeTransitionActivity implements View.OnClickListener {
	public static final int HEADER_IMAGE_ID = R.drawable.v1_1;
	public static final int ICON_IMAGE_ID = R.drawable.v1;
	public static final String HEADER_TEXT = "Neuen Wunsch...";
	public static final String PHOTO_PATH = "detail:_photoId";

	LinearLayout contentView;
	PhotoThumbnail photoThumbnail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		contentView = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.activity_create_wish_content, null);
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
		photoThumbnail = contentView.findViewById(R.id.activity_create_wish_content_photo_thumbnail);
		TextView headline = contentView.findViewById(R.id.activity_create_wish_content_headline);
		TextView description = contentView.findViewById(R.id.activity_create_wish_content_description);
		Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/handwrite.ttf");
		headline.setTypeface(typeface);
		description.setTypeface(typeface);

		final String photoPath = getIntent().getStringExtra(PHOTO_PATH);
		photoThumbnail.setPhoto(this, photoPath);
		photoThumbnail.setOnClickListener(this);
		return contentView;
	}

	@Override
	protected void onPostCreate(@Nullable Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
	}

	@Override
	protected View getPreFooter() {
		return null;
	}

	@Override
	protected View getFooter() {
		TextView text = new TextView(this);
		text.setText("Weiter zur Vorschau");
		text.setTextAppearance(this, R.style.TextAppearance_MaterialComponents_Headline5);

		LinearLayout footer = new LinearLayout(this);
		footer.addView(text);

		text.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});

		return footer;
	}

	@Override
	public void onClick(View view) {
		Intent intent = new Intent(this, PhotoWishActivity.class);
		finish();
		ActivityCompat.startActivity(this, intent, null);
	}
}
