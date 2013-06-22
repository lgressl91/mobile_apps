package com.example.bluetoothdevicefinder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class TrackingView extends View {
	private static int Y_VALUE = 1;
	private static float SCALE_CIRCLE_FACTOR = 0.9f;
	private static float DIFF_MAX_MIN_RADIUS = 0.3f;

	Paint my_paint;
	float cur_color;
	Bitmap map;
	Context base_context;
	private CalculateDistance calcdist = CalculateDistance.Instance();

	public TrackingView(Context context) {
		super(context);
		base_context = context;
		my_paint = new Paint();
		my_paint.setColor(0);
		my_paint.setAntiAlias(true);
		my_paint.setStyle(Paint.Style.FILL);
		cur_color = CalculateDistance.COLOR_MAX_DIST;
		map = BitmapFactory.decodeResource(getResources(),
				R.drawable.green_red_advanced);
	}

	@Override
	public void onDraw(Canvas canvas) {
		int canvas_width = canvas.getWidth();
		int canvas_height = canvas.getHeight();
		int next_color = calcdist.getNextColor();

		if (next_color > cur_color)
			cur_color++;
		else if (next_color < cur_color)
			cur_color--;

		if (cur_color < 1)
			cur_color = 1;
		if (cur_color > 256)
			cur_color = 256;

		int calculated_color = map.getPixel((int) cur_color, Y_VALUE);
		my_paint.setColor(calculated_color);

		float radius = SCALE_CIRCLE_FACTOR
				* (canvas_width / 2.0f)
				* (1.0f - ((float) cur_color / CalculateDistance.COLOR_MAX_DIST)
						* DIFF_MAX_MIN_RADIUS);

		canvas.drawCircle(canvas_width / 2.0f, canvas_height / 2.0f, radius,
				my_paint);

		invalidate();
	}
}
