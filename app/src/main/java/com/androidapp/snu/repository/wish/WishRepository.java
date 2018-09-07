package com.androidapp.snu.repository.wish;

import android.content.Context;

import java.util.List;
import java.util.UUID;

public interface WishRepository {
	Wish store(final Wish wish);
	Wish findById(final UUID id);
	List<Wish> findAll();
	List<Wish> delete(final Wish wish);

	static WishRepository myWishes(final Context context) {
		return new MyWishRepositoryImpl(context);
	}

	static WishRepository friendsWishes(final String normalizedPhoneNumber, final Context context) {
		return new FriendsWishRepositoryImpl(normalizedPhoneNumber, context);
	}
}
