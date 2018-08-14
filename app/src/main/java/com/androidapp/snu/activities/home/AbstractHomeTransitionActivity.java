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
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.androidapp.snu.R;
import com.squareup.picasso.Picasso;

public abstract class AbstractHomeTransitionActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Window window = getWindow();
		window.setStatusBarColor(Color.argb(255, 0, 0, 0));
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.homeactivityscene);

		setHeaderImage();
		setContent();
	}

	protected abstract int getHeaderImageId();
	protected abstract String getHeaderText();
	protected abstract View getContent();
	protected abstract View getPreFooter();
	protected abstract View getFooter();

	private void setHeaderImage() {
		ImageView headerImageView = findViewById(R.id.imageview_header);
		Picasso.with(headerImageView.getContext())
			.load(getHeaderImageId())
			.noFade()
			.noPlaceholder()
			.into(headerImageView);
	}

	private void setContent() {
		LinearLayout content = findViewById(R.id.view_content);
		LinearLayout preFooter = findViewById(R.id.textview_pre_footer);
		LinearLayout footer = findViewById(R.id.textview_footer);
		preFooter.setGravity(Gravity.CENTER_HORIZONTAL);
		footer.setGravity(Gravity.CENTER_HORIZONTAL);

		View contentView = getContent();
		if (contentView != null) {
			content.addView(contentView);
		}

		View preFooterView = getPreFooter();
		if (preFooterView != null) {
			preFooter.addView(preFooterView);
		}

		View footerView = getFooter();
		if (footerView != null) {
			footer.addView(footerView);
		}
	}
}
