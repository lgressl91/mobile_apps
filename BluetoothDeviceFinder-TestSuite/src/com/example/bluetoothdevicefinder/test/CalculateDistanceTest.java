package com.example.bluetoothdevicefinder.test;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

import com.example.bluetoothdevicefinder.CalculateDistance;
import android.test.AndroidTestCase;

public class CalculateDistanceTest extends AndroidTestCase{
	
	CalculateDistance calcTest = CalculateDistance.Instance();
	double result;
	boolean test;

	@Test
	public void testCalcDistance1() {
		result = calcTest.getDistance((short) -40);
		if(result == 0.5)
		{
			test = true;
		}
		else
		{
			test = false;
		}
		Assert.assertEquals(true, test);
	}
	
	@Test
	public void testCalcDistance2() {
		result = calcTest.getDistance((short) -65);
		if(result == 1.5)
		{
			test = true;
		}
		else
		{
			test = false;
		}
		Assert.assertEquals(true, test);
	}
	
	@Test
	public void testCalcDistance3() {
		result = calcTest.getDistance((short) -75);
		if(result == 5.0)
		{
			test = true;
		}
		else
		{
			test = false;
		}
		Assert.assertEquals(true, test);
	}
	
	@Test
	public void testCalcDistance4() {
		result = calcTest.getDistance((short) -90);
		if(result == 10.0)
		{
			test = true;
		}
		else
		{
			test = false;
		}
		Assert.assertEquals(true, test);
	}
}
