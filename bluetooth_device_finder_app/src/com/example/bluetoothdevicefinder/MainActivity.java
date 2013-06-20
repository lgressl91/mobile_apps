package com.example.bluetoothdevicefinder;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.support.v4.app.DialogFragment;

public class MainActivity extends FragmentActivity {
	// Debugging
	private static final String TAG = "BluetoothActivity";

	// Key names received from the BluetoothChatService Handler
	public static final String DEVICE_NAME = "device_name";
	public static final String TOAST = "toast";

	// Intent request codes
	private static final int REQUEST_ENABLE_BT = 3;

	// Layout Views
	private Button mChooseButton;
	// Layout Views
	private Button mConnectButton;

	// Local Bluetooth adapter
	private BluetoothAdapter mBluetoothAdapter = null;

	/**
	 * EVENTS
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e(TAG, "+++ ON CREATE +++");

		// Set up the window layout
		setContentView(R.layout.activity_main);

		// Get the local Bluetooth adapter
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

		// If the adapter is null, then Bluetooth is not supported
		if (mBluetoothAdapter == null) {
			Toast.makeText(this, "Bluetooth is not available",
					Toast.LENGTH_LONG).show();
			finish();
			return;
		}

		mChooseButton = (Button) findViewById(R.id.btn_info);
		mChooseButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Log.e(TAG, "CLICK: Info");
				showDialog();
			}
		});
	}

	@Override
	public void onStart() {
		super.onStart();
		Log.e(TAG, "++ ON START ++");

		// If BT is not on, request that it be enabled.
		if (!mBluetoothAdapter.isEnabled()) {
			Intent enableIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
		} else {
			setupBT();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.d(TAG, "+ ON RESUME +");
	}

	private void setupBT() {
		mConnectButton = (Button) findViewById(R.id.btn_find_devices);
		mConnectButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Log.d(TAG,"CLICK: List Devices");
				Intent serverIntent = new Intent(MainActivity.this,
						DeviceListActivity.class);
				startActivity(serverIntent);
			}
		});
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.e(TAG, "- ON PAUSE -");
	}

	@Override
	public void onStop() {
		super.onStop();
		Log.e(TAG, "-- ON STOP --");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.e(TAG, "--- ON DESTROY ---");
	}

	private void ensureDiscoverable() {
		if (mBluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
//			Log.d(TAG,"Bluetooth Dialog shown");
			Intent discoverableIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
			discoverableIntent.putExtra(
					BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 200);
			startActivity(discoverableIntent);
		}
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_ENABLE_BT:
			// When the request to enable Bluetooth returns
			if (resultCode == Activity.RESULT_OK) {
				// Bluetooth is now enabled, so set up a chat session
				Log.d(TAG,"BT enabled");
				setupBT();
			} else {
				// User did not enable Bluetooth or an error occurred
				Log.d(TAG, "BT not enabled");
				Toast.makeText(this, R.string.bt_not_enabled_leaving,
						Toast.LENGTH_SHORT).show();
				finish();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.discoverable:
			// Ensure this device is discoverable by others
			ensureDiscoverable();
			return true;
		}
		return false;
	}
	
	
	void showDialog() {
		DialogFragment newFragment = InformationDialogFragment.newInstance(
                R.string.dialog_title);
        newFragment.show(getSupportFragmentManager(), "dialog");
    }

    public void doPositiveClick() {
        // Do stuff here.
        Log.i("FragmentAlertDialog", "Positive click!");
    }
    
    public void doNegativeClick() {
        // Do stuff here.
        Log.i("FragmentAlertDialog", "Negative click!");
    }
    
    public BluetoothAdapter getBluetootAdapter()
    {
    	return this.mBluetoothAdapter;
    }
}


