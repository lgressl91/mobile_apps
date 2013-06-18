package com.example.bluetoothdevicefinder;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class DeviceFinderActivity extends Activity {
	private BluetoothAdapter mBtAdapter;
	private static final String TAG = "DeviceFinderActivity";
	private String tracked_dev_mac_addr;
	private String tracked_dev_name;
	private TrackingView trackingView;
	private CalculateDistance calcdist = CalculateDistance.Instance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e(TAG, "+++ ON CREATE +++");

		tracked_dev_name = getIntent().getStringExtra(
				DeviceListActivity.EXTRA_DEVICE_NAME);

		setTitle(tracked_dev_name);

		trackingView = new TrackingView(this);
		setContentView(trackingView);

		tracked_dev_mac_addr = getIntent().getStringExtra(
				DeviceListActivity.EXTRA_DEVICE_ADDRESS);
		Log.d("DeviceFinder", "found Mac address: " + tracked_dev_mac_addr);

		// Register for broadcasts when a device is discovered
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		this.registerReceiver(mReceiver, filter);

		// Register for broadcasts when discovery has finished
		filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		this.registerReceiver(mReceiver, filter);

		mBtAdapter = BluetoothAdapter.getDefaultAdapter();
		startTracking();
	}

	private void startTracking() {
		Log.d(TAG, "startTracking()");

		if (mBtAdapter.isDiscovering()) {
			mBtAdapter.cancelDiscovery();
		}
		mBtAdapter.startDiscovery();
	}

	private void paint(int rssi) {
		trackingView.setColor(rssi);
	}

	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				BluetoothDevice device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				if (device.getAddress().equals(tracked_dev_mac_addr)) {
					short rssi = intent.getShortExtra(
							BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE);
					Log.d(TAG, "Singal=" + rssi);
					paint(rssi);
					calcdist.setRSSI(rssi);
					trackingView.setRSSI(rssi);
					startTracking();
				} else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED
						.equals(action)) {
					startTracking();
				}
			}
		}
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.e(TAG, "--- ON DESTROY ---");

		if (mBtAdapter != null) {
			mBtAdapter.cancelDiscovery();
		}
		this.unregisterReceiver(mReceiver);
		mBtAdapter.cancelDiscovery();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.device_finder, menu);
		return true;
	}

}
