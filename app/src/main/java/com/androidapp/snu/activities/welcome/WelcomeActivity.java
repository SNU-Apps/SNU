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
import android.widget.LinearLayout;

import com.androidapp.snu.R;
import com.androidapp.snu.activities.home.HomeActivity;
import com.androidapp.snu.components.bigbutton.BigButton;
import com.androidapp.snu.components.progress.LoadingSpinner;
import com.androidapp.snu.security.SharedPreferencesRepository;
import com.androidapp.snu.transformation.BlurBuilder;

import java.util.UUID;

public class WelcomeActivity extends Activity {

	private LinearLayout content;
	private LoadingSpinner loadingSpinner;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		initStylesAndBackground();
		init();
		checkCredentials();
	}

	private void init() {
		content = findViewById(R.id.welcome_view_text_welcome_content);
		loadingSpinner = new LoadingSpinner(content, this);
		loadingSpinner.show();
	}

	private void checkCredentials() {
		final UUID deviceRegistrationId = SharedPreferencesRepository.withContext(this).getDeviceRegistrationId();
		if (deviceRegistrationId != null) {
			startActivity(new Intent(getApplicationContext(), HomeActivity.class));
			return;
		}
		initWelcomeContent();
	}

	private void initWelcomeContent() {
		loadingSpinner.hide();
		final BigButton createSimpleAccountButton = new BigButton(this).setText("Account später anlegen").setIcon(R.drawable.profile_no_bottom_no_smile_bold_brown);
		final BigButton createFullAccountButton = new BigButton(this).setText("Account jetzt anlegen").setIcon(R.drawable.profile_no_bottom_smile_bold_brown);
		final BigButton continueWithAccountButton = new BigButton(this).setText("Ich habe schon einen Account").setIcon(R.drawable.profile_verified_no_bottom_smile_bold_brown);

		createSimpleAccountButton.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), WelcomeActivityCreateSimpleAccount.class)));
		createFullAccountButton.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), WelcomeActivityCreateFullAccount.class)));
		continueWithAccountButton.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), WelcomeActivityCreateSimpleAccount.class)));

		content.addView(createSimpleAccountButton);
		content.addView(createFullAccountButton);
		content.addView(continueWithAccountButton);
	}

	private void initStylesAndBackground() {
		getWindow().getDecorView().setBackground(BlurBuilder.blur(this, R.drawable.vintage_photo_small));
		getWindow().setStatusBarColor(Color.argb(100, 0, 0, 0));
		getWindow().setNavigationBarColor(Color.argb(100, 0, 0, 0));
	}
}