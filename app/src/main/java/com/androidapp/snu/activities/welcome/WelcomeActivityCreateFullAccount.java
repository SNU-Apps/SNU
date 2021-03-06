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
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.androidapp.snu.R;
import com.androidapp.snu.activities.home.HomeActivity;
import com.androidapp.snu.components.bigbutton.BigButton;
import com.androidapp.snu.components.progress.LoadingSpinner;
import com.androidapp.snu.components.utils.KeyboardUtils;
import com.androidapp.snu.repository.account.Account;
import com.androidapp.snu.repository.account.AccountRepository;
import com.androidapp.snu.security.SharedPreferencesRepository;
import com.androidapp.snu.transformation.BlurBuilder;

import java.util.UUID;

public class WelcomeActivityCreateFullAccount extends Activity {

	private TextInputEditText name;
	private TextInputEditText email;
	private TextInputEditText password;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome_create_full_account);

		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		initStylesAndBackground();
		init();
	}

	private void init() {
		final LinearLayout mainView = findViewById(R.id.welcome_layout_main_view_create_full_account);
		final LinearLayout footer = findViewById(R.id.welcome_layout_main_view_create_full_account_footer);

		name = findViewById(R.id.welcome_layout_view_create_full_account_fullName);
		email = findViewById(R.id.welcome_layout_view_create_full_account_email);
		password = findViewById(R.id.welcome_layout_view_create_full_account_password);

		KeyboardUtils.addKeyboardToggleListener(this, name::setCursorVisible);
		KeyboardUtils.addKeyboardToggleListener(this, email::setCursorVisible);
		KeyboardUtils.addKeyboardToggleListener(this, password::setCursorVisible);
		KeyboardUtils.addKeyboardToggleListener(this, isVisible ->
				findViewById(R.id.welcome_layout_main_view_create_full_account_footer).setVisibility(isVisible ? View.GONE : View.VISIBLE));

		mainView.setOnClickListener(view -> KeyboardUtils.forceCloseKeyboard(mainView));

		final BigButton createAccountButton = new BigButton(this).setText("Account jetzt anlegen").setIcon(R.drawable.profile_no_bottom_smile_bold_brown);
		createAccountButton.setOnClickListener(view -> {
			mainView.removeAllViews();
			footer.removeAllViews();
			new LoadingSpinner("SNU erstellt deinen Account ...", mainView, this).show();
			new Handler().postDelayed(() -> {
				createAccount();
				startActivity(new Intent(getApplicationContext(), HomeActivity.class));
			}, 3000);
		});

		footer.addView(createAccountButton);
	}

	private void createAccount() {
		final UUID deviceRegistrationId = UUID.randomUUID();
		final UUID accountId = UUID.randomUUID();
		final Account account = new Account();
		SharedPreferencesRepository.withContext(this).setDeviceRegistrationId(deviceRegistrationId);
		account.setAccountId(accountId);
		account.addDeviceId(deviceRegistrationId);
		account.setEmail("foo.bar@baz.de");
		AccountRepository.withContext(this).store(account);
	}

	private void initStylesAndBackground() {
		getWindow().getDecorView().setBackground(BlurBuilder.blur(this, R.drawable.vintage_photo_small));
		getWindow().setStatusBarColor(Color.argb(100, 0, 0, 0));
		getWindow().setNavigationBarColor(Color.argb(100, 0, 0, 0));
	}
}