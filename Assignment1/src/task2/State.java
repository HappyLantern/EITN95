package task2;
import java.util.*;

import java.io.*;

class State extends GlobalSimulation{
	
	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public double noJobB = 0, noJobA = 0, accumulated = 0, noMeasurements = 0;
	public static final double lambda_measure = 1/5.0;
	public static final double lambda_Q1 = 1/2.1;
	private static final double lambda_arrival = 150;
	private static final double SERVICE_TIME_A = 0.002;
	private static final double SERVICE_TIME_B = 0.004;
	private boolean priorityInversion = true;
	Random slump = new Random(); // This is just a random number generator
	
	 
	// The following method is called by the main program each time a new event has been fetched
	// from the event list in the main loop. 
	public void treatEvent(Event x){
		switch (x.eventType){
			case ARRIVAL:
				arrival();
				break;
			case READY_ONE:
				jobA();
				break;
			case DELAY:
				delay();
				break;
			case READY_TWO:
				jobB();
				break;
			case MEASURE:
				measure();
				break;
		}
	}
	
	// The following methods defines what should be done when an event takes place. This could
	// have been placed in the case in treatEvent, but often it is simpler to write a method if 
	// things are getting more complicated than this.
	
	private void arrival() {
		noJobA++;
		if (noJobA + noJobB == 1) {
			insertEvent(READY_ONE, time + SERVICE_TIME_A);			
		}
		insertEvent(ARRIVAL, time + Math.log(1-slump.nextDouble())/(-lambda_arrival));
	}
	
	private void jobA() {
		insertEvent(DELAY, time + 1);
		noJobA--;
		
		if (priorityInversion) {
			if (noJobA > 0) 
				insertEvent(READY_ONE, time + SERVICE_TIME_A);
			else if (noJobB > 0)
				insertEvent(READY_TWO, time + SERVICE_TIME_B); 
		} else {
		if (noJobB > 0) 
			insertEvent(READY_TWO, time + SERVICE_TIME_B); 
		else if (noJobA > 0)
			insertEvent(READY_ONE, time + SERVICE_TIME_A);
		}
			
	}
	
	private void jobB() {
		noJobB--;
		
		if (priorityInversion) {
			if (noJobA > 0)
			insertEvent(READY_ONE, time + SERVICE_TIME_A);
		else if (noJobB > 0) 
			insertEvent(READY_TWO, time + SERVICE_TIME_B);
		} else {
			
		if (noJobB > 0)
			insertEvent(READY_TWO, time + SERVICE_TIME_B);
		else if (noJobA > 0) 
			insertEvent(READY_ONE, time + SERVICE_TIME_A);
		}
	}
	
	private void delay() {
		noJobB++;
        if (noJobA + noJobB == 1)
        	insertEvent(READY_TWO, time + SERVICE_TIME_B);
	}
	
	private void measure() {
			accumulated += noJobA + noJobB;
			noMeasurements++;
			insertEvent(MEASURE, time + 0.1);
	}
}