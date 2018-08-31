package com.androidapp.snu.repository.wish;

import android.content.Context;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
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
		List<Wish> wishes = delete(wish);
		wishes.add(wish);
		storeWishListToFileInternal(wishes);
		return wish;
	}

	@Override
	public Wish findById(UUID id) {
		for (Wish wish : findAll()) {
			if(wish.getWishId().equals(id)) {
				return wish;
			}
		}
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
	public List<Wish> delete(Wish wish) {
		List<Wish> remainingWishes = findAll();
		if (!remainingWishes.isEmpty()) {
			Iterator<Wish> wishIterator = remainingWishes.iterator();
			while (wishIterator.hasNext()) {
				Wish currentWish = wishIterator.next();
				if(currentWish.getWishId().equals(wish.getWishId())) {
					wishIterator.remove();
				}
			}
		}
		return remainingWishes;
	}

	private Wish storeWishToFileInternal(Wish wish) {
		File mFile = new File(context.getExternalFilesDir(null), getNormalizedWishFileName(wish.getWishId().toString()));
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(mFile);
			fos.write(serialize(wish));
			fos.flush();
			fos.close();
			return wish;
		} catch (Exception e) {
			return null;
		}
	}
	private List<Wish> storeWishListToFileInternal(List<Wish> wishes) {
		File mFile = new File(context.getExternalFilesDir(null), getNormalizedWishListFileName("myWishList"));
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(mFile);
			fos.write(serialize(wishes));
			fos.flush();
			fos.close();
			return wishes;
		} catch (Exception e) {
			return null;
		}
	}

	private String getNormalizedWishFileName(String fileName) {
		if (!fileName.endsWith(".wsh")) {
			return fileName + ".wsh";
		}
		return fileName;
	}

	private String getNormalizedWishListFileName(String fileName) {
		if (!fileName.endsWith(".wsl")) {
			return fileName + ".wsl";
		}
		return fileName;
	}

	private static byte[] serialize(Object obj) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(out);
		os.writeObject(obj);
		return out.toByteArray();
	}

	public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
		ByteArrayInputStream in = new ByteArrayInputStream(data);
		ObjectInputStream is = new ObjectInputStream(in);
		return is.readObject();
	}
}
