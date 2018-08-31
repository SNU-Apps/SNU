package com.androidapp.snu.repository.wish;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

class WishRepositoryImpl implements WishRepository {
	private final Context context;

	WishRepositoryImpl(final Context context) {
		this.context = context;
	}

	@Override
	public Wish store(Wish wish) {
		wish.setWishId(wish.getWishId() != null ? wish.getWishId() : UUID.randomUUID());
		wish.setCreatedDate(new Date());
		//todo store to file(s)
		return wish;
	}

	@Override
	public Wish findById(UUID id) {
		return null;
	}

	@Override
	public List<Wish> findAll() {
		final List<Wish> wishes = new ArrayList<>();
		//todo load all
		Collections.sort(wishes, (w1, w2) -> w1.getCreatedDate().compareTo(w2.getCreatedDate()));
		return wishes;
	}

	@Override
	public void delete(Wish wish) {

	}
}
