package task1;

import java.util.ArrayList;

import org.apache.commons.math3.distribution.ExponentialDistribution;

import java.awt.geom.Point2D;


class Gateway extends Proc {
	public int numberReceived = 0;
	public int numberAttemptSend = 0;
	public ArrayList<Sensor> transmitting = new ArrayList<Sensor>();
	ExponentialDistribution expDist;

	public int R;
	
	public Gateway(int R, int Ts) {
		this.R = R;
		expDist = new ExponentialDistribution(Ts);
	}


	public void TreatSignal(Signal x) {
		Sensor s = (Sensor) x.from;
		switch (x.signalType) {

		case TRANSMISSION:
			
			numberAttemptSend++;
			boolean ok = true;
			for (int i = 0; i < transmitting.size(); i++) {
				double distance = Point2D.distance(s.x, s.y, transmitting.get(i).x, transmitting.get(i).y);
				if (distance < R) {
					SignalList.SendSignal(ACTIVE, null, transmitting.remove(i), time + expDist.sample());
					ok = false;
				}
			}
			
			if (ok) {
				transmitting.add(s);
				SignalList.SendSignal(TRANSMITTED, s, this, time + 1 );
			} else {
				SignalList.SendSignal(ACTIVE, null, s, time + expDist.sample());
			}
			break;
			
		case TRANSMITTED:
			
			if (transmitting.remove(s)) {
				numberReceived++;
				SignalList.SendSignal(ACTIVE, null, s, time + expDist.sample());
			}
			break;
		}
	}
}