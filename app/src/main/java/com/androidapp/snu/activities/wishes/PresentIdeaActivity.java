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
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidapp.snu.R;
import com.androidapp.snu.activities.AbstractBaseActivity;
import com.androidapp.snu.components.polaroid.PhotoPolaroidDialog;
import com.androidapp.snu.components.polaroid.PhotoPolaroidThumbnailSmall;
import com.androidapp.snu.repository.image.ImageRepository;
import com.androidapp.snu.repository.wish.Wish;
import com.androidapp.snu.repository.wish.WishRepository;

import java.io.File;
import java.util.UUID;

public class PresentIdeaActivity extends AbstractBaseActivity {
	public static final String WISH_ID = "detail:_wishId";
	private static final String fontPath = "fonts/handwrite.ttf";

	private LinearLayout contentView;
	private LinearLayout headerView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		headerView = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.component_polaroid_detail_thumbnail_header, null);
		contentView = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.activity_present_idea_content, null);
		super.onCreate(savedInstanceState);
	}

	@Override
	public int getIconImageId() {
		return 0;
	}

	@Override
	public String getHeaderText() {
		return null;
	}

	@Override
	protected View getHeader() {
		final Context context = this;
		final Wish currentWish = getCurrentWish();
		final File jpg = ImageRepository.withContext(this).findAsFile(currentWish.getPhotoFileName());
		final PhotoPolaroidThumbnailSmall thumbnail = headerView.findViewById(R.id.component_polaroid_image_detail_thumbnail_header);
		final TextView description = headerView.findViewById(R.id.component_polaroid_description_detail_thumbnail_header);

		thumbnail.setPhoto(context, jpg);
		thumbnail.setOnClickListener(v -> new PhotoPolaroidDialog(context, jpg).show());

		Typeface typeface = Typeface.createFromAsset(this.getAssets(), fontPath);
		description.setTypeface(typeface);
		description.setText(getCurrentWish().getDescription());
		return headerView;
	}

	@Override
	protected View getContent() {
		return contentView;
	}

	private Wish getCurrentWish() {
		final String currentWishIdString = getIntent().getStringExtra(WISH_ID);
		final UUID currentWishId = currentWishIdString != null ? UUID.fromString(currentWishIdString) : null;
		final WishRepository wishRepository = WishRepository.withContext(this);
		return wishRepository.findById(currentWishId);
	}
}
