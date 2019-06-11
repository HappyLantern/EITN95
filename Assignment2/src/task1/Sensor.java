package task1;

import java.util.ArrayList;
import java.util.Random;
import org.apache.commons.math3.distribution.ExponentialDistribution;


// This class defines a simple queuing system with one server. It inherits Proc so that we can use time and the
// signal names without dot notation
class Sensor extends Proc {
	
	public ArrayList<Sensor> withinRange = new ArrayList<Sensor>();
	public int x;
	public int y;
	public Proc sendTo;
	
	Random slump = new Random();

	public void TreatSignal(Signal x) {
		switch (x.signalType) {
		case ACTIVE:
			SignalList.SendSignal(TRANSMISSION, this, sendTo, time);
			break;
		}
	}
}