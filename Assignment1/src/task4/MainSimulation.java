package task4;


import eventSchedulingUtils.Event;
import eventSchedulingUtils.GlobalSimulation;

import java.io.*;

public class MainSimulation extends GlobalSimulation {
	
	private static final int NO_MEASUREMENTS = 1000;

	public static void main(String[] args) throws IOException {
			BufferedWriter writer = new BufferedWriter(new FileWriter("./output/outputTask4.4.txt"));
			Event actEvent;
			State actState = new State(); 
			
			insertEvent(ARRIVAL, 0);
			insertEvent(MEASURE, 0.1);
			
			while (actState.noMeasurements < NO_MEASUREMENTS) {
				actEvent = eventList.fetchEvent();
				if (actEvent.eventType == State.MEASURE) {
					System.out.println(actState.customers);
					writer.write(Integer.toString(actState.customers));
					writer.newLine();
				}
				time = actEvent.eventTime;
				actState.treatEvent(actEvent);
			}
			
			writer.close();

			// Printing the result of the simulation, in this case a mean value
			System.out.println(actState.accumulated);
			System.out.println(actState.noMeasurements);
			System.out.println(1.0 * actState.accumulated / actState.noMeasurements);
	}
}