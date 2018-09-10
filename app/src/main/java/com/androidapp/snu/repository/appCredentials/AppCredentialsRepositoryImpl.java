package com.androidapp.snu.repository.appCredentials;

import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

class AppCredentialsRepositoryImpl implements AppCredentialsRepository {
	final Context context;

	AppCredentialsRepositoryImpl(final Context context) {
		this.context = context;
	}

	@Override
	public AppCredentials store(AppCredentials appCredentials) {
		return storeCredentials(appCredentials);
	}

	@Override
	public AppCredentials find() {
		AppCredentials credentials;
		try {
			File file = new File(context.getExternalFilesDir(getLocalSubFolder()), getNormalizedFileNAme(getAppName()));
			credentials = (AppCredentials) deserialize(file);
		} catch (IOException | ClassNotFoundException e) {
			return null;
		}
		return credentials;
	}

	private AppCredentials storeCredentials(final AppCredentials appCredentials) {
		File mFile = new File(context.getExternalFilesDir(getLocalSubFolder()), getNormalizedFileNAme(getAppName()));
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(mFile);
			fos.write(serialize(appCredentials));
			fos.flush();
			fos.close();
			return appCredentials;
		} catch (Exception e) {
			return null;
		}
	}

	private String getNormalizedFileNAme(String fileName) {
		if (!fileName.endsWith(".crd")) {
			return fileName + ".crd";
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

	private String getAppName() {
		return "snu";
	}

	private String getLocalSubFolder() {
		return null;
	}
}
