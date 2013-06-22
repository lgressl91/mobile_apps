package com.example.bluetoothdevicefinder;

public class CalculateDistance {
	private int next_color;
	private static CalculateDistance instance;

	public static final int COLOR_MAX_DIST = 256;
	public static final short RSSI_MIN_VALUE = -40;
	public static final short RSSI_MAX_VALUE = -90;

	public static CalculateDistance Instance() {
		if (instance == null)
			instance = new CalculateDistance();
		return instance;
	}

	private CalculateDistance() {
		next_color = COLOR_MAX_DIST;
	}

	public int getNextColor() {
		return next_color;
	}

	public void setRSSI(short rssi) {
		if (rssi > RSSI_MIN_VALUE)
			rssi = RSSI_MIN_VALUE;
		if (rssi < RSSI_MAX_VALUE)
			rssi = RSSI_MAX_VALUE;
		next_color = (int) (((float) (Math.abs(rssi) + RSSI_MIN_VALUE) / (RSSI_MIN_VALUE - RSSI_MAX_VALUE)) * COLOR_MAX_DIST);
	}
}
