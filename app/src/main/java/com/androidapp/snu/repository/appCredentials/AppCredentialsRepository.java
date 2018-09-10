package com.androidapp.snu.repository.appCredentials;

import android.content.Context;

public interface AppCredentialsRepository {
	AppCredentials store(final AppCredentials appCredentials);
	AppCredentials find();

	static AppCredentialsRepository withContext(final Context context) {
		return new AppCredentialsRepositoryImpl(context);
	}
}
