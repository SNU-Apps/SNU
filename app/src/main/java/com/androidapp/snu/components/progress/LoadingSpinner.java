package com.androidapp.snu.components.progress;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoadingSpinner {
	private static final String fontPath = "fonts/handwrite.ttf";
	private ViewGroup targetView;
	private ProgressBar loadingSpinner;
	private TextView loadingSpinnerText;

	public LoadingSpinner(final String description, final ViewGroup targetView, final Context context) {
		this.targetView = targetView;
		loadingSpinner = new ProgressBar(context);
		loadingSpinner.getIndeterminateDrawable().setColorFilter(Color.argb(255, 166, 112, 63), android.graphics.PorterDuff.Mode.MULTIPLY);
		loadingSpinnerText = new TextView(context);
		loadingSpinnerText.setText(description);
		loadingSpinnerText.setTypeface(Typeface.createFromAsset(context.getAssets(), fontPath));
		loadingSpinnerText.setTextSize(24);
		loadingSpinnerText.setGravity(Gravity.CENTER);
	}

	public void show() {
		targetView.addView(loadingSpinner);
		targetView.addView(loadingSpinnerText);
	}

	public void hide() {
		targetView.removeView(loadingSpinner);
		targetView.removeView(loadingSpinnerText);
	}
}
