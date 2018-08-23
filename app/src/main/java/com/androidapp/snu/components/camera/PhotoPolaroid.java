package com.androidapp.snu.components.camera;

import android.content.Context;
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

	public void setPhoto(final Context context, final File file) {
		ImageView imageView = this.findViewById(R.id.component_polaroid_image);
		imageView.setImageDrawable(null);
		if (file != null) {
			Picasso
					.with(context)
					.load(file)
					.into(imageView);
		}
	}

	public void deletePhoto() {
		ImageView imageView = this.findViewById(R.id.component_polaroid_image);
		imageView.setImageDrawable(null);
	}
}
