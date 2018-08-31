package com.androidapp.snu.activities.wishes.createWish.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;

import com.androidapp.snu.R;
import com.androidapp.snu.components.polaroid.PhotoPolaroid;

public class PhotoCreateDialog extends Dialog {
	public interface ToolbarListener {
		void onNewFromCamera();

		void onNewFromGallery();
	}

	public PhotoCreateDialog(@NonNull Context context) {
		super(context);
		setContentView(R.layout.dialog_new_photo);
	}

	public void show(@NonNull final ToolbarListener toolbar) {
		super.show();
		findViewById(R.id.dialog_new_photo_content_new_from_camera).setOnClickListener((view -> toolbar.onNewFromCamera()));
		findViewById(R.id.dialog_new_photo_content_new_from_gallery).setOnClickListener((view -> toolbar.onNewFromGallery()));
	}
}
