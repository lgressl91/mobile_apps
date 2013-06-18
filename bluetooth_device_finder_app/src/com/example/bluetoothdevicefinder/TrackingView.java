package com.example.bluetoothdevicefinder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public class TrackingView extends View {
	Paint p = new Paint();
	int next_color;
	float cur_color;
	Bitmap map;
	Context BaseContext;
	private short rssi;
	private CalculateDistance calcdist = CalculateDistance.Instance();

	public TrackingView(Context context) {
		super(context);
		rssi = 0;
		BaseContext = context;
		p.setColor(0);
		p.setAntiAlias(true);
		p.setStyle(Paint.Style.FILL);
		next_color = 256;
		cur_color = 256;
		map = BitmapFactory.decodeResource(getResources(),
				R.drawable.green_red_advanced);
	}

	public void setColor(int rssi) {
		if (rssi > -40)
			rssi = -40;
		if (rssi < -90)
			rssi = -90;
		next_color = (int) (((float) (Math.abs(rssi) - 40) / 50) * 255 + 1);
		Log.d("RSSI", (String.valueOf(rssi)));
	}

	@Override
	public void onDraw(Canvas canvas) {
		int w = canvas.getWidth();
		int h = canvas.getHeight();

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

//		Short s = rssi;
//		String dd = calcdist.getStringDistance();
//
//		TextView textView = new TextView(BaseContext);
//		textView.layout(0, 0, 300, 500); // text box size 300px x 500px
//		textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 50);
//		textView.setTextColor(Color.WHITE);
//		textView.setShadowLayer(5, 2, 2, Color.BLACK); // text shadow
//		textView.setText(dd);
//		textView.setDrawingCacheEnabled(true);
//		canvas.drawBitmap(textView.getDrawingCache(), w / 2.0f,
//				h / 2.0f - 25, null);
		invalidate();
	}

	public void setRSSI(short rssi) {
		this.rssi = rssi;
	}
}
