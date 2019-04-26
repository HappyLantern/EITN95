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

		int i = 1;
		int totalCustomers = 0;
		double variance = 0;
		while (actState.noMeasurements < NO_MEASUREMENTS) {
			actEvent = eventList.fetchEvent();
			if (actEvent.eventType == State.MEASURE) {
				int sample = actState.customers;
				totalCustomers += sample;
				double mean = totalCustomers / i;
				variance += (sample - mean) * (sample - mean);
				writer.write(Integer.toString(sample));
				writer.newLine();
			}
			time = actEvent.eventTime;
			actState.treatEvent(actEvent);
			i++;
		}
		double sample_mean = totalCustomers / NO_MEASUREMENTS;
		double mean_variance = variance / NO_MEASUREMENTS;
		double mean_stdev = Math.sqrt(mean_variance / NO_MEASUREMENTS);
		double w = 1.96; // 95% confidence
		double from = sample_mean - w * mean_stdev;
		double to = sample_mean + w * mean_stdev;

		writer.close();

		System.out.println("Confidence interval: [" + from + "," + to + "]");
		System.out.println("CI Length: " + (to - from));
		System.out.println("Mean: " + 1.0 * actState.accumulated / actState.noMeasurements);
	}
}