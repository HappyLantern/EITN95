package task1;

import java.util.*;



import java.io.*;

public class MainSimulation extends GlobalSimulation {

	public static void main(String[] args) throws IOException {
			Event actEvent;
			State actState = new State(); // The state that shoud be used
			// Some events must be put in the event list at the beginning
			insertEvent(ARRIVAL, 0);
			insertEvent(MEASURE, 5);

			// The main simulation loop

			while (time < 100) {
				actEvent = eventList.fetchEvent();
				time = actEvent.eventTime;
				actState.treatEvent(actEvent);
			}

			// Printing the result of the simulation, in this case a mean value
			System.out.println(actState.noRejected);
			System.out.println(actState.noArrived);
			System.out.println(1.0 * actState.noRejected / actState.noArrived);
	}
}