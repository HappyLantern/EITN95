package task5;
import java.util.*;
import java.io.*;

// This class defines a simple queuing system with one server. It inherits Proc so that we can use time and the
// signal names without dot notation
class QS extends Proc{
	public int numberInQueue, noMeasurements, accumulated = 0;
	public double timeInSystem = 0;
	public Proc sendTo;
	public ArrayList<Double> timeInSys = new ArrayList<Double>();
	Random slump = new Random();
	Queue<Double> queue = new LinkedList<Double>();
	public int index;
	

	public void TreatSignal(Signal x){
		switch (x.signalType){
			case ARRIVAL:
				queue.add(time);
				numberInQueue++;
				if (queue.size() == 1)
					SignalList.SendSignal(READY,this, time + Math.log(1-slump.nextDouble())/(-2));
			 break;
			case READY:
				Dispatcher.noInSystem--;
				Dispatcher.queueSize[index]--;
				
				numberInQueue--;
				timeInSystem = time - queue.poll();
				timeInSys.add(timeInSystem);
				if (queue.size() > 0)
					SignalList.SendSignal(READY, this, time + Math.log(1-slump.nextDouble())/(-2));
			 break;
			case MEASURE:
				noMeasurements++;
				accumulated = accumulated + numberInQueue;
				SignalList.SendSignal(MEASURE, this, time + 1*slump.nextDouble());
			 break;
		}
	}
}