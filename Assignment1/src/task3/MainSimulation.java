package task3;

import java.util.*;

import eventSchedulingUtils.Event;
import eventSchedulingUtils.GlobalSimulation;

import java.io.*;

public class MainSimulation extends GlobalSimulation {

	public static void main(String[] args) throws IOException {
			Event actEvent;
			State actState = new State(); // The state that shoud be used
			// Some events must be put in the event list at the beginning
			insertEvent(ARRIVAL, 0);
			insertEvent(MEASURE, 5);

			// The main simulation loop

			while (time < 10000000) {
				actEvent = eventList.fetchEvent();
				time = actEvent.eventTime;
				actState.treatEvent(actEvent);
			}

			// Printing the result of the simulation, in this case a mean value
			
			double meanNumCustomers = 1.0 * actState.accumulated / actState.noMeasurements;
			System.out.println("Mean number of customers: " + meanNumCustomers);
			int numServers = 2;
			double meanTime = (meanNumCustomers + numServers) * actState.MU;
			System.out.println("Mean time: " + meanTime);
	}
}