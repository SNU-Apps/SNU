package com.androidapp.snu.repository.wish;

import android.content.Context;

import java.util.List;
import java.util.UUID;

public interface WishRepository {
	Wish store(final Wish wish);
	Wish findById(final UUID id);
	List<Wish> findAll();
	void delete(final Wish wish);

	static WishRepository withContext(final Context context) {
		return new WishRepositoryImpl(context);
	}
}
