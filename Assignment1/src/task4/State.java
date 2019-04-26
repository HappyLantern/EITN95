package task4;
import java.util.*;

import eventSchedulingUtils.Event;
import eventSchedulingUtils.GlobalSimulation;

import java.io.*;

class State extends GlobalSimulation{
	
	public int customers, accumulated, noMeasurements = 0;
	public static final double MEASURE_TIME = 4;
	private static final double LAMBDA_ARRIVAL = 4;
	private static final double SERVICE_TIME = 10;
	private static final int SERVERS = 100;
	Random slump = new Random();
	public void treatEvent(Event x){
		switch (x.eventType){
			case ARRIVAL:
				arrival();
				break;
			case READY_ONE:
				ready();
				break;
			case MEASURE:
				measure();
				break;
		}
	}
	
	private void arrival() {
		if (customers < SERVERS) {
			customers++;
			insertEvent(READY_ONE, time + SERVICE_TIME);			
		}
		insertEvent(ARRIVAL, time + Math.log(1-slump.nextDouble())/(-LAMBDA_ARRIVAL));
	}
	
	private void ready() {
		customers--;
	}
	
	private void measure() {
			accumulated += customers;
			noMeasurements++;
			insertEvent(MEASURE, time + MEASURE_TIME);
	}
}