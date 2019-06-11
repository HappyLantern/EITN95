package task1;
import org.apache.commons.math3.distribution.ExponentialDistribution;

class Gen extends Proc{

	public Sensor[] sendTo; 
	ExponentialDistribution expDist;
	
	
	public Gen(int Ts) {
		expDist = new ExponentialDistribution(Ts);
	}

	public void TreatSignal(Signal x){
		switch (x.signalType){
			case READY:
				for (int i = 0; i < sendTo.length; i++) 
					SignalList.SendSignal(ACTIVE, null, sendTo[i], time + expDist.sample());
				break;
		}
	}
}