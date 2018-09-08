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

package com.androidapp.snu.activities.welcome;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidapp.snu.R;
import com.androidapp.snu.activities.home.HomeActivity;
import com.androidapp.snu.components.progress.LoadingSpinner;
import com.androidapp.snu.transformation.BlurBuilder;

public class WelcomeActivitySetupStep1 extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome_setup_step_1);
		initStylesAndBackground();
		initContent();
	}

	private void initContent() {
		final LinearLayout mainContent = findViewById(R.id.welcome_setup_step_1);
		final TextView next = findViewById(R.id.welcome_setup_step_1_continue);
		next.setText(Html.fromHtml("<u>" + next.getText() + "</u>"));
		next.setOnClickListener(v -> {
			mainContent.removeAllViews();
			new LoadingSpinner("", mainContent, this).show();
			Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		});
	}

	private void initStylesAndBackground() {
		getWindow().getDecorView().setBackground(BlurBuilder.blur(this, R.drawable.vintage_photo_small));
		getWindow().setStatusBarColor(Color.argb(100, 0, 0, 0));
		getWindow().setNavigationBarColor(Color.argb(100, 0, 0, 0));
	}
}