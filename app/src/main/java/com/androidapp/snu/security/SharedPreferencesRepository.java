package com.androidapp.snu.security;

import android.app.Activity;
import android.content.Context;

import java.util.UUID;

public interface SharedPreferencesRepository {
	String DEVICE_REGISTRATION_ID = "DEVICE_REGISTRATION_ID";

	static SharedPreferencesRepository withContext(final Context context) {
		return new SharedPreferencesRepositoryImpl(context);
	}

	void setDeviceRegistrationId(final UUID deviceRegistrationId);
	UUID getDeviceRegistrationId();
	void clear();
}
