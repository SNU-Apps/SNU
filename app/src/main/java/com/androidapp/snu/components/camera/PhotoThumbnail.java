package com.androidapp.snu.components.camera;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.androidapp.snu.R;
import com.squareup.picasso.Picasso;

import java.io.File;

public class PhotoThumbnail extends LinearLayout {

	private ImageView imageView;

	public PhotoThumbnail(Context context) {
		super(context);

		this.setGravity(Gravity.CENTER_HORIZONTAL);
		this.imageView = new ImageView(context);
		this.imageView.setImageResource(R.drawable.polaroid_with_camera_100px);
		this.addView(this.imageView);
	}

	public void setPhoto(final Context context, final String path) {
		File imgFile;
		if (path != null && (imgFile = new File(path)).exists()) {
			Picasso
				.with(context)
				.load(imgFile)
				.resize(125, 70)
				.into(imageView);

			imageView.setMaxHeight(10);
			imageView.setPadding(0,20,0,50);
			imageView.setBackgroundColor(Color.argb(255,0,0,0));
			imageView.setBackgroundResource(R.drawable.polaroid_with_camera_100px);
		}
	}
}
