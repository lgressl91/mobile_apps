package com.example.bluetoothdevicefinder.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)

@SuiteClasses({ CalculateDistanceTest.class, BluetoothListItemTest.class,
		DeviceFinderActivityTest.class, InformationTest.class })

public class BluetoothDeviceFinderTestSuite {

}
