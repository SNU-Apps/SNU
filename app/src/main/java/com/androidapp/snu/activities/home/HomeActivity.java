/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.androidapp.snu.activities.home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;

import com.androidapp.snu.R;
import com.androidapp.snu.activities.wishes.CreateWishActivity;

public class HomeActivity extends Activity implements AdapterView.OnItemClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Window window = getWindow();
		window.setStatusBarColor(Color.argb(255,0,0,0));

		super.onCreate(savedInstanceState);
		setContentView(R.layout.grid);

		// Setup the GridView and set the adapter
		GridView mGridView = findViewById(R.id.grid);
		mGridView.setOnItemClickListener(this);
		HomeGridAdapter mAdapter = new HomeGridAdapter(this);
		mGridView.setAdapter(mAdapter);
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
		HomeItem item = (HomeItem) adapterView.getItemAtPosition(position);

		Intent intent = new Intent(this, item.getTargetActivity());
		intent.putExtra(CreateWishActivity.EXTRA_PARAM_ID, item.getId());

		/*
		ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
				this,
				new Pair<View, String>(view.findViewById(R.id.imageview_item),
						CreateWishActivity.VIEW_NAME_HEADER_IMAGE));
				//new Pair<View, String>(view.findViewById(R.id.textview_name),
				//		CreateWishActivity.VIEW_NAME_HEADER_TITLE));
*/
		// Now we can start the Activity, providing the activity options as a bundle
		//ActivityCompat.startActivity(this, intent, activityOptions.toBundle());
		ActivityCompat.startActivity(this, intent, null);
	}
}
