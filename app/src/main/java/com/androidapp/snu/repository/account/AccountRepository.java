package com.androidapp.snu.repository.account;

import android.content.Context;

public interface AccountRepository {
	Account store(final Account account);
	Account get();

	static AccountRepository withContext(final Context context) {
		return new AccountRepositoryImpl(context);
	}
}
