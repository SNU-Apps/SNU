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

package com.androidapp.snu.activities;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.androidapp.snu.R;
import com.androidapp.snu.components.utils.KeyboardUtils;
import com.androidapp.snu.transformation.BlurBuilder;

public abstract class AbstractBaseActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Window window = getWindow();
		window.setStatusBarColor(Color.argb(200, 0, 0, 0));
		getWindow().setNavigationBarColor(Color.argb(200, 0, 0, 0));

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_home_base_layout);
		setContent();

		AppCompatImageView mainLayout = findViewById(R.id.main_scene_layout_background_image);
		mainLayout.setOnClickListener(view -> KeyboardUtils.forceCloseKeyboard(mainLayout));

		setDefaultBackGroundImage();
	}

	public abstract int getIconImageId();

	public abstract String getHeaderText();

	protected View getContent() {
		return null;
	}

	protected View getFooter() {
		return null;
	}

	protected void setBackGroundImage(Bitmap bitmap) {
		AppCompatImageView mainLayout = findViewById(R.id.main_scene_layout_background_image);
		Drawable d = BlurBuilder.blur(this, bitmap, 0.4f, 0.4f, 7.5f);
		d.setAlpha(160);
		mainLayout.setBackground(d);
	}

	protected void setDefaultBackGroundImage() {
		AppCompatImageView mainLayout = findViewById(R.id.main_scene_layout_background_image);
		Drawable d = BlurBuilder.blur(this, R.drawable.vintage_photo_small);
		d.setAlpha(210);
		mainLayout.setBackground(d);
	}

	private void setContent() {
		LinearLayout content = findViewById(R.id.view_content);
		LinearLayout footer = findViewById(R.id.view_footer);

		View contentView = getContent();
		if (contentView != null) {
			content.addView(contentView);
		}

		View footerView = getFooter();
		if (footerView != null) {
			footer.addView(footerView);
		}
	}
}
