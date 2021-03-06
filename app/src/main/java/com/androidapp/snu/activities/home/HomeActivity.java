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
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.androidapp.snu.R;
import com.androidapp.snu.activities.wishes.FriendsWishesActivity;
import com.androidapp.snu.activities.wishes.MyWishesActivity;
import com.androidapp.snu.activities.wishes.PhotoWishActivity;
import com.androidapp.snu.activities.wishes.createWish.CreateWishActivity;
import com.androidapp.snu.repository.account.Account;
import com.androidapp.snu.repository.account.AccountRepository;
import com.androidapp.snu.security.SharedPreferencesRepository;
import com.androidapp.snu.transformation.BlurBuilder;
import com.google.android.gms.ads.MobileAds;

public class HomeActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		initActivities();
		initScrollActivationListener();
		initStylesAndBackground();

		// Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
		MobileAds.initialize(this, "ca-app-pub-3940256099942544/6300978111");

		final Account account = AccountRepository.withContext(this).get();
		final ImageView accountIcon = findViewById(R.id.main_scene_layout_info_bar_account_icon);
		if (account != null) {
			accountIcon.setImageResource(account.getEmail() != null ? R.drawable.profile_no_bottom_smile_bold_brown : R.drawable.profile_no_bottom_no_smile_bold_brown);
			accountIcon.setOnClickListener(v -> {
				SharedPreferencesRepository.withContext(this).clear();
			});

			CardView infoIcon = findViewById(R.id.main_scene_layout_info_bar_account_info_icon);
			if (account.getEmail() == null || !account.getConfirmed()) {
				infoIcon.setVisibility(View.VISIBLE);
			}
		} else {
			//display login page
			accountIcon.setImageResource(R.drawable.profile_verified_no_bottom_smile_bold_brown);
			accountIcon.setOnClickListener(v -> {
				SharedPreferencesRepository.withContext(this).clear();
			});
		}
	}

	@Override
	public void onBackPressed() {
		//noop
	}

	private void initActivities() {
		LinearLayout mainView = findViewById(R.id.home_view);
		createFirstRow(mainView);

		mainView.addView(ActivityStartView.createForActivity(new MyWishesActivity(), this));
		mainView.addView(ActivityStartView.createForActivity(new FriendsWishesActivity(), this));
	}

	private void createFirstRow(LinearLayout mainView) {
		LinearLayout firstRow = new LinearLayout(this);
		firstRow.setOrientation(LinearLayout.HORIZONTAL);

		firstRow.addView(
				ActivityStartView
						.createForActivity(new CreateWishActivity(), this)
						.withCustomPadding(16, 0, 8, 0));

		firstRow.addView(
				ActivityStartView
						.createForActivity(new PhotoWishActivity(), this)
						.withCustomPadding(8, 0, 16, 0));

		mainView.addView(firstRow);

		//horizontal split --> same weight for each child
		LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) firstRow.getChildAt(0).getLayoutParams();
		layoutParams.weight = 1;
		firstRow.getChildAt(0).setLayoutParams(layoutParams);
		firstRow.getChildAt(1).setLayoutParams(layoutParams);
	}

	private void initScrollActivationListener() {
		ScrollView scrollView = findViewById(R.id.home_layout);
		scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				ScrollView scrollView = findViewById(R.id.home_layout);
				LinearLayout mainView = findViewById(R.id.home_view);
				int childHeight = mainView.getHeight();
				boolean isScrollable = scrollView.getHeight() < childHeight + scrollView.getPaddingTop() + scrollView.getPaddingBottom();
				if (isScrollable) {
					FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mainView.getLayoutParams();
					layoutParams.gravity = Gravity.TOP;
					mainView.setLayoutParams(layoutParams);
					//remove the listener as soon as we know, that we need to scroll
					scrollView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
				}
			}
		});
	}

	private void initStylesAndBackground() {
		getWindow().getDecorView().setBackground(BlurBuilder.blur(this, R.drawable.vintage_photo_small));
		getWindow().setStatusBarColor(Color.argb(100, 0, 0, 0));
		getWindow().setNavigationBarColor(Color.argb(100, 0, 0, 0));
	}
}