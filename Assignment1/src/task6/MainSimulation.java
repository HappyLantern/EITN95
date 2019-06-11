package task6;

import java.util.*;

import java.io.*;

public class MainSimulation extends GlobalSimulation {

	public static void main(String[] args) throws IOException {
			Event actEvent;
			State actState = new State();
			insertEvent(ARRIVAL, 0);
			insertEvent(MEASURE, 5);
			

			// The main simulation loop

			// time = 0 corresponds to 09:00. time = 28800 corresponds to  17.00
			int workingDays = 0;
			double meanTimeToFinish = 0;
 			while (workingDays < 100000) {
				actEvent = eventList.fetchEvent();
				time = actEvent.eventTime;
				actState.treatEvent(actEvent);
				//System.out.println(actState.numberInQueue);
				if (time > 28800 && actState.numberPrescriptions == 0) {
					meanTimeToFinish += time;
					time = 0;
					workingDays++;
					insertEvent(ARRIVAL, 0);
				}
			}

 			meanTimeToFinish = meanTimeToFinish / workingDays; 
 			System.out.println(meanTimeToFinish / 3600); // Hours from 09:00
 			System.out.println(60 * (meanTimeToFinish - 28800) / 3600); // Minutes from 17:00
 			double timeInSystem = 0;
 			for (int i = 0; i < actState.timeInSystem.size(); i++) {
 				timeInSystem += actState.timeInSystem.get(i);
 			}
 			double meanTimeInSystem = timeInSystem / actState.timeInSystem.size();
 			System.out.println(meanTimeInSystem / 60); // Minutes
	}
}