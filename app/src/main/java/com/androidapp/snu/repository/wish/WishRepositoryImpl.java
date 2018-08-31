package com.androidapp.snu.repository.wish;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class WishRepositoryImpl implements WishRepository {
	private final Context context;

	WishRepositoryImpl(final Context context) {
		this.context = context;
	}

	@Override
	public Wish store(Wish wish) {
		return null;
	}

	@Override
	public Wish findById(UUID id) {
		return null;
	}

	@Override
	public List<Wish> findAll() {
		final List<Wish> wishes = new ArrayList<>();
		return wishes;
	}

	@Override
	public void delete(Wish wish) {

	}
}
