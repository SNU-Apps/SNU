package com.androidapp.snu.activities.home;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidapp.snu.R;
import com.squareup.picasso.Picasso;

public class HomeGridAdapter extends BaseAdapter {

	private Activity activity;

	HomeGridAdapter(final Activity activity) {
		this.activity = activity;
	}

	@Override
	public int getCount() {
		return HomeItem.ITEMS.length;
	}

	@Override
	public HomeItem getItem(int position) {
		return HomeItem.ITEMS[position];
	}

	@Override
	public long getItemId(int position) {
		return getItem(position).getId();
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {
		if (view == null) {
			view = activity.getLayoutInflater().inflate(R.layout.grid_item, viewGroup, false);
		}

		final HomeItem item = getItem(position);

		// Load the thumbnail image
		ImageView image = (ImageView) view.findViewById(R.id.imageview_item);
		Picasso.with(image.getContext())
				.load(item.getImageViewId())
				.transform(new RoundedCornersTransformation(100, 30))
				.into(image);

		// Set the TextView's contents
		TextView name = (TextView) view.findViewById(R.id.textview_name);
		name.setText(item.getName());
		return view;
	}
}
