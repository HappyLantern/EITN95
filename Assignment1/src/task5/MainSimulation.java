package task5;
import java.io.*;


public class MainSimulation extends Global{
	
	private static final double ARRIVAL_TIME = 2;

    public static void main(String[] args) throws IOException {
    	
    	Signal actSignal;
    	new SignalList();
    	

    	QS[] queues = new QS[5];
    	for (int i = 0; i < queues.length; i++) {
    		queues[i] = new QS();
    		queues[i].index = i;
    		queues[i].sendTo = null;
    	}
    	
    	Dispatcher dispatcher = new Dispatcher(queues, Dispatcher.ROUND_ROBIN); // RANDOM / ROUND_ROBIN / SMALLEST_FIRST

    	Gen Generator = new Gen();
    	Generator.lambda = 1/ARRIVAL_TIME;
    	Generator.sendTo = dispatcher;
 
    	SignalList.SendSignal(READY, Generator, time);
    	SignalList.SendSignal(MEASURE, dispatcher, 10);
    	for (int i = 0; i < queues.length; i++)
    		SignalList.SendSignal(MEASURE, queues[i], 10);

    	while (time < 1000000) {
    		actSignal = SignalList.FetchSignal();
    		time = actSignal.arrivalTime;
    		actSignal.destination.TreatSignal(actSignal);
    	}
    	
    	double meanTime = 0;
    	for (int i = 0; i < queues.length; i++) {
    		double meanTimeInQueue = 0;
    		for (int j = 0; j < queues[i].timeInSys.size(); j++) {
    			meanTimeInQueue += queues[i].timeInSys.get(j);
    		}
    		if (queues[i].timeInSys.size() != 0)
    			meanTime = meanTime + meanTimeInQueue / queues[i].timeInSys.size();
    	}
    	meanTime = meanTime / queues.length;
		double meanNoCustomers = 1.0 * dispatcher.accumulated / dispatcher.noMeasurements;
    	System.out.println("Lambda: " + 1/ARRIVAL_TIME);
    	System.out.println("Mean time in system for customer: " + meanTime);
    	System.out.println("Mean number of customers in queuing system: " + meanNoCustomers);
    	System.out.println("Little's law: Lambda * meanTime: " + (1/ARRIVAL_TIME) * meanTime);
    	
    }
}