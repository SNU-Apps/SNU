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

package com.androidapp.snu.activities.home;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.androidapp.snu.R;
import com.androidapp.snu.components.utils.KeyboardUtils;
import com.androidapp.snu.transformation.BlurBuilder;

public abstract class AbstractHomeTransitionActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Window window = getWindow();
		window.setStatusBarColor(Color.argb(100, 0, 0, 0));
		getWindow().setNavigationBarColor(Color.argb(100, 0, 0, 0));

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_home_base_layout);
		setContent();

		RelativeLayout mainLayout = findViewById(R.id.main_scene_layout);
		mainLayout.setOnClickListener(view -> KeyboardUtils.forceCloseKeyboard(mainLayout));

		setDefaultBackGroundImage();
	}

	protected abstract int getIconImageId();

	protected abstract String getHeaderText();

	protected View getContent() {
		return null;
	}

	protected View getFooter() {
		return null;
	}

	protected void setBackGroundImage(Bitmap bitmap) {
		RelativeLayout mainLayout = findViewById(R.id.main_scene_layout);
		Drawable d = BlurBuilder.blur(this, bitmap);
		d.setAlpha(170);
		mainLayout.setBackground(d);
	}

	protected void setDefaultBackGroundImage() {
		RelativeLayout mainLayout = findViewById(R.id.main_scene_layout);
		Drawable d = BlurBuilder.blur(this, R.drawable.vintage_photo_small);
		d.setAlpha(170);
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
