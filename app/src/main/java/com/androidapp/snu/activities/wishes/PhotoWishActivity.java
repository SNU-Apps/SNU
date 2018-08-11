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

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidapp.snu.R;
import com.androidapp.snu.activities.home.AbstractHomeTransitionActivity;
import com.androidapp.snu.activities.home.HomeItem;

public class PhotoWishActivity extends AbstractHomeTransitionActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected View getContent(HomeItem currentItem) {
		LinearLayout layout = new LinearLayout(this);
		layout.setBackgroundResource(R.drawable.polaroid_big);
		return layout;
	}

	@Override
	protected View getPreFooter(HomeItem currentItem) {
		TextView takePhoto = new TextView(this);
		takePhoto.setText("Foto aufnehmen");
		takePhoto.setTextAppearance(this, R.style.TextAppearance_MaterialComponents_Headline5);
		takePhoto.setTextColor(currentItem.getSceneMainColor());

		LinearLayout footer = new LinearLayout(this);
		footer.addView(takePhoto);

		return footer;
	}

	@Override
	protected View getFooter(HomeItem currentItem) {
		TextView text = new TextView(this);
		text.setText("Infos hinzufügen");
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
}