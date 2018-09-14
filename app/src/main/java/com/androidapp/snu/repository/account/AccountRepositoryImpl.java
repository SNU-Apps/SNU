package com.androidapp.snu.repository.account;

import android.content.Context;

class AccountRepositoryImpl implements AccountRepository {
	final Context context;
	private static Account currentAccount;

	AccountRepositoryImpl(final Context context) {
		this.context = context;
	}

	@Override
	public Account store(Account account) {
		currentAccount = account;
		return account;
	}

	@Override
	public Account get() {
		return currentAccount;
	}
}
