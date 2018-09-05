/*
 * Copyright 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.androidapp.snu.components.contacts;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.androidapp.snu.R;

public class ContactPermissionService {
	public static final int REQUEST_CONTACTS_READ_PERMISSION = 1;

	public static interface CustomDialogAware {
		void onContactPermissionDenied();
	}

	public static ContactPermissionService newInstance() {
		return new ContactPermissionService();
	}

	public boolean hasPermission(final Activity activity) {
		return ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED;
	}

	public void requestPermissionIfRequired(final Activity activity, final CustomDialogAware customCallback) {
		if (ContextCompat.checkSelfPermission(activity,
				Manifest.permission.READ_CONTACTS)
				!= PackageManager.PERMISSION_GRANTED) {

			// Permission is not granted

			AlertDialog.Builder alertBuilder = new AlertDialog.Builder(activity);
			alertBuilder.setCancelable(true);
			alertBuilder.setView(activity.getLayoutInflater().inflate(R.layout.request_permission_dialog_read_contacts, null));
			alertBuilder.setPositiveButton("Ja, gerne!", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					ActivityCompat.requestPermissions(activity,
							new String[]{Manifest.permission.READ_CONTACTS},
							REQUEST_CONTACTS_READ_PERMISSION);
				}
			});
			alertBuilder.setNegativeButton("Lieber nicht", (alertDialog, which) -> {
				alertDialog.dismiss();
				if (customCallback != null) {
					customCallback.onContactPermissionDenied();
				}
			});
			AlertDialog alert = alertBuilder.create();

			alert.show();
			int textColor = Color.argb(255, 204, 153, 102);
			alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(textColor);
			alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(textColor);
		}
	}
}
