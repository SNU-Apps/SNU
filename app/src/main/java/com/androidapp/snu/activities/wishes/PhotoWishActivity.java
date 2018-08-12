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

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.androidapp.snu.R;
import com.androidapp.snu.components.camera.CameraFragment;
import com.androidapp.snu.activities.home.AbstractHomeTransitionActivity;
import com.androidapp.snu.activities.home.HomeItem;

public class PhotoWishActivity extends AbstractHomeTransitionActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);

		if (null == savedInstanceState) {
			getSupportFragmentManager().beginTransaction()
				.replace(R.id.photoCcontainer, CameraFragment.newInstance())
				.commit();
		}
	}

	@Override
	protected View getContent(HomeItem currentItem) {
		//special handling for camera activity
		return null;
	}

	@Override
	protected View getPreFooter(HomeItem currentItem) {
		//special handling for camera activity
		return null;
	}

	@Override
	protected View getFooter(HomeItem currentItem) {
		//special handling for camera activity
		return null;
	}

	@Override
	protected int getStatusBarColor(HomeItem currentItem) {
		return currentItem.getSceneMainColor();
	}
}
