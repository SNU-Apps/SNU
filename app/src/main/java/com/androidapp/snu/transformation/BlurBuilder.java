package com.androidapp.snu.transformation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

public class BlurBuilder {
	private static final float BITMAP_SCALE = 0.1f;
	private static final float BLUR_RADIUS = 7.5f;

	public static Drawable blur(Context context, final int drawableId) {
		Drawable wallpaperDrawable = context.getResources().getDrawable(drawableId);
		Bitmap bitmap = ((BitmapDrawable) wallpaperDrawable).getBitmap();
		Bitmap blurryBitmap = blurInternal(context, bitmap);
		return new BitmapDrawable(context.getResources(), blurryBitmap);
	}

	public static Drawable blur(Context context, final Bitmap bitmap) {
		Bitmap blurryBitmap = blurInternal(context, bitmap);
		return new BitmapDrawable(context.getResources(), blurryBitmap);
	}

	private static Bitmap blurInternal(Context context, Bitmap image) {
		int width = Math.round(image.getWidth() * BITMAP_SCALE);
		int height = Math.round(image.getHeight() * BITMAP_SCALE);

		Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);
		Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

		RenderScript rs = RenderScript.create(context);
		ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
		Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
		Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
		theIntrinsic.setRadius(BLUR_RADIUS);
		theIntrinsic.setInput(tmpIn);
		theIntrinsic.forEach(tmpOut);
		tmpOut.copyTo(outputBitmap);

		Bitmap newBitmap = Bitmap.createBitmap(outputBitmap.getWidth(), outputBitmap.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(newBitmap);
		Paint alphaPaint = new Paint();
		alphaPaint.setAlpha(0);
		canvas.drawBitmap(outputBitmap, 0, 0, alphaPaint);
		return outputBitmap;
	}
}
