package com.androidapp.snu.repository.account;

import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

class AccountRepositoryImpl implements AccountRepository {
	final Context context;

	AccountRepositoryImpl(final Context context) {
		this.context = context;
	}

	@Override
	public Account store(Account account) {
		return storeCredentials(account);
	}

	@Override
	public Account get() {
		Account credentials;
		try {
			File file = new File(context.getExternalFilesDir(getLocalSubFolder()), getNormalizedFileName(getFileName()));
			credentials = (Account) deserialize(file);
		} catch (IOException | ClassNotFoundException e) {
			return null;
		}
		return credentials;
	}

	private Account storeCredentials(final Account account) {
		File mFile = new File(context.getExternalFilesDir(getLocalSubFolder()), getNormalizedFileName(getFileName()));
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(mFile);
			fos.write(serialize(account));
			fos.flush();
			fos.close();
			return account;
		} catch (Exception e) {
			return null;
		}
	}

	private String getNormalizedFileName(String fileName) {
		if (!fileName.endsWith(".acc")) {
			return fileName + ".acc";
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

	private String getFileName() {
		return "account";
	}

	private String getLocalSubFolder() {
		return null;
	}
}
