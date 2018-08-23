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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.androidapp.snu.R;
import com.androidapp.snu.activities.home.AbstractHomeTransitionActivity;
import com.androidapp.snu.activities.wishes.createWish.CreateWishActivity;
import com.androidapp.snu.components.camera.CameraCaptureActivity;

public class PhotoWishActivity extends AbstractHomeTransitionActivity {
	public static final int HEADER_IMAGE_ID = R.drawable.v2;
	public static final String HEADER_TEXT = "...fotografieren";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		capturePhoto();
	}

	@Override
	protected int getHeaderImageId() {
		return HEADER_IMAGE_ID;
	}

	@Override
	protected int getIconImageId() {
		return HEADER_IMAGE_ID;
	}

	@Override
	protected String getHeaderText() {
		return HEADER_TEXT;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CreateWishActivity.PICK_IMAGE_FROM_CAMERA) {
			if (resultCode == Activity.RESULT_OK) {
				String fileName = data.getStringExtra(CreateWishActivity.PHOTO_FILE_NAME);
				Intent intent = new Intent(this, CreateWishActivity.class);
				intent.putExtra(CreateWishActivity.PHOTO_FILE_NAME, fileName);
				startActivity(intent);
			}
		}
		finish();
	}

	private void capturePhoto() {
		Intent intent = new Intent(this, CameraCaptureActivity.class);
		startActivityForResult(intent, CreateWishActivity.PICK_IMAGE_FROM_CAMERA);
	}
}
