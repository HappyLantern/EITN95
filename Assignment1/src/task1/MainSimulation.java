package task1;

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

			while (time < 1000000) {
				actEvent = eventList.fetchEvent();
				time = actEvent.eventTime;
				actState.treatEvent(actEvent);
			}

			// Printing the result of the simulation, in this case a mean value
			System.out.println((1.0 * actState.accumulatedRejected / (actState.accumulatedRejected + actState.accumulated))/actState.noMeasurements);
	}
}