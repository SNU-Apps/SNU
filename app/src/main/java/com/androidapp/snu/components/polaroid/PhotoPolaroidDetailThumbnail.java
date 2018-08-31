package com.androidapp.snu.components.polaroid;

import android.content.Context;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.androidapp.snu.R;

import java.io.File;

public class PhotoPolaroidDetailThumbnail extends ConstraintLayout {
	private static final String fontPath = "fonts/handwrite.ttf";

	public PhotoPolaroidDetailThumbnail(Context context) {
		super(context);
		this.addView(LayoutInflater.from(context).inflate(R.layout.component_polaroid_detail_thumbnail, null));
	}

	public PhotoPolaroidDetailThumbnail(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		this.addView(LayoutInflater.from(context).inflate(R.layout.component_polaroid_detail_thumbnail, null));
	}

	public void setPhoto(final Context context, final File jpg) {
		PhotoPolaroidThumbnail polaroid = this.findViewById(R.id.component_polaroid_image_detail_thumbnail);
		polaroid.setPhoto(context, jpg);
	}

	public void setDescription(final Context context, final String description) {
		TextView descriptionView = this.findViewById(R.id.component_polaroid_description_detail_thumbnail);
		Typeface typeface = Typeface.createFromAsset(context.getAssets(), fontPath);
		descriptionView.setTypeface(typeface);
		descriptionView.setText(description);
	}
}
