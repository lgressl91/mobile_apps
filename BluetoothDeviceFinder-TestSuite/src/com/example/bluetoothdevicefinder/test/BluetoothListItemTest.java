package com.example.bluetoothdevicefinder.test;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

import com.example.bluetoothdevicefinder.BluetoothListItem;
import android.test.AndroidTestCase;

public class BluetoothListItemTest extends AndroidTestCase {

	String name = "Name";
	String mac_adress = "Mac_Adress";

	BluetoothListItem myBluetoothListItemTest = new BluetoothListItem(name,
			mac_adress);
	boolean test;

	@Test
	public void testBluetoothListItemName() {
		test = false;

		if (myBluetoothListItemTest.getName().equals(name)) {
			test = true;
		}

		Assert.assertEquals(true, test);
	}
	
	@Test
	public void testBluetoothListItemMacAdress() {
		test = false;

		if (myBluetoothListItemTest.getMacAddress().equals(mac_adress)) {
			test = true;
		}

		Assert.assertEquals(true, test);
	}
}
