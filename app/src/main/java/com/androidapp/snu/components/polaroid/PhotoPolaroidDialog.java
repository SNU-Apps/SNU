package com.androidapp.snu.components.polaroid;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;

import com.androidapp.snu.R;

import java.io.File;

public class PhotoPolaroidDialog extends Dialog {

	public PhotoPolaroidDialog(@NonNull Context context, @NonNull final File jpg) {
		super(context);
		setContentView(R.layout.dialog_photo);
		PhotoPolaroid photo = findViewById(R.id.dialog_photo_content_polaroid);
		photo.setPhoto(context, jpg);
	}
}
