package com.androidapp.snu.repository.wish;

import java.util.Date;
import java.util.UUID;

public class Wish {
	private UUID wishId;
	private Date createdDate;
	private String photoFileName;
	private String description;

	public UUID getWishId() {
		return wishId;
	}

	public void setWishId(UUID wishId) {
		this.wishId = wishId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean hasPhoto() {
		return photoFileName != null;
	}

	public String getPhotoFileName() {
		return photoFileName;
	}

	public void setPhotoFileName(String photoFileName) {
		this.photoFileName = photoFileName;
	}
}
