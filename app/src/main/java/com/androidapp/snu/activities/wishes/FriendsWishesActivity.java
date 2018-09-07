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
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidapp.snu.R;
import com.androidapp.snu.activities.AbstractBaseActivity;
import com.androidapp.snu.components.contacts.Contact;
import com.androidapp.snu.components.contacts.ContactPermissionService;
import com.androidapp.snu.components.contacts.ContactService;
import com.androidapp.snu.components.contacts.view.ContactView;
import com.androidapp.snu.components.progress.LoadingSpinner;

public class FriendsWishesActivity extends AbstractBaseActivity
		implements ContactPermissionService.CustomDialogAware, ActivityCompat.OnRequestPermissionsResultCallback {
	private static final String fontPath = "fonts/handwrite.ttf";
	public static final int HEADER_IMAGE_ID = R.drawable.v3_2;
	public static final String HEADER_TEXT = "Wünsche von Freunden";

	private TextView header;
	private LinearLayout content;
	private LoadingSpinner loadingSpinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		header = (TextView) LayoutInflater.from(this).inflate(R.layout.activity_friends_wishes_header, null);
		content = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.activity_friends_wishes_content, null);
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

	@Override
	public void onContactPermissionDenied() {
		LinearLayout noPermissionContent = (LinearLayout) getLayoutInflater().inflate(R.layout.no_permission_content_read_contacts, null);
		content.addView(noPermissionContent);
		TextView hint = noPermissionContent.findViewById(R.id.no_permission_content_read_contacts_hint);
		hint.setText(Html.fromHtml("<u>" + hint.getText() + "</u>"));
		hint.setOnClickListener(view -> ContactPermissionService.newInstance().showHint(this));
	}

	private void loadContactsAsync() {
		final Context context = this;
		final Activity activity = this;
		showLoadingSpinner();
		ContactService.withContext(context).getContactsAsync(this, contacts -> activity.runOnUiThread(() -> {
			hideLoadingSpinner();
			for (Contact contact : contacts) {
				content.addView(
						new ContactView(context)
								.setName(contact.getName())
								.setNumber(contact.getMobileNumber())
								.setPhoto(contact.getPhoto()));
			}
		}));
	}

	private void showLoadingSpinner() {
		loadingSpinner = new LoadingSpinner("SNU sucht nach den Wünschen Deiner Freunde ...", content, this);
		loadingSpinner.show();
	}

	private void hideLoadingSpinner() {
		loadingSpinner.hide();
	}
}
