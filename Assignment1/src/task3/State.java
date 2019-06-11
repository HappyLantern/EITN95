package task3;

import java.util.*;


import java.io.*;

class State extends GlobalSimulation{
	
	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int numberInQueueOne = 0, numberInQueueTwo, accumulated = 0, accumulatedOne = 0, accumulatedTwo = 0, noMeasurements = 0, noArrived = 0;
	public double accumulatedTime = 0, queueTime = 0;
	public static final double MU = 1;
	private static final double MEAN_ONE = 2.0, MEAN_TWO = 1.5, MEAN_THREE = 1.1;
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
		double meanArrivalTime = MEAN_ONE;
		//double meanArrivalTime = MEAN_TWO;
		//double meanArrivalTime = MEAN_THREE;
		double lambda = 1 / meanArrivalTime;
		double meanServiceTime = 1;
		double mu = 1 / meanServiceTime;
		
		numberInQueueOne++;
		if (numberInQueueOne == 1) {
			double expTime = Math.log(1-slump.nextDouble())/(-mu);
			queueTime += expTime;
			insertEvent(READY_ONE, time + expTime);		
		}
		insertEvent(ARRIVAL, time + Math.log(1-slump.nextDouble())/(-lambda));
	}
	
	private void readyQ1(){
		double meanServiceTime = 1;
		double mu = 1 / meanServiceTime;

		numberInQueueOne--;
		if (numberInQueueOne > 0) {
			double expTime = Math.log(1-slump.nextDouble())/(-mu);
			queueTime += expTime;
			insertEvent(READY_ONE, time + expTime);
		}

		numberInQueueTwo++;
		if (numberInQueueTwo == 1) {
			double expTime = Math.log(1-slump.nextDouble())/(-mu);
			queueTime += expTime;
			insertEvent(READY_TWO, time + expTime);
		}
	}
	
	private void readyQ2() {
		double meanServiceTime = 1;
		double mu = 1 / meanServiceTime;

		numberInQueueTwo--;
		if (numberInQueueTwo > 0) {
			double expTime = Math.log(1-slump.nextDouble())/(-mu);
			queueTime += expTime;
			insertEvent(READY_TWO, time + expTime);
		}
	}
	
	private void measure(){
		double meanMeasureTime = 5.0;
		double lambda = 1 / meanMeasureTime;

		accumulatedOne += numberInQueueOne;
		accumulatedTwo += numberInQueueTwo;
		accumulated += numberInQueueOne + numberInQueueTwo;
		accumulatedTime += queueTime;
		noMeasurements++;
		insertEvent(MEASURE, time + Math.log(1-slump.nextDouble())/(-lambda));
	}
}