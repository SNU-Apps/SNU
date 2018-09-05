package com.androidapp.snu.components.contacts;

import android.graphics.Bitmap;
import android.net.Uri;

public class Contact {
	String id;
	String name;
	String mobileNumber;
	Bitmap photo;
	Uri photoURI;

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public Bitmap getPhoto() {
		return photo;
	}

	public Uri getPhotoURI() {
		return photoURI;
	}
}
