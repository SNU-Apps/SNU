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

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.Html;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidapp.snu.R;
import com.androidapp.snu.activities.AbstractBaseActivity;
import com.androidapp.snu.components.contacts.Contact;
import com.androidapp.snu.components.contacts.ContactPermissionService;
import com.androidapp.snu.components.contacts.ContactService;

import java.util.List;

public class FriendsWishesActivity extends AbstractBaseActivity
		implements ContactPermissionService.CustomDialogAware, ActivityCompat.OnRequestPermissionsResultCallback {
	private static final String fontPath = "fonts/handwrite.ttf";
	public static final int HEADER_IMAGE_ID = R.drawable.v3_2;
	public static final String HEADER_TEXT = "Wünsche von Freunden";

	private LinearLayout content;
	ProgressBar loadingSpinner;
	TextView loadingSpinnerText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		content = new LinearLayout(this);
		content.setOrientation(LinearLayout.VERTICAL);
		super.onCreate(savedInstanceState);
		ContactPermissionService contactPermissionService = ContactPermissionService.newInstance();
		if (contactPermissionService.hasPermission(this)) {
			loadContactsAsync();
			return;
		}
		contactPermissionService.requestPermissionIfRequired(this, this);
	}

	@Override
	public int getIconImageId() {
		return HEADER_IMAGE_ID;
	}

	@Override
	public String getHeaderText() {
		return HEADER_TEXT;
	}

	@Override
	protected View getContent() {
		return content;
	}

	@Override
	protected View getHeader() {
		TextView header = new TextView(this);
		Typeface typeface = Typeface.createFromAsset(this.getAssets(), fontPath);
		header.setTypeface(typeface);
		header.setText("Wünsche von Freunden");
		header.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
		header.setPadding(0, 0, 0, 20);
		header.setTextColor(Color.argb(255, 214, 214, 214));
		return header;
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
		switch (requestCode) {
			case ContactPermissionService.REQUEST_CONTACTS_READ_PERMISSION: {
				if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					loadContactsAsync();
				} else {
					onContactPermissionDenied();
				}
			}
		}
	}

	private void loadContactsAsync() {
		final Context context = this;
		final Activity activity = this;
		showLoadingSpinner();
		ContactService.withContext(context).getContactsAsync(this, new ContactService.ContactsLoadedCallback() {
			@Override
			public void onContactsLoaded(List<Contact> contacts) {
				activity.runOnUiThread(() -> {
					hideLoadingSpinner();
					for (Contact contact : contacts) {
						TextView contactNr = new TextView(context);
						contactNr.setText(contact.getName() + " | " + contact.getMobileNumber());
						content.addView(contactNr);
					}
				});
			}
		});
	}

	private void showLoadingSpinner() {
		loadingSpinner = new ProgressBar(this);
		loadingSpinner.getIndeterminateDrawable()
				.setColorFilter(Color.argb(255, 166, 112, 63), android.graphics.PorterDuff.Mode.MULTIPLY);

		loadingSpinnerText = new TextView(this);
		loadingSpinnerText.setText("SNU sucht nach den Wünschen Deiner Freunde ...");
		Typeface typeface = Typeface.createFromAsset(this.getAssets(), fontPath);
		loadingSpinnerText.setTypeface(typeface);
		loadingSpinnerText.setTextSize(24);
		loadingSpinnerText.setGravity(Gravity.CENTER);
		content.addView(loadingSpinner);
		content.addView(loadingSpinnerText);
	}

	private void hideLoadingSpinner() {
		content.removeView(loadingSpinner);
		content.removeView(loadingSpinnerText);
	}

	@Override
	public void onContactPermissionDenied() {
		LinearLayout noPermissionContent = (LinearLayout) getLayoutInflater().inflate(R.layout.no_permission_content_read_contacts, null);
		content.addView(noPermissionContent);
		TextView hint = noPermissionContent.findViewById(R.id.no_permission_content_read_contacts_hint);
		hint.setText(Html.fromHtml("<u>" + hint.getText() + "</u>"));
		hint.setOnClickListener(view -> ContactPermissionService.newInstance().showHint(this));
	}
}
