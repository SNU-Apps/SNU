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
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.androidapp.snu.R;
import com.androidapp.snu.activities.home.AbstractHomeTransitionActivity;
import com.androidapp.snu.activities.wishes.createWish.CreateWishActivity;
import com.androidapp.snu.activities.wishes.createWish.Wish;
import com.androidapp.snu.components.camera.CameraFragment;
import com.androidapp.snu.components.utils.BitmapUtils;

import java.io.File;

public class PhotoWishActivity extends AbstractHomeTransitionActivity {
	public static final int HEADER_IMAGE_ID = R.drawable.v2;
	public static final String HEADER_TEXT = "...fotografieren";

	private Wish wish;

	public interface PhotoCreatedCallback {
		void onPhotoCreated(final File file);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);

		CameraFragment cameraFragment =
				CameraFragment
						.newInstance()
						.withPhotoCreatedHandler(this::openCreateWishActivtiy);

		if (null == savedInstanceState) {
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.photoCcontainer, cameraFragment)
					.commit();
		}

		wish = Wish.fromIntent(getIntent());
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
	protected View getContent() {
		return null;
	}

	@Override
	protected View getFooter() {
		return null;
	}

	private void openCreateWishActivtiy(File file) {
		file = initRotate(file);
		wish.setPhotoPath(file.getPath());
		Intent intent = new Intent(this, CreateWishActivity.class);
		Wish.addToIntent(wish, intent);
		ActivityCompat.startActivity(this, intent, null);
		finish();
	}

	private File initRotate(File file) {
		//initial rotate to fit in polaroid
		Bitmap bitmap = BitmapUtils.getRotatedBitmap(
				BitmapUtils.loadBitmapAndDeleteFile(file.getPath()), 90);
		return BitmapUtils.storeAsJpg(bitmap, false, this);
	}
}
