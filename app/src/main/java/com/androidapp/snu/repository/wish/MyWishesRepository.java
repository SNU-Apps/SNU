package com.androidapp.snu.repository.wish;

import android.content.Context;

import java.util.List;
import java.util.UUID;

public interface MyWishesRepository {
	interface WishesLoadedCallback {
		void onWishesLoaded(List<Wish> wishes);
	}
	Wish store(final Wish wish);
	Wish findById(final UUID id);
	List<Wish> findAll();
	void findAllAsync(WishesLoadedCallback callback);
	List<Wish> delete(final Wish wish);

	static MyWishesRepository withContext(final Context context) {
		return new MyWishesRepositoryImpl(context);
	}

}
