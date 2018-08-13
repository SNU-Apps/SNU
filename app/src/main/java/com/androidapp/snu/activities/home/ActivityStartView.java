package com.androidapp.snu.activities.home;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidapp.snu.R;
import com.androidapp.snu.activities.wishes.CreateWishActivity;
import com.squareup.picasso.Picasso;

class ActivityStartView extends LinearLayout implements View.OnClickListener {
	private Class<? extends AbstractHomeTransitionActivity> activity;

	private ActivityStartView(Context context) {
		super(context);
		ImageView image = new ImageView(context);

		this.setPadding(10, 0, 10, 0);
		image.setScaleType(ImageView.ScaleType.FIT_XY);

		Picasso.with(context)
				.load(CreateWishActivity.HEADER_IMAGE_ID)
				.transform(new RoundedCornersTransformation(30, 5))
				.noFade()
				.noPlaceholder()
				.into(image);
		this.addView(image);

		image.setOnClickListener(this);
	}

	static ActivityStartView createForActivity(
			Class<? extends AbstractHomeTransitionActivity> activity,
			Context context) {
		ActivityStartView view = new ActivityStartView(context);
		view.activity = activity;
		return view;
	}

	@Override
	public void onClick(View view) {
		Intent intent = new Intent(getContext(), activity);
		ActivityCompat.startActivity(getContext(), intent, null);
	}
}
