package com.androidapp.snu.components.camera;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.androidapp.snu.R;
import com.squareup.picasso.Picasso;

import java.io.File;

public class PhotoPolaroidThumbnail extends ConstraintLayout {

	public PhotoPolaroidThumbnail(Context context) {
		super(context);
		this.addView(LayoutInflater.from(context).inflate(R.layout.component_polaroid_thumbnail, null));
	}

	public PhotoPolaroidThumbnail(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		this.addView(LayoutInflater.from(context).inflate(R.layout.component_polaroid_thumbnail, null));
	}

	public void setPhoto(final Context context, final File jpg) {
		ImageView imageView = this.findViewById(R.id.component_polaroid_image_thumbnail);
		imageView.setImageDrawable(null);
		if (jpg != null) {
			Picasso
					.with(context)
					.load(jpg)
					.into(imageView);
		}
	}

	public void deletePhoto() {
		ImageView imageView = this.findViewById(R.id.component_polaroid_image_thumbnail);
		imageView.setImageDrawable(null);
	}
}
