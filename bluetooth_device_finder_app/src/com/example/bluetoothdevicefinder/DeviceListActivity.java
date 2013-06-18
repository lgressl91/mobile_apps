/*
 * Copyright (C) 2009 The Android Open Source Project
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

package com.example.bluetoothdevicefinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DeviceListActivity extends Activity {
	// Debugging
	private static final String TAG = "DeviceListActivity";
	public static String EXTRA_DEVICE_ADDRESS = "device_address";
	public static String EXTRA_DEVICE_NAME = "device_name";


	// Member fields
	private BluetoothAdapter mBtAdapter;
	private ArrayAdapter<String> mNewDevicesArrayAdapter;
	private List<BluetoothListItem> itemlist_bluetooth;
	private boolean exists = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e(TAG, "+++ ON CREATE +++");

		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

		setContentView(R.layout.device_list);

		Button scanButton = (Button) findViewById(R.id.button_scan);
		scanButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mNewDevicesArrayAdapter.clear();
				itemlist_bluetooth.clear();
				doDiscovery();
			}
		});

		// Get the local Bluetooth adapter
		mBtAdapter = BluetoothAdapter.getDefaultAdapter();

		BluetoothDevice[] tmp = (BluetoothDevice[]) mBtAdapter
				.getBondedDevices().toArray(new BluetoothDevice[0]);
		Log.d(TAG, "length = " + tmp.length);

		// Log.d(TAG, ".... " + tmp[0].getName().toString());

		mNewDevicesArrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1);
		itemlist_bluetooth = new ArrayList<BluetoothListItem>();

		// Find and set up the ListView for newly discovered devices
		ListView newDevicesListView = (ListView) findViewById(R.id.new_devices);
		newDevicesListView.setAdapter(mNewDevicesArrayAdapter);
		newDevicesListView.setOnItemClickListener(mDeviceClickListener);
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.e(TAG, "++ ON START ++");

		// Register broadcasts for device discovery
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		this.registerReceiver(mReceiver, filter);
		filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		this.registerReceiver(mReceiver, filter);
		
		doDiscovery();
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.e(TAG, "--- ON RESUME ---");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.e(TAG, "-- ON STOP --");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.e(TAG, "--- ON DESTROY ---");
		unregisterReceiver(mReceiver);
	}

	private void doDiscovery() {
		Log.d(TAG, "doDiscovery()");

		// Indicate scanning in the title
		setProgressBarIndeterminateVisibility(true);
		setTitle(R.string.scanning);

		// If we're already discovering, stop it
		if (mBtAdapter.isDiscovering()) {
			mBtAdapter.cancelDiscovery();
		}
		mBtAdapter.startDiscovery();
	}

	private void stopDiscovery() {
		// Log.d(TAG, "stopDiscovery()");

		if (mBtAdapter != null) {
			mBtAdapter.cancelDiscovery();
			Log.d(TAG, "discovery canceled");
		}
		setProgressBarIndeterminateVisibility(false);
		setTitle(R.string.select_device);
	}

	private OnItemClickListener mDeviceClickListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {

			Intent intent = new Intent(getApplicationContext(),
					DeviceFinderActivity.class);
			intent.putExtra(EXTRA_DEVICE_ADDRESS, itemlist_bluetooth.get(arg2)
					.getMacAddress());
			intent.putExtra(EXTRA_DEVICE_NAME, itemlist_bluetooth.get(arg2)
					.getName());
			startActivity(intent);
		}
	};

	// The BroadcastReceiver that listens for discovered devices and
	// changes the title when discovery is finished
	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();

			// When discovery finds a device
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				short rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,
						Short.MIN_VALUE);
				exists = false;
				BluetoothDevice device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				if (device.getBondState() != BluetoothDevice.BOND_BONDED) {

					for (int i = 0; i < itemlist_bluetooth.size(); i++) {
						if (itemlist_bluetooth.get(i).getMacAddress()
								.equals(device.getAddress())) {

							exists = true;
						}
					}
					if (!exists) {
						mNewDevicesArrayAdapter.add(device.getName());
						itemlist_bluetooth.add(new BluetoothListItem(device
								.getName(), device.getAddress()));
					}
				}
			} else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED
					.equals(action)) {
				setProgressBarIndeterminateVisibility(false);
				Log.d(TAG, "discovery finished");
				setTitle(R.string.select_device);
				// message to user
				if (mNewDevicesArrayAdapter.getCount() == 0) {
					// message to user
					Toast.makeText(getApplicationContext(),
							"Keine Geräte gefunden", Toast.LENGTH_SHORT).show();
				}
			}
		}
	};

}
