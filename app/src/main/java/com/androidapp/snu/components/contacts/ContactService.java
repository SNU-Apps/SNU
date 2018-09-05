package com.androidapp.snu.components.contacts;

import android.app.Activity;
import android.content.Context;

import java.util.List;

public interface ContactService {
	public interface ContactsLoadedCallback {
		void onContactsLoaded(List<Contact> contacts);
	}
	void getContactsAsync(final Activity activity, final ContactsLoadedCallback callback);

	static ContactService withContext(final Context context) {
		return new ContactServiceImpl(context);
	}
}
