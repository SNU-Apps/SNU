package com.androidapp.snu.security;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.UUID;

public class SharedPreferencesRepositoryImpl implements SharedPreferencesRepository {
	final static String instanceId = "com.androidapp.snu";
	final Context context;

	SharedPreferencesRepositoryImpl(final Context context) {
		this.context = context;
	}

	@Override
	public void setDeviceRegistrationId(UUID deviceRegistrationId) {
		SharedPreferences preferences = context.getSharedPreferences(instanceId, Context.MODE_PRIVATE); // 0 - for private mode
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(DEVICE_REGISTRATION_ID, deviceRegistrationId.toString());
		editor.commit();
	}

	@Override
	public UUID getDeviceRegistrationId() {
		SharedPreferences preferences = context.getSharedPreferences(instanceId, Context.MODE_PRIVATE); // 0 - for private mode
		final String deviceRegistrationIdString = preferences.getString(DEVICE_REGISTRATION_ID, null);
		return deviceRegistrationIdString != null ? UUID.fromString(deviceRegistrationIdString) : null;
	}

	@Override
	public void clear() {
		SharedPreferences preferences = context.getSharedPreferences(instanceId, Context.MODE_PRIVATE); // 0 - for private mode
		SharedPreferences.Editor editor = preferences.edit();
		editor.clear().commit();
	}
}
