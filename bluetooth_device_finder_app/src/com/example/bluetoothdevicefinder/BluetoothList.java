package com.example.bluetoothdevicefinder;

import java.util.ArrayList;
import java.util.List;
import android.widget.ArrayAdapter;

/**
 * A class with two lists.
 * @param mNewDevicesArrayAdapter: output for list view 
 * @param itemlistBluetooth: keeps track of the whole BluetoothListItem 
*/
public class BluetoothList {

	private ArrayAdapter<String> mNewDevicesArrayAdapter;
	private List<BluetoothListItem> itemlistBluetooth;
	
	/**
	 * Default constructor
	 * @param dla: needed for ArrayAdapter<String>
	 */
	public BluetoothList(DeviceListActivity dla)
	{
		mNewDevicesArrayAdapter = new ArrayAdapter<String>(dla,
				android.R.layout.simple_list_item_1);
		itemlistBluetooth = new ArrayList<BluetoothListItem>();
	}
	
	public void clear()
	{
		mNewDevicesArrayAdapter.clear();
		itemlistBluetooth.clear();
	}
	
	public ArrayAdapter<String> getDevicesArrayAdapter()
	{
		return mNewDevicesArrayAdapter;
	}
	
	public BluetoothListItem getListItem(int index)
	{
		return itemlistBluetooth.get(index);
	}
	
	public void add(String name, String address)
	{
		itemlistBluetooth.add(new BluetoothListItem(name, address));
		
		mNewDevicesArrayAdapter.add(name);
	}
	
	public boolean inItemList(String device_address)
	{
		for (int i = 0; i < itemlistBluetooth.size(); i++) {
			if (itemlistBluetooth.get(i).getMacAddress()
					.equals(device_address)) {

				return true;
			}
		}
		
		return false;
	}
	
	public boolean isArrayAdapterEmpty()
	{
		if (mNewDevicesArrayAdapter.getCount() == 0) {
			return true;
		}
		
		return false;
	}
}
