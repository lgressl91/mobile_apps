package com.example.bluetoothdevicefinder.test;

import static org.junit.Assert.*;
import com.jayway.android.robotium.solo.Solo;
import com.example.bluetoothdevicefinder.MainActivity;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

public class InformationTest extends
		ActivityInstrumentationTestCase2<MainActivity> {

	private Solo solo;

	public InformationTest() {
		super(MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}

	public void testInformation() {
		
		
		Log.d("DEBUG TESTING: ", "started Information Test");
		
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
