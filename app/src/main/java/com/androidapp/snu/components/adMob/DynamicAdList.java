package com.androidapp.snu.components.adMob;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.text.Html;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

public class DynamicAdList extends LinearLayout {
	private static final String fontPath = "fonts/handwrite.ttf";
	private final Context context;

	public DynamicAdList(Context context) {
		super(context);
		this.context = context;
		setOrientation(VERTICAL);
	}

	public void requestAds(final int pageCount) {
		init(pageCount);
	}

	private void init(int pageCount) {
		ProgressBar spinner = new ProgressBar(context);
		spinner.getIndeterminateDrawable()
				.setColorFilter(Color.argb(255, 166, 112, 63), android.graphics.PorterDuff.Mode.MULTIPLY);

		TextView infoText = new TextView(context);
		infoText.setText("SNU versucht, passende Produkte zu finden ...");
		Typeface typeface = Typeface.createFromAsset(context.getAssets(), fontPath);
		infoText.setTypeface(typeface);
		infoText.setTextSize(24);
		infoText.setGravity(Gravity.CENTER);

		TextView continueSearch = new TextView(context);
		continueSearch.setText(Html.fromHtml("<u>weitersuchen</u>"));
		continueSearch.setTypeface(typeface);
		continueSearch.setPadding(0, 0, 0, 25);
		continueSearch.setTextSize(22);
		continueSearch.setGravity(Gravity.CENTER);

		List<AdView> ads = new ArrayList<>();
		final int maxAds = pageCount;
		for (int i = 0; i < maxAds; i++) {
			AdView adView = new AdView(context);
			adView.setAdSize(new AdSize(300, 100));
			adView.setAlpha(0.7f);
			adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

			AdRequest.Builder builder = new AdRequest.Builder()
					.addKeyword("car")
					.addKeyword("black");

			adView.loadAd(builder.build());
			ads.add(adView);
		}

		final Handler handler = new Handler();
		handler.postDelayed(() -> {
			this.removeView(infoText);
			this.removeView(spinner);
			for (AdView ad : ads) {
				this.addView(ad);
				TextView space = new TextView(context);
				space.setText("");
				this.addView(space);
			}
			this.addView(continueSearch);

			continueSearch.setOnClickListener(v -> {
				this.removeView(continueSearch);
				for (int i = 0; i < maxAds; i++) {
					AdView ad = new AdView(context);
					ad.setAdSize(new AdSize(300, 100));
					ad.setAlpha(0.7f);
					ad.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

					AdRequest.Builder builder = new AdRequest.Builder()
							.addKeyword("car")
							.addKeyword("black");

					ad.loadAd(builder.build());
					this.addView(ad);
					TextView space = new TextView(context);
					space.setText("");
					this.addView(space);
				}
				this.addView(continueSearch);
			});
		}, 3000);

		this.addView(spinner);
		this.addView(infoText);
	}
}
