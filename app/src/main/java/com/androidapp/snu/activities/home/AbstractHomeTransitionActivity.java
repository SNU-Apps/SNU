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

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidapp.snu.R;
import com.squareup.picasso.Picasso;

public abstract class AbstractHomeTransitionActivity extends AppCompatActivity {

	// Extra name for the ID parameter
	public static final String EXTRA_PARAM_ID = "detail:_id";

	// Used for activity scene transitions
	public static final String VIEW_NAME_HEADER_IMAGE = "detail:header:image";
	public static final String VIEW_NAME_HEADER_TITLE = "detail:header:title";

	private ImageView headerImageView;
	private TextView headerTitle;
	private HomeItem currentItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.homeactivityscene);

		currentItem = getCurrentItem();
		Window window = getWindow();
		window.setStatusBarColor(getStatusBarColor(currentItem));

		headerImageView = findViewById(R.id.imageview_header);
		headerTitle = findViewById(R.id.textview_title);
		LinearLayout contentView = findViewById(R.id.view_content);
		LinearLayout preFooter = findViewById(R.id.textview_pre_footer);
		preFooter.setGravity(Gravity.CENTER_HORIZONTAL);
		LinearLayout footer = findViewById(R.id.textview_footer);
		footer.setGravity(Gravity.CENTER_HORIZONTAL);
		//footer.setBackgroundColor(Color.argb(15, 0, 0, 0));
		footer.setBackgroundColor(Color.argb(255, 255, 255, 255));
		//contentView.setBackgroundColor(Color.argb(255, 255, 255, 255));
		findViewById(R.id.main_scene_layout).setBackgroundColor(Color.argb(255, 255, 255, 255));

		ViewCompat.setTransitionName(headerImageView, VIEW_NAME_HEADER_IMAGE);
		ViewCompat.setTransitionName(headerTitle, VIEW_NAME_HEADER_TITLE);

		loadHeaderImage();
		View content = getContent(currentItem);
		if (content != null) {
			contentView.addView(content);
		}
		View preFooterView = getPreFooter(currentItem);
		if (preFooterView != null) {
			preFooter.addView(preFooterView);
		}
		View footerView = getFooter(currentItem);
		if (footerView != null) {
			footer.addView(footerView);
		}
	}

	protected abstract View getContent(HomeItem currentItem);

	protected abstract View getPreFooter(HomeItem currentItem);

	protected abstract View getFooter(HomeItem currentItem);

	protected int getStatusBarColor(HomeItem currentItem) {
		//default is "black"
		return Color.argb(255, 0, 0, 0);
	}

	private HomeItem getCurrentItem() {
		return HomeItem.getItem(getIntent().getIntExtra(EXTRA_PARAM_ID, 0));
	}

	private void loadHeaderImage() {
		headerTitle.setText(currentItem.getName());

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && addTransitionListener()) {
			// If we're running on Lollipop and we have added a listener to the shared element
			// transition, load the thumbnail. The listener will load the full-size image when
			// the transition is complete.
			loadThumbnail();
		} else {
			// If all other cases we should just load the full-size image now
			loadFullSizeImage();
		}
	}

	private void loadThumbnail() {
		Picasso.with(headerImageView.getContext())
			.load(currentItem.getImageViewId())
			.noFade()
			.into(headerImageView);
	}

	private void loadFullSizeImage() {
		Picasso.with(headerImageView.getContext())
			.load(currentItem.getActiveImageViewId())
			.noFade()
			.noPlaceholder()
			.into(headerImageView);
	}

	/**
	 * Try and add a {@link Transition.TransitionListener} to the entering shared element
	 * {@link Transition}. We do this so that we can load the full-size image after the transition
	 * has completed.
	 *
	 * @return true if we were successful in adding a listener to the enter transition
	 */
	private boolean addTransitionListener() {
		final Transition transition = getWindow().getSharedElementEnterTransition();

		if (transition != null) {
			// There is an entering shared element transition so add a listener to it
			transition.addListener(new Transition.TransitionListener() {
				@Override
				public void onTransitionEnd(Transition transition) {
					loadFullSizeImage();
					transition.removeListener(this);
				}

				@Override
				public void onTransitionStart(Transition transition) {
					//no-op
				}

				@Override
				public void onTransitionCancel(Transition transition) {
					// Make sure we remove ourselves as a listener
					transition.removeListener(this);
				}

				@Override
				public void onTransitionPause(Transition transition) {
					// No-op
				}

				@Override
				public void onTransitionResume(Transition transition) {
					// No-op
				}
			});
			return true;
		}
		return false;
	}
}
