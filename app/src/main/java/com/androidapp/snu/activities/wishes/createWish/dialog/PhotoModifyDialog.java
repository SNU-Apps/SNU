package com.androidapp.snu.activities.wishes.createWish.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;

import com.androidapp.snu.R;
import com.androidapp.snu.components.camera.PhotoPolaroid;

import java.io.File;

public class PhotoModifyDialog extends Dialog {
	public interface ToolbarListener {
		void onRotateLeft();

		void onRotateRight();

		void onDelete();

		void onNew();

		void onOK();
	}

	private PhotoPolaroid photo;

	public PhotoModifyDialog(@NonNull Context context, @NonNull final File jpg) {
		super(context);
		setContentView(R.layout.dialog_modify_photo);
		photo = findViewById(R.id.dialog_modify_photo_content_polaroid);
		photo.setPhoto(context, jpg);
	}

	public void show(@NonNull final ToolbarListener toolbar) {
		super.show();
		findViewById(R.id.dialog_modify_photo_content_rotate_left).setOnClickListener((view -> toolbar.onRotateLeft()));
		findViewById(R.id.dialog_modify_photo_content_rotate_right).setOnClickListener((view -> toolbar.onRotateRight()));
		findViewById(R.id.dialog_modify_photo_content_delete).setOnClickListener((view -> toolbar.onDelete()));
		findViewById(R.id.dialog_modify_photo_content_new).setOnClickListener((view -> toolbar.onNew()));
		findViewById(R.id.dialog_modify_photo_content_ok).setOnClickListener((view -> toolbar.onOK()));
	}

	public void setPhoto(Context context, File jpg) {
		photo.setPhoto(context, jpg);
	}
}
