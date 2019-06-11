package task6;
import java.util.*;


import java.io.*;

class State extends GlobalSimulation{
	
	public int numberPrescriptions = 0, noArrived = 0, noRejected = 0, accumulated = 0, accumulatedRejected = 0, noMeasurements = 0;
	public double lambda = 1.0/900;
	public Queue<Double> prescriptions = new LinkedList<Double>();
	public ArrayList<Double> timeInSystem = new ArrayList<Double>();
	
	public Random slump = new Random(); 
	
	public void treatEvent(Event x){
		switch (x.eventType){
			case ARRIVAL:
				arrival();
				break;
			case READY_ONE:
				ready();
				break;
			case MEASURE:
				measure();
				break;
		}
	}
	
	private void arrival(){
		noArrived++;
		numberPrescriptions++;
		prescriptions.add(time);
		if (numberPrescriptions == 1) 
			insertEvent(READY_ONE, time + (600*slump.nextDouble() + 600));			
		if (time < 28800)  
			insertEvent(ARRIVAL, time + Math.log(1-slump.nextDouble())/(-lambda));
		
	}
	
	private void ready(){
		numberPrescriptions--;
		timeInSystem.add(time - prescriptions.poll());
		if (numberPrescriptions > 0)
			insertEvent(READY_ONE, time + (600*slump.nextDouble() + 600));

	}
	
	private void measure(){
		accumulated += noArrived;
		noMeasurements++;
		insertEvent(MEASURE, time + 1);
	}
}