package com.androidapp.snu.repository.wish;

import android.content.Context;

class FriendsWishRepositoryImpl extends AbstractWishRepositoryImpl {
	private final String normalizedPhoneNumber;

	FriendsWishRepositoryImpl(final String normalizedPhoneNumber, final Context context) {
		super(context);
		this.normalizedPhoneNumber = normalizedPhoneNumber;
	}

	@Override
	String getWishListName() {
		return normalizedPhoneNumber;
	}

	@Override
	String getLocalSubFolder() {
		return normalizedPhoneNumber;
	}
}
