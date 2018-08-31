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
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidapp.snu.R;
import com.androidapp.snu.activities.AbstractBaseActivity;
import com.androidapp.snu.components.polaroid.PhotoPolaroidThumbnail;
import com.androidapp.snu.repository.image.ImageRepository;
import com.androidapp.snu.repository.wish.Wish;
import com.androidapp.snu.repository.wish.WishRepository;

import java.util.List;

public class MyWishesActivity extends AbstractBaseActivity {
	private static final String fontPath = "fonts/handwrite.ttf";
	public static final int HEADER_IMAGE_ID = R.drawable.v3_1;
	public static final String HEADER_TEXT = "Meine Wunschliste";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public int getIconImageId() {
		return HEADER_IMAGE_ID;
	}

	@Override
	public String getHeaderText() {
		return HEADER_TEXT;
	}

	@Override
	protected View getContent() {
		Typeface typeface = Typeface.createFromAsset(getAssets(), fontPath);

		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		final List<Wish> currentWishes = WishRepository.withContext(this)
				.findAll();

		for (Wish wish : currentWishes) {
			LinearLayout wishLayout = new LinearLayout(this);

			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

			layoutParams.setMargins(10, 10, 0, 0);
			wishLayout.setLayoutParams(layoutParams);

			TextView text = new TextView(this);
			text.setTypeface(typeface);
			text.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
			text.setText(wish.getDescription());
			PhotoPolaroidThumbnail polaroidThumbnail = new PhotoPolaroidThumbnail(this);
			polaroidThumbnail.setPhoto(this, ImageRepository.withContext(this).findAsFile(wish.getPhotoFileName()));
			wishLayout.addView(polaroidThumbnail);
			wishLayout.addView(text);
			layout.addView(wishLayout);
		}
		return layout;
	}
}
