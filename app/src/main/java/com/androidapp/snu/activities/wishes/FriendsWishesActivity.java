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

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.Html;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		content = new LinearLayout(this);
		content.setOrientation(LinearLayout.VERTICAL);
		super.onCreate(savedInstanceState);
		ContactPermissionService contactPermissionService = ContactPermissionService.newInstance();
		if (contactPermissionService.hasPermission(this)) {
			loadContacts();
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
					loadContacts();
				} else {
					onContactPermissionDenied();
				}
			}
		}
	}

	private void loadContacts() {
		List<Contact> contacts = ContactService.withContext(this).getContacts(this);
		for (Contact contact : contacts) {
			TextView contactNr = new TextView(this);
			contactNr.setText(contact.getName() + " | " + contact.getMobileNumber());
			content.addView(contactNr);
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
}
