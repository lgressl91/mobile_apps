package com.example.bluetoothdevicefinder;

public class CalculateDistance {
	private String str_distance;
	private static CalculateDistance instance;
	
	public static CalculateDistance Instance()
	{
		if(instance == null) instance = new CalculateDistance();
		return instance;
	}
	
	private CalculateDistance(){
		str_distance = "";
	}
	
	public String getStringDistance(){
		return str_distance;
	}
	
	public void setRSSI(short rssi){
		if(rssi >= -45) 				str_distance = "Gefunden :-)";
		if(rssi >= -70 && rssi < -60) 	str_distance = "In der Nähe";
		if(rssi < -80)	 				str_distance = "Laufe herum";
	}
	
	public double getDistance(short rssi){
		
		int rssi_c = Math.abs((int)rssi);
		if (rssi_c >30 && rssi_c < 60 ){
			return 0.5;
		}
		else if (rssi_c > 60 && rssi_c < 70 ){
			return 1.5;
		}
		else if (rssi_c > 70 && rssi_c < 80 ){
			return 5;
		}
		else
			return 10;
	}
}
