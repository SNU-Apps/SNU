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
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidapp.snu.R;
import com.androidapp.snu.activities.AbstractBaseActivity;
import com.androidapp.snu.components.contacts.Contact;
import com.androidapp.snu.components.contacts.ContactService;
import com.androidapp.snu.components.contacts.ContactPermissionService;

import java.util.List;

public class FriendsWishesActivity extends AbstractBaseActivity implements ActivityCompat.OnRequestPermissionsResultCallback {
	public static final int HEADER_IMAGE_ID = R.drawable.v3_2;
	public static final String HEADER_TEXT = "Wünsche von Freunden";

	private LinearLayout content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		content = new LinearLayout(this);
		super.onCreate(savedInstanceState);
		ContactPermissionService.newInstance().requestPermissionIfRequired(this);
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
	protected View getFooter() {
		TextView text = new TextView(this);
		text.setText("Weiter zur Vorschau");
		text.setTextAppearance(this, R.style.TextAppearance_MaterialComponents_Headline5);
		LinearLayout footer = new LinearLayout(this);
		footer.addView(text);

		text.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});

		return footer;
	}

	private void loadContacts() {
		List<Contact> contacts = ContactService.withContext(this).getContacts(this);
		for (Contact contact : contacts) {
			TextView contactNr = new TextView(this);
			contactNr.setText(contact.getMobileNumber());
			content.addView(contactNr);
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
		switch (requestCode) {
			case ContactPermissionService.REQUEST_CONTACTS_READ_PERMISSION: {
				// If request is cancelled, the result arrays are empty.
				if (grantResults.length > 0
						&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					// permission was granted, yay! Do the
					// contacts-related task you need to do.
					loadContacts();
				} else {
					// permission denied, boo! Disable the
					// functionality that depends on this permission.
				}
				return;
			}

			// other 'case' lines to check for other
			// permissions this app might request.
		}
	}
}
