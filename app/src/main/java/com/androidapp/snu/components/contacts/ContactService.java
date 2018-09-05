package com.androidapp.snu.components.contacts;

import android.app.Activity;
import android.content.Context;

import java.util.List;

public interface ContactService {
	List<Contact> getContacts(final Activity activity);

	static ContactService withContext(final Context context) {
		return new ContactServiceImpl(context);
	}
}
