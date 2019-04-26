package task3;
import java.util.*;
import java.io.*;
import eventSchedulingUtils.*;
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
			System.out.println("Mean number of customers" + 1.0 * actState.accumulated / actState.noMeasurements);
			System.out.println("Mean time" + 1.0 * actState.accumulatedTime / actState.noMeasurements);
	}
}