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

import android.app.Activity;

import com.androidapp.snu.R;
import com.androidapp.snu.activities.wishes.CreateWishActivity;

/**
 * Represents an HomeItem in our application. Each item has a name, id, full size image url and
 * thumbnail url.
 */
public class HomeItem {
	public static HomeItem[] ITEMS = new HomeItem[]{
			new HomeItem("Neuen Wunsch fotografieren...", R.drawable.camera_blur_with_icon, CreateWishActivity.class),
			new HomeItem("Neuen Wunsch beschreiben...", R.drawable.camera_blur_with_icon, CreateWishActivity.class),
			new HomeItem("Meine Wunschliste :)", R.drawable.camera_blur_with_icon, CreateWishActivity.class),
			new HomeItem("Wünsche von Freunden", R.drawable.camera_blur_with_icon, CreateWishActivity.class)
	};

	public static HomeItem getItem(int id) {
		for (HomeItem item : ITEMS) {
			if (item.getId() == id) {
				return item;
			}
		}
		return null;
	}

	private final String name;
	private final int imageViewId;
	private final Class<? extends Activity> targetActivity;

	private HomeItem(String name, int imageViewId, Class<? extends Activity> activity) {
		this.name = name;
		this.imageViewId = imageViewId;
		this.targetActivity = activity;
	}

	public int getId() {
		return name.hashCode() + imageViewId;
	}

	public String getName() {
		return name;
	}

	public Class<? extends Activity> getTargetActivity() {
		return targetActivity;
	}

	public int getImageViewId() {
		return imageViewId;
	}

	public int getActiveImageViewId() {
		return imageViewId;
	}
}
