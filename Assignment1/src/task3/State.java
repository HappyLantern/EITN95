package task3;
import java.util.*;

import eventSchedulingUtils.*;

import java.io.*;

class State extends GlobalSimulation{
	
	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int numberInQueueOne = 0, numberInQueueTwo, accumulated = 0, noMeasurements = 0;
	public double accumulatedTime = 0;
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
		double meanArrivalTime = 2.0; // 2, 1.5, 1.1
		double lambda = 1 / meanArrivalTime;

		numberInQueueOne++;
		boolean wasEmpty = numberInQueueOne == 1;
		if (wasEmpty)
			insertEvent(READY_ONE, time + Math.log(1-slump.nextDouble())/(-lambda));			
		insertEvent(ARRIVAL, time + 1);
	}
	
	private void readyQ1(){
		double meanServiceTime = 1;
		double mu = 1 / meanServiceTime;

		numberInQueueOne--;
		boolean stillNotEmpty = numberInQueueOne > 0;
		if (stillNotEmpty)
			insertEvent(READY_ONE, time + Math.log(1-slump.nextDouble())/(-mu));

		numberInQueueTwo++;
		boolean queueTwoWasEmpty = numberInQueueTwo == 1;
		if (queueTwoWasEmpty)
			insertEvent(READY_TWO, time + 2);
	}
	
	private void readyQ2() {
		double meanServiceTime = 1;
		double mu = 1 / meanServiceTime;

		numberInQueueTwo--;
		boolean queueTwoStillNotEmpty = numberInQueueTwo > 0;
		if (queueTwoStillNotEmpty)
			insertEvent(READY_TWO, time + Math.log(1-slump.nextDouble())/(-mu));
	}
	
	private void measure(){
		double meanMeasureTime = 5.0;
		double lambda = 1/meanMeasureTime;

		accumulated = accumulated + numberInQueueOne;
		accumulatedTime = accumulatedTime + time;
		noMeasurements++;
		insertEvent(MEASURE, time + Math.log(1-slump.nextDouble())/(-lambda));
	}
}