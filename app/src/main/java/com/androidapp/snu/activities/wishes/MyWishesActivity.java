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

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidapp.snu.R;
import com.androidapp.snu.activities.AbstractBaseActivity;
import com.androidapp.snu.activities.wishes.createWish.CreateWishActivity;
import com.androidapp.snu.components.polaroid.PhotoPolaroidDetailThumbnail;
import com.androidapp.snu.repository.image.ImageRepository;
import com.androidapp.snu.repository.wish.Wish;
import com.androidapp.snu.repository.wish.WishRepository;

import java.io.File;
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
	protected View getHeader() {
		TextView header = new TextView(this);
		Typeface typeface = Typeface.createFromAsset(this.getAssets(), fontPath);
		header.setTypeface(typeface);
		header.setText("Meine Wunschliste");
		header.setTextSize(TypedValue.COMPLEX_UNIT_SP, 34);
		header.setTextColor(Color.argb(255, 214, 214, 214));
		return header;
	}

	@Override
	protected View getContent() {
		final ImageRepository imageRepository = ImageRepository.withContext(this);
		final WishRepository wishRepository = WishRepository.withContext(this);

		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		final List<Wish> currentWishes = wishRepository.findAll();

		for (Wish currentWish : currentWishes) {
			final Context context = this;
			final PhotoPolaroidDetailThumbnail wish = new PhotoPolaroidDetailThumbnail(this);
			final File photo = imageRepository.findAsFile(currentWish.getPhotoFileName());
			wish.setPhoto(this, photo);
			wish.setDescription(this, currentWish.getDescription());
			layout.addView(wish);

			wish.setOnClickListener(v -> {
				Intent intent = new Intent(context, CreateWishActivity.class);
				intent.putExtra(CreateWishActivity.WISH_ID, currentWish.getWishId().toString());
				startActivity(intent);
			});
		}
		return layout;
	}
}
