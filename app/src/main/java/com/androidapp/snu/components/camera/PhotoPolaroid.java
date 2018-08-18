package com.androidapp.snu.components.camera;

import android.content.Context;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.androidapp.snu.R;
import com.squareup.picasso.Picasso;

import java.io.File;

public class PhotoPolaroid extends ConstraintLayout {

	public PhotoPolaroid(Context context) {
		super(context);
		this.addView(LayoutInflater.from(context).inflate(R.layout.component_polaroid, null));
	}

	public PhotoPolaroid(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		this.addView(LayoutInflater.from(context).inflate(R.layout.component_polaroid, null));
	}

	public void setPhoto(final Context context, final String path) {
		ImageView imageView = this.findViewById(R.id.component_polaroid_image);
		File imgFile;
		if (path != null && (imgFile = new File(path)).exists()) {
			Picasso
					.with(context)
					.load(imgFile)
					.into(imageView);
		}
	}

	public void setPhoto(final Context context, final Uri uri) {
		ImageView imageView = this.findViewById(R.id.component_polaroid_image);
		if (uri != null) {
			Picasso
					.with(context)
					.load(uri)
					.into(imageView);
		}
	}
}
