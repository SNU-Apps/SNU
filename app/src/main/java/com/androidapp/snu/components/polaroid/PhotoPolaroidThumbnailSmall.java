package com.androidapp.snu.components.polaroid;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.androidapp.snu.R;
import com.squareup.picasso.Picasso;

import java.io.File;

public class PhotoPolaroidThumbnailSmall extends ConstraintLayout {

	public PhotoPolaroidThumbnailSmall(Context context) {
		super(context);
		this.addView(LayoutInflater.from(context).inflate(R.layout.component_polaroid_thumbnail_small, null));
	}

	public PhotoPolaroidThumbnailSmall(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		this.addView(LayoutInflater.from(context).inflate(R.layout.component_polaroid_thumbnail_small, null));
	}

	public void setPhoto(final Context context, final File jpg) {
		ImageView imageView = this.findViewById(R.id.component_polaroid_image_thumbnail_small);
		imageView.setImageDrawable(null);
		if (jpg != null) {
			Picasso
					.with(context)
					.load(jpg)
					.into(imageView);
		}
	}
}
