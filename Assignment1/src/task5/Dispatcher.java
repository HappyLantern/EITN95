package task5;

import java.util.*;
import java.io.*;

// This class defines a simple queuing system with one server. It inherits Proc so that we can use time and the
// signal names without dot notation
class Dispatcher extends Proc {
	public static final int RANDOM = 0;
	public static final int ROUND_ROBIN = 1;
	public static final int SMALLEST_FIRST = 2;
	public static double noInSystem = 0;
	public static int[] queueSize;
	public double accumulated = 0;
	public int noMeasurements = 0;

	private QS[] queues;
	private Random slump = new Random();
	private int type;
	private int round = 0;

	public Dispatcher(QS[] queues, int type) {
		this.queues = queues;
		this.type = type;
		queueSize = new int[queues.length];
	}

	public void TreatSignal(Signal x) {
		switch (x.signalType) {

		case ARRIVAL:
			int sendTo = 0;
			noInSystem++;
			switch (type) {
			case RANDOM:
				sendTo = slump.nextInt(5);
				SignalList.SendSignal(ARRIVAL, queues[sendTo], time);
				break;
			case ROUND_ROBIN:
				SignalList.SendSignal(ARRIVAL, queues[round], time);
				round = (round + 1) % 5;
				break;
			case SMALLEST_FIRST:
				
				// Get smallest value
				int smallest = queueSize[0];
				for (int i = 1; i < queueSize.length; i++) 
					if (queueSize[i] < smallest) 
						smallest = queueSize[i];
				
				
				// get indices for queues with smallest value	
				ArrayList<Integer> smallestIndices = new ArrayList<Integer>();
				for (int i = 0; i < queueSize.length; i++) 
					if (smallest == queueSize[i]) 
						smallestIndices.add(i);
					
				// Automatically 0 if size == 1.
				sendTo = slump.nextInt(smallestIndices.size());
				sendTo = smallestIndices.get(sendTo);
				queueSize[sendTo]++;
				SignalList.SendSignal(ARRIVAL, queues[sendTo], time);
				break;
			}
			break;
		case MEASURE:
			noMeasurements++;
			accumulated = accumulated + noInSystem;
			SignalList.SendSignal(MEASURE, this, time + 1);
			break;
		}
	}
}