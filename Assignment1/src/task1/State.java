package task1;
import java.util.*;

import eventSchedulingUtils.Event;
import eventSchedulingUtils.GlobalSimulation;

import java.io.*;

class State extends GlobalSimulation{
	
	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int numberInQueueOne = 0, numberInQueueTwo, numberRejected = 0, accumulated = 0, accumulatedRejected = 0, noMeasurements = 0;
	public double lambda_measure = 1/5.0;
	public double lambda_Q1 = 1/2.1;
	Random slump = new Random(); // This is just a random number generator
	
	
	// The following method is called by the main program each time a new event has been fetched
	// from the event list in the main loop. 
	public void treatEvent(Event x){
		switch (x.eventType){
			case ARRIVAL:
				arrival();
				break;
			case READY_ONE:
				readyQ1();
				break;
			case READY_TWO:
				readyQ2();
				break;
			case MEASURE:
				measure();
				break;
		}
	}
	
	
	// The following methods defines what should be done when an event takes place. This could
	// have been placed in the case in treatEvent, but often it is simpler to write a method if 
	// things are getting more complicated than this.
	
	private void arrival(){
		numberInQueueOne++;
		if (numberInQueueOne == 1) {
			insertEvent(READY_ONE, time + Math.log(1-slump.nextDouble())/(-lambda_Q1));			
		} else if (numberInQueueOne == 11) {
			numberInQueueOne--;
			numberRejected++;
		}
		insertEvent(ARRIVAL, time + 1);
	}
	
	private void readyQ1(){
		numberInQueueOne--;
		if (numberInQueueOne > 0)
			insertEvent(READY_ONE, time + Math.log(1-slump.nextDouble())/(-lambda_Q1));
		numberInQueueTwo++;
		if (numberInQueueTwo == 1)
			insertEvent(READY_TWO, time + 2);
	}
	
	private void readyQ2() {
		numberInQueueTwo--;
		if (numberInQueueTwo > 0)
			insertEvent(READY_TWO, time + 2);
	}
	
	private void measure(){
		accumulatedRejected = accumulatedRejected + numberRejected;
		accumulated = accumulated + numberInQueueOne;
		noMeasurements++;
		insertEvent(MEASURE, time + Math.log(1-slump.nextDouble())/(-lambda_measure));
	}
}