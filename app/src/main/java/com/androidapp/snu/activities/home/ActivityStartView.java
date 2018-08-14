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
import com.androidapp.snu.transformation.RoundedCornersTransformation;
import com.squareup.picasso.Picasso;

class ActivityStartView extends LinearLayout implements View.OnClickListener {
	private AbstractHomeTransitionActivity activity;

	private ActivityStartView(Context context) {
		super(context);
	}

	private void initActivity(AbstractHomeTransitionActivity activity) {
		this.activity = activity;
		final Context context = getContext();
		this.addView(getIconWithText(context));
		setExtraStyles();
	}

	private View getIconWithText(Context context) {
		View view = LayoutInflater.from(context).inflate(R.layout.activity_start_icon, null);
		ImageView image = view.findViewById(R.id.activity_start_image_icon);
		TextView text = view.findViewById(R.id.activity_start_header_text);
		Picasso.with(context)
				.load(activity.getHeaderImageId())
				.transform(new RoundedCornersTransformation(30, 0))
				.noFade()
				.noPlaceholder()
				.into(image);
		text.setText(activity.getHeaderText());
		image.setOnClickListener(this);
		return view;
	}

	private void setExtraStyles() {
		this.setPadding(0, 0, 0, 0);
	}

	static ActivityStartView createForActivity(
			AbstractHomeTransitionActivity activity,
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
