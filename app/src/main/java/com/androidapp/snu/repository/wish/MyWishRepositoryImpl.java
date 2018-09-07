package com.androidapp.snu.repository.wish;

import android.content.Context;

class MyWishRepositoryImpl extends AbstractWishRepositoryImpl {

	MyWishRepositoryImpl(final Context context) {
		super(context);
	}

	@Override
	String getWishListName() {
		return "myWishList";
	}

	@Override
	String getLocalSubFolder() {
		return null;
	}
}
