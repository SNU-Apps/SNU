package com.androidapp.snu.components.polaroid;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.androidapp.snu.R;
import com.androidapp.snu.activities.home.HomeActivity;

import java.io.File;

public class PhotoPolaroidDetailThumbnail extends ConstraintLayout {
	private static final String fontPath = "fonts/handwrite.ttf";

	private Context context;

	public PhotoPolaroidDetailThumbnail(Context context) {
		super(context);
		this.context = context;
		ConstraintLayout layout = (ConstraintLayout) LayoutInflater.from(context).inflate(R.layout.component_polaroid_detail_thumbnail, null);
		layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		this.addView(layout);
		initPresentIdeaClickListener();
	}

	public void setPhoto(final Context context, final File jpg) {
		PhotoPolaroidThumbnail polaroid = this.findViewById(R.id.component_polaroid_image_detail_thumbnail);
		polaroid.setPhoto(context, jpg);
		polaroid.setOnClickListener(v -> new PhotoPolaroidDialog(context, jpg).show());
	}

	public void setDescription(final Context context, final String description) {
		TextView descriptionView = this.findViewById(R.id.component_polaroid_description_detail_thumbnail);
		Typeface typeface = Typeface.createFromAsset(context.getAssets(), fontPath);
		descriptionView.setTypeface(typeface);
		descriptionView.setText(description);
	}

	void initPresentIdeaClickListener() {
		findViewById(R.id.present_idea).setOnClickListener(v -> {
			Intent intent = new Intent(context, HomeActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			context.startActivity(intent);
		});
	}
}
