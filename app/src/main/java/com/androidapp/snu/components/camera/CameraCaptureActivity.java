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

package com.androidapp.snu.components.camera;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.androidapp.snu.R;
import com.androidapp.snu.activities.wishes.createWish.CreateWishActivity;
import com.androidapp.snu.repository.image.ImageRepository;
import com.androidapp.snu.components.utils.BitmapUtils;

import java.io.File;

public class CameraCaptureActivity extends AppCompatActivity {
	public interface PhotoCreatedCallback {
		void onPhotoCreated(final File file, final int rotation);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getWindow().setStatusBarColor(Color.argb(255, 0, 0, 0));
		getWindow().setNavigationBarColor(Color.argb(100, 0, 0, 0));
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);

		CameraFragment cameraFragment =
				CameraFragment
						.newInstance()
						.withPhotoCreatedHandler(this::onPhotoCreated);

		if (null == savedInstanceState) {
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.photoCcontainer, cameraFragment)
					.commit();
		}
	}

	private void onPhotoCreated(File file, int rotation) {
		initRotate(file, rotation);
		Intent returnIntent = new Intent();
		returnIntent.putExtra(CreateWishActivity.PHOTO_FILE_NAME, file.getName());
		setResult(Activity.RESULT_OK, returnIntent);
		finish();
	}

	private void initRotate(File file, int rotation) {
		//initial rotate to fit in polaroid
		final ImageRepository imageRepository = ImageRepository.withContext(this);
		Bitmap bitmap = imageRepository.findAsBitmap(file.getName());
		imageRepository
				.store(BitmapUtils.getRotatedBitmap(bitmap, rotation), file.getName());
	}
}
