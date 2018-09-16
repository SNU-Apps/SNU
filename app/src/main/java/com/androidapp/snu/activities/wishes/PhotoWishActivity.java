/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.androidapp.snu.activities.wishes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.ImageView;

import com.androidapp.snu.R;
import com.androidapp.snu.activities.AbstractBaseActivity;
import com.androidapp.snu.components.polaroid.PhotoPolaroid;
import com.androidapp.snu.components.utils.BitmapUtils;
import com.androidapp.snu.repository.image.ImageRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class PhotoWishActivity extends AbstractBaseActivity {
	public static final int HEADER_IMAGE_ID = R.drawable.v2;
	public static final String HEADER_TEXT = "...fotografieren";

	private PhotoPolaroid polaroid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		polaroid = new PhotoPolaroid(this);
		super.onCreate(savedInstanceState);
		//capturePhoto();
		//dispatchTakePictureIntent();
	}

	@Override
	protected View getContent() {
		polaroid.setOnClickListener(v -> dispatchTakePictureIntent());
		return polaroid;
	}

	@Override
	public int getIconImageId() {
		return HEADER_IMAGE_ID;
	}

	@Override
	public String getHeaderText() {
		return HEADER_TEXT;
	}

//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		if (requestCode == CreateWishActivity.PICK_IMAGE_FROM_CAMERA) {
//			if (resultCode == Activity.RESULT_OK) {
//				String fileName = data.getStringExtra(CreateWishActivity.PHOTO_FILE_NAME);
//				Intent intent = new Intent(this, CreateWishActivity.class);
//				intent.putExtra(CreateWishActivity.PHOTO_FILE_NAME, fileName);
//				startActivity(intent);
//			}
//		}
//		finish();
//	}
//
//	private void capturePhoto() {
//		Intent intent = new Intent(this, CameraCaptureActivity.class);
//		startActivityForResult(intent, CreateWishActivity.PICK_IMAGE_FROM_CAMERA);
//	}

	static final int REQUEST_IMAGE_CAPTURE = 1;

	private void dispatchTakePictureIntent() {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// Ensure that there's a camera activity to handle the intent
		if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
			// Create the File where the photo should go
			File photoFile = null;
			try {
				photoFile = createImageFile();
			} catch (IOException ex) {
				// Error occurred while creating the File
			}
			// Continue only if the File was successfully created
			if (photoFile != null) {
				Uri photoURI = FileProvider.getUriForFile(this,
						"com.androidapp.snu.fileprovider",
						photoFile);
				takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
				startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//			Bundle extras = data.getExtras();
//			Bitmap imageBitmap = (Bitmap) extras.get("data");
//			imageView.setImageBitmap(imageBitmap);
			setPic();
		}
	}

	String mCurrentPhotoPath;

	File image;
	private File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = "JPEG_" + timeStamp + "_";
		File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
		image = File.createTempFile(
				imageFileName,  /* prefix */
				".jpg",         /* suffix */
				storageDir      /* directory */
		);

		// Save a file: path for use with ACTION_VIEW intents
		mCurrentPhotoPath = image.getAbsolutePath();
		return image;
	}

	private void setPic() {
		// Get the dimensions of the View
		int targetW = findViewById(R.id.component_polaroid_image).getWidth();
		int targetH = findViewById(R.id.component_polaroid_image).getHeight();

		// Get the dimensions of the bitmap
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		bmOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
		int photoW = bmOptions.outWidth;
		int photoH = bmOptions.outHeight;

		// Determine how much to scale down the image
		int scaleFactor = Math.min(photoW/(targetW), photoH/(targetH));

		// Decode the image file into a Bitmap sized to fill the View
		bmOptions.inJustDecodeBounds = false;
		bmOptions.inSampleSize = scaleFactor;
		bmOptions.inPurgeable = true;

		Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
		File f = persistImage(bitmap, UUID.randomUUID().toString());
		polaroid.setPhoto(this, f);
	}

	private File persistImage(Bitmap bitmap, String name) {
		File filesDir = this.getExternalFilesDir(null);
		File imageFile = new File(filesDir, name + ".jpg");

		OutputStream os;
		try {
			os = new FileOutputStream(imageFile);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
			os.flush();
			os.close();
		} catch (Exception e) {

		}

		return imageFile;
	}
}
