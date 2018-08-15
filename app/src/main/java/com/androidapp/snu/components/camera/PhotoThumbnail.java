package com.androidapp.snu.components.camera;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.androidapp.snu.R;
import com.squareup.picasso.Picasso;

import java.io.File;

public class PhotoThumbnail extends ConstraintLayout {

	public PhotoThumbnail(Context context) {
		super(context);
		this.addView(LayoutInflater.from(context).inflate(R.layout.component_polaroid_thumbnail, null));
	}

	public void setPhoto(final Context context, final String path) {
		ImageView imageView = this.findViewById(R.id.component_polaroid_image_thumbnail);
		File imgFile;
		if (path != null && (imgFile = new File(path)).exists()) {
			Picasso
					.with(context)
					.load(imgFile)
					.into(imageView);
		}
	}
}
