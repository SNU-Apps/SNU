package com.androidapp.snu.repository.wish;

import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
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

class MyWishesRepositoryImpl implements MyWishesRepository {
	final Context context;

	MyWishesRepositoryImpl(final Context context) {
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
			if (wish.getWishId().equals(id)) {
				return wish;
			}
		}
		return null;
	}

	@Override
	public List<Wish> findAll() {
		List<Wish> wishes;
		try {
			File file = new File(context.getExternalFilesDir(getLocalSubFolder()), getNormalizedWishListFileName(getWishListName()));
			wishes = (List<Wish>) deserialize(file);
		} catch (IOException | ClassNotFoundException e) {
			return new ArrayList<>();
		}

		Collections.sort(wishes, (w1, w2) -> w2.getCreatedDate().compareTo(w1.getCreatedDate()));
		return wishes;
	}

	@Override
	public void findAllAsync(MyWishesRepository.WishesLoadedCallback callback) {
		final Runnable runnable = new Runnable() {
			@Override
			public void run() {
				final List<Wish> wishes = findAll();
				callback.onWishesLoaded(wishes);
			}
		};
		Thread mythread = new Thread(runnable);
		mythread.start();
	}

	@Override
	public List<Wish> delete(Wish wish) {
		List<Wish> remainingWishes = findAll();
		if (!remainingWishes.isEmpty()) {
			Iterator<Wish> wishIterator = remainingWishes.iterator();
			while (wishIterator.hasNext()) {
				Wish currentWish = wishIterator.next();
				if (currentWish.getWishId().equals(wish.getWishId())) {
					wishIterator.remove();
				}
			}
		}
		return remainingWishes;
	}
	private List<Wish> storeWishListToFileInternal(List<Wish> wishes) {
		File mFile = new File(context.getExternalFilesDir(getLocalSubFolder()), getNormalizedWishListFileName(getWishListName()));
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

	private static Object deserialize(File data) throws IOException, ClassNotFoundException {
		FileInputStream in = new FileInputStream(data);
		ObjectInputStream is = new ObjectInputStream(in);
		return is.readObject();
	}

	private String getWishListName() {
		return "myWishList";
	}

	private String getLocalSubFolder() {
		return null;
	}
}
