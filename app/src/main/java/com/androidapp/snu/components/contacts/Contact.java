package com.androidapp.snu.components.contacts;

import android.graphics.Bitmap;
import android.net.Uri;

import java.util.Objects;

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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Contact contact = (Contact) o;
		return Objects.equals(mobileNumber, contact.mobileNumber);
	}

	@Override
	public int hashCode() {
		return Objects.hash(mobileNumber);
	}
}
