package com.example.bluetoothdevicefinder;

public class BluetoothListItem {
	
	private String name;
	private String mac_address;

	
	public BluetoothListItem(String n, String a) {
		this.name = n;
		this.mac_address = a;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMacAddress() {
		return mac_address;
	}

	public void setMac_address(String mac_address) {
		this.mac_address = mac_address;
	}
}
