package task7;
import java.io.*;

//Denna klass ärver Global så att man kan använda time och signalnamnen utan punktnotation
//It inherits Proc so that we can use time and the signal names without dot notation


public class MainSimulation extends Global{
	

    public static void main(String[] args) throws IOException {
    	
    	Signal actSignal;
    	new SignalList();
    	
    	QS[] queues = new QS[5];
    	for (int i = 0; i < queues.length; i++) {
    		queues[i] = new QS();
    		queues[i].sendTo = null;
    	}
    	
    	// Breakdown dependencies
    	queues[0].sendTo = new Proc[2];
    	queues[0].sendTo[0] = queues[1];
    	queues[0].sendTo[1] = queues[4];
    	queues[2].sendTo = new Proc[1];
    	queues[2].sendTo[0] = queues[3];
    	
    	SystemStarter systemStart = new SystemStarter(queues);
    	SignalList.SendSignal(START, systemStart, time);
    	
    	double timeToBreak = 0;
    	int timesBroken = 0;
    	boolean brokenDown = false;
    	while (timesBroken < 1000) {
    		actSignal = SignalList.FetchSignal();
    		time = actSignal.arrivalTime;
    		actSignal.destination.TreatSignal(actSignal);
    		
    		brokenDown = true;
    		for (int i = 0; i < queues.length; i++)
    			if (!queues[i].broken)
    				brokenDown = false;
    		
    		if (brokenDown) {
    			timeToBreak += time;
    			time = 0;
    			timesBroken++;
    			SignalList.SendSignal(START, systemStart, time);
    		}
    	}
    	
    	System.out.println(timeToBreak / timesBroken);
    }
}