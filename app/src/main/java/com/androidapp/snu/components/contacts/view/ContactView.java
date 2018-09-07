package com.androidapp.snu.components.contacts.view;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
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

	public ContactView setNumber(final String number) {
		TextView numberView = this.findViewById(R.id.component_contact_number);
		numberView.setText(number);
		return this;
	}
}
