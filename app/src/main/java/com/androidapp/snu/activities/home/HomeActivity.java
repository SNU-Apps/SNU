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
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.widget.LinearLayout;

import com.androidapp.snu.R;
import com.androidapp.snu.activities.wishes.CreateWishActivity;
import com.androidapp.snu.activities.wishes.PhotoWishActivity;

public class HomeActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Window window = getWindow();
		window.setStatusBarColor(Color.argb(255, 255, 255, 255));

		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		initActivities();
	}

	private void initActivities() {
		LinearLayout mainView = findViewById(R.id.home_view);
		createFirstRow(mainView);

		/*
		mainView.addView(ActivityStartView.createForActivity(new PhotoWishActivity(), this));
		mainView.addView(ActivityStartView.createForActivity(new CreateWishActivity(), this));
		mainView.addView(ActivityStartView.createForActivity(new MyWishesActivity(), this));
		*/
	}

	private void createFirstRow(LinearLayout mainView) {
		LinearLayout firstRow = new LinearLayout(this);
		firstRow.setOrientation(LinearLayout.HORIZONTAL);

		firstRow.addView(ActivityStartView.createForActivity(new PhotoWishActivity(), this));
		firstRow.addView(ActivityStartView.createForActivity(new CreateWishActivity(), this));
		mainView.addView(firstRow);

		//horizontal split --> same weight for each child
		LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) firstRow.getChildAt(0).getLayoutParams();
		layoutParams.weight = 1;
		firstRow.getChildAt(0).setLayoutParams(layoutParams);
		firstRow.getChildAt(1).setLayoutParams(layoutParams);
	}
}

/*
public static HomeItem[] ITEMS = new HomeItem[]{
		new HomeItem("Neuen Wunsch fotografieren...", R.drawable.camera_wish_blur_with_icon, R.drawable.camera_wish_blur,
			PhotoWishActivity.class, Color.argb(255, 78, 135, 75)),
		new HomeItem("Neuen Wunsch beschreiben...", R.drawable.manual_wish_blur_with_icon, R.drawable.manual_wish_blur,
			CreateWishActivity.class, Color.argb(255, 73, 114, 159)),
		new HomeItem("Meine Wunschliste", R.drawable.my_wishlist_blur_with_icon, R.drawable.my_wishlist_blur,
			MyWishesActivity.class, Color.argb(255, 144, 137, 48)),
		new HomeItem("Wünsche von Freunden", R.drawable.friends_wishlist_blur_with_icon, R.drawable.friends_wishlist_blur,
			CreateWishActivity.class, Color.argb(255, 139, 46, 124)),
		new HomeItem("Geschenkideensammlung", R.drawable.ideas_blur_with_icon, R.drawable.ideas_blur,
			CreateWishActivity.class, Color.argb(255, 190, 85, 38))
	};
 */