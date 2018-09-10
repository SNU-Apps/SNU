package com.androidapp.snu.components.bigbutton;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.androidapp.snu.R;

public class BigButton extends ConstraintLayout {

	public BigButton(Context context) {
		super(context);
		ConstraintLayout layout = (ConstraintLayout) LayoutInflater.from(context).inflate(R.layout.component_big_button, null);
		layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		this.addView(layout);
	}

	public BigButton setText(final String text) {
		TextView textView = this.findViewById(R.id.component_big_button_text);
		textView.setText(text);
		return this;
	}
}
