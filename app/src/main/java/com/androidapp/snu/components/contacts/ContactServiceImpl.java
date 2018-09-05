package com.androidapp.snu.components.contacts;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

class ContactServiceImpl implements ContactService {
	private final Context context;

	ContactServiceImpl(final Context context) {
		this.context = context;
	}

	@Override
	public void getContactsAsync(final Activity activity, final ContactService.ContactsLoadedCallback callback) {

		final Runnable runnable = new Runnable() {
			public void run() {
				List<Contact> list = new ArrayList<>();
				ContentResolver contentResolver = context.getContentResolver();
				Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
				if (cursor != null && cursor.getCount() > 0) {
					while (cursor.moveToNext()) {
						String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
						if (cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
							Cursor cursorInfo = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
									ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
							InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(context.getContentResolver(),
									ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.valueOf(id)));

							Uri person = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.valueOf(id));
							Uri pURI = Uri.withAppendedPath(person, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);

							Bitmap photo = null;
							if (inputStream != null) {
								photo = BitmapFactory.decodeStream(inputStream);
							}

							if (cursorInfo != null) {
								while (cursorInfo.moveToNext()) {
									Contact contact = new Contact();
									contact.id = id;
									contact.name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
									contact.mobileNumber = cursorInfo.getString(cursorInfo.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
									contact.photo = photo;
									contact.photoURI = pURI;
									list.add(contact);
								}

								cursorInfo.close();
							}
						}
					}
					cursor.close();
				}
				callback.onContactsLoaded(list);
			}
		};
		Thread mythread = new Thread(runnable);
		mythread.start();
	}
}
