package com.androidapp.snu.components.contacts.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidapp.snu.R;

public class ContactView extends ConstraintLayout {
	private Context context;

	public ContactView(Context context) {
		super(context);
		this.context = context;
		ConstraintLayout layout = (ConstraintLayout) LayoutInflater.from(context).inflate(R.layout.component_contact, null);
		layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		this.addView(layout);
	}

	public ContactView setName(final String name) {
		TextView nameView = this.findViewById(R.id.component_contact_name);
		nameView.setText(name);
		return this;
	}

	public ContactView setDetails(final String details) {
		TextView detailsView = this.findViewById(R.id.component_contact_details);
		detailsView.setText(details);
		return this;
	}

	public ContactView setPhoto(final Bitmap bitmap) {
		if(bitmap != null) {
			ImageView image = this.findViewById(R.id.component_contact_card_image);
			image.setImageBitmap(bitmap);
		}
		return this;
	}
}
