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
import android.os.Handler;
import android.widget.LinearLayout;

import com.androidapp.snu.R;
import com.androidapp.snu.activities.home.HomeActivity;
import com.androidapp.snu.components.progress.LoadingSpinner;
import com.androidapp.snu.repository.appCredentials.AppCredentials;
import com.androidapp.snu.repository.appCredentials.AppCredentialsRepository;
import com.androidapp.snu.transformation.BlurBuilder;

import java.util.UUID;

public class WelcomeActivityCreateAccount extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome_create_account);
		initStylesAndBackground();
		init();
	}

	private void init() {
		final LinearLayout mainContent = findViewById(R.id.welcome_view_create_account);
		new LoadingSpinner("SNU erstellt deinen Account ...", mainContent, this).show();
		new Handler().postDelayed(() -> {
			final AppCredentials appCredentials = new AppCredentials();
			appCredentials.setDeviceRegistrationId(UUID.randomUUID());
			AppCredentialsRepository.withContext(this).store(appCredentials);
			startActivity(new Intent(getApplicationContext(), HomeActivity.class));
		}, 3000);
	}

	private void initStylesAndBackground() {
		getWindow().getDecorView().setBackground(BlurBuilder.blur(this, R.drawable.vintage_photo_small));
		getWindow().setStatusBarColor(Color.argb(100, 0, 0, 0));
		getWindow().setNavigationBarColor(Color.argb(100, 0, 0, 0));
	}
}