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
import android.support.v4.app.ActivityCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidapp.snu.R;
import com.androidapp.snu.activities.home.AbstractHomeTransitionActivity;
import com.androidapp.snu.activities.home.HomeItem;
import com.androidapp.snu.components.camera.PhotoThumbnail;

public class CreateWishActivity extends AbstractHomeTransitionActivity implements View.OnClickListener{
	public static final String PHOTO_PATH = "detail:_photoId";

	PhotoThumbnail photoThumbnail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		photoThumbnail.setOnClickListener(this);
	}

	@Override
	protected View getContent(HomeItem currentItem) {
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setPadding(20, 0, 20, 0);

		final String photoPath = getIntent().getStringExtra(PHOTO_PATH);
		photoThumbnail = new PhotoThumbnail(this);
		photoThumbnail.setPhoto(this, photoPath);

		TextView text = new TextView(this);
		text.setGravity(Gravity.CENTER_HORIZONTAL);
		text.setPadding(0, 20, 0, 25);
		text.setText("Ich wünsche mir...");
		text.setTextColor(currentItem.getSceneMainColor());
		text.setTextSize(28);
		Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/handwrite.ttf");
		text.setTypeface(typeface);

		TextView text2 = new TextView(this);
		text2.setText(
			"\n\nein kleines, witziges Schnugg." +
				"\n\n\nWICHIG:  Es muss seeehr klein sein!");
		text2.setTextColor(currentItem.getSceneMainColor());
		text2.setTextSize(22);
		text2.setTypeface(typeface);

		layout.addView(text);
		layout.addView(photoThumbnail);

		ImageView pen = new ImageView(this);
		pen.setImageResource(R.drawable.pen_grey_200px);
		pen.setPadding(0, 80, 0, 50);
		layout.addView(pen);

		//layout.addView(text2);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins(80, 50, 80, 0);
		//layout.setBackgroundResource(R.drawable.shadow);
		layout.setLayoutParams(layoutParams);

		return layout;
	}

	@Override
	protected View getPreFooter(HomeItem currentItem) {
		return null;
	}

	@Override
	protected View getFooter(HomeItem currentItem) {
		TextView text = new TextView(this);
		text.setText("Weiter zur Vorschau");
		text.setTextAppearance(this, R.style.TextAppearance_MaterialComponents_Headline5);
		text.setTextColor(currentItem.getSceneMainColor());
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
		intent.putExtra(CreateWishActivity.ENABLE_TRANSITION, false);
		intent.putExtra(CreateWishActivity.EXTRA_PARAM_ID, HomeItem.ITEMS[0].getId());
		finish();
		ActivityCompat.startActivity(this, intent, null);
	}
}
