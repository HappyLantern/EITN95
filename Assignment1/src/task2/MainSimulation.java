package task2;

import java.util.*;

import java.io.*;

public class MainSimulation extends GlobalSimulation {

	public static void main(String[] args) throws IOException {
			Event actEvent;
			State actState = new State(); // The state that shoud be used
			// Some events must be put in the event list at the beginning
			insertEvent(ARRIVAL, 0);
			insertEvent(MEASURE, 0.1);

			// The main simulation loop

			while (actState.noMeasurements < 100000) {
				actEvent = eventList.fetchEvent();
				time = actEvent.eventTime;
				actState.treatEvent(actEvent);
			}

			// Printing the result of the simulation, in this case a mean value
			System.out.println(actState.accumulated);
			System.out.println(actState.noMeasurements);
			System.out.println(1.0 * actState.accumulated / actState.noMeasurements);
	}
}