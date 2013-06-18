package com.example.bluetoothdevicefinder.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;

import com.jayway.android.robotium.solo.Condition;
import com.jayway.android.robotium.solo.Solo;
import com.example.bluetoothdevicefinder.MainActivity;
import com.example.bluetoothdevicefinder.DeviceFinderActivity;
import com.example.bluetoothdevicefinder.DeviceListActivity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.ActivityNotFoundException;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.example.bluetoothdevicefinder.R;

public class DeviceFinderActivityTest extends
		ActivityInstrumentationTestCase2<MainActivity> {

	private Solo solo;
	private Integer size;

	public DeviceFinderActivityTest() {
		super(MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}

	public void testDeviceListActivity() {
		
		
		Log.d("DEBUG TESTING: ", "started Finder Test");
		
		solo.clickOnButton("Suche Bluetooth Geräte");
		solo.assertCurrentActivity("Search Bluetooth Devices Test", DeviceListActivity.class);
		
		assertTrue(solo.searchText("Suche läuft..."));
		
		ArrayList<ListView> myList = solo.getCurrentViews(ListView.class);
		
		solo.waitForText("Wähle das zu suchende Gerät");
		
		assertTrue(solo.searchText("Wähle das zu suchende Gerät"));
		
		size = myList.get(0).getCount();
		
		Log.d("DEBUG TESTING: size = ", size.toString());
		
		if(size == 0)
		{
			Log.d("DEBUG TESTING: size = ", "no devices found");
			solo.assertCurrentActivity("List Bluetooth Devices Test", DeviceListActivity.class);
		}
		
		else
		{
			Log.d("DEBUG TESTING: size = ", "some devices found");
			for(Integer i = 1; i <= size; i++)
			{
			Log.d("DEBUG TESTING:", "Loop = " + i.toString());
				solo.clickInList(i);
				solo.waitForActivity(DeviceFinderActivity.class);
				solo.assertCurrentActivity("Find Bluetooth Device Test", DeviceFinderActivity.class);
				solo.goBack();
				solo.assertCurrentActivity("List Bluetooth Device Test", DeviceListActivity.class);
			}
		}
		
		solo.goBack();
		solo.assertCurrentActivity("Main Activity Test", MainActivity.class);
		
		solo.clickOnButton("Info");
		assertTrue("Information opened", solo.searchText("Information"));

		solo.clickOnButton("Gelesen");

		solo.assertCurrentActivity("Main Activity Test", MainActivity.class);
		
	}

	@Override
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}
}
