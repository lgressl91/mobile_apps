package com.example.bluetoothdevicefinder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

public class TrackingView extends View {
	Paint p = new Paint();
	float cur_color;
	Bitmap map;
	Context BaseContext;
	private CalculateDistance calcdist = CalculateDistance.Instance();

	public TrackingView(Context context) {
		super(context);
		BaseContext = context;
		p.setColor(0);
		p.setAntiAlias(true);
		p.setStyle(Paint.Style.FILL);
		cur_color = 256;
		map = BitmapFactory.decodeResource(getResources(),
				R.drawable.green_red_advanced);
	}

	@Override
	public void onDraw(Canvas canvas) {
		int w = canvas.getWidth();
		int h = canvas.getHeight();
		int next_color = calcdist.getNextColor();

		if (next_color > cur_color)
			cur_color++;
		else if (next_color < cur_color)
			cur_color--;

		if (cur_color < 1)
			cur_color = 1;
		if (cur_color > 256)
			cur_color = 256;

		int c = map.getPixel((int) cur_color, 1);
		p.setColor(c);

		canvas.drawCircle(w / 2.0f, h / 2.0f, 0.9f * w / 2.0f
				* (1.0f - ((float) cur_color / 256) * 0.3f), p);

		invalidate();
	}
}
