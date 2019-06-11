package task4;

import java.io.*;
import java.util.ArrayList;

public class MainSimulation extends GlobalSimulation {

	private static final int NO_MEASUREMENTS = 4000;

	public static void main(String[] args) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("./output/outputTask4.4.txt"));
		Event actEvent;
		State actState = new State();

		insertEvent(ARRIVAL, 0);
		insertEvent(MEASURE, 0.1);

		ArrayList<Integer> customers = new ArrayList<Integer>();
		int numCustomers = 0;
		while (actState.noMeasurements < NO_MEASUREMENTS) {
			actEvent = eventList.fetchEvent();
			if (actEvent.eventType == State.MEASURE) {
				int sample = actState.customers;
				customers.add(sample);
				numCustomers += sample;
				writer.write(Integer.toString(sample));
				writer.newLine();
			}
			time = actEvent.eventTime;
			actState.treatEvent(actEvent);
		}
		double sampleMean = numCustomers / NO_MEASUREMENTS;
		double sampleVariance = 0;
		for(int sample : customers) {
			sampleVariance += Math.pow((sample - sampleMean), 2);
		}
		double meanVariance = sampleVariance / NO_MEASUREMENTS;
		double meanStdev = Math.sqrt(meanVariance / NO_MEASUREMENTS);
		double w = 1.96; // 95% confidence
		double from = sampleMean - w * meanStdev;
		double to = sampleMean + w * meanStdev;

		writer.close();

		System.out.println("Confidence interval: [" + from + "," + to + "]");
		System.out.println("CI Length: " + (to - from));
		System.out.println("Mean: " + 1.0 * actState.accumulated / actState.noMeasurements);
	}
}