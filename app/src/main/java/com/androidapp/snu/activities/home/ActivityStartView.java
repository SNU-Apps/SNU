package com.androidapp.snu.activities.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidapp.snu.R;
import com.androidapp.snu.activities.AbstractBaseActivity;
import com.androidapp.snu.transformation.RoundedCornersTransformation;
import com.squareup.picasso.Picasso;

class ActivityStartView extends LinearLayout implements View.OnClickListener {
	private AbstractBaseActivity activity;

	private ActivityStartView(Context context) {
		super(context);
	}

	private void initActivity(AbstractBaseActivity activity) {
		this.activity = activity;
		final Context context = getContext();
		this.addView(getIconWithText(context));
		setDefaultPadding();
	}

	private View getIconWithText(Context context) {
		View view = LayoutInflater.from(context).inflate(R.layout.fragment_activity_home_scene, null);
		ImageView image = view.findViewById(R.id.activity_start_image_icon);
		TextView text = view.findViewById(R.id.activity_start_header_text);
		Picasso.with(context)
				.load(activity.getIconImageId())
				.transform(new RoundedCornersTransformation(30, 0))
				.noFade()
				.noPlaceholder()
				.into(image);
		text.setText(activity.getHeaderText());
		image.setOnClickListener(this);
		return view;
	}

	void setDefaultPadding() {
		this.setPadding(16, 16, 16, 0);
	}

	ActivityStartView withCustomPadding(int left, int top, int right, int bottom) {
		this.setPadding(left, top, right, bottom);
		return this;
	}

	static ActivityStartView createForActivity(
			AbstractBaseActivity activity,
			Context context) {
		ActivityStartView view = new ActivityStartView(context);
		view.initActivity(activity);
		return view;
	}

	@Override
	public void onClick(View view) {
		Intent intent = new Intent(getContext(), activity.getClass());
		ActivityCompat.startActivity(getContext(), intent, null);
	}
}
