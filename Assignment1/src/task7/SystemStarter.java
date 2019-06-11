package task7;
import java.util.Random;

// This class defines a simple queuing system with one server. It inherits Proc so that we can use time and the
// signal names without dot notation
class SystemStarter extends Proc {

	private QS[] queues;
	Random slump = new Random();

	public SystemStarter(QS[] queues) {
		this.queues = queues;
	}

	public void TreatSignal(Signal x) {
		switch (x.signalType) {
		case START:
			for (int i = 0; i < queues.length; i++) 
				queues[i].broken = false;
			for (int i = 0; i < queues.length; i++)
				SignalList.SendSignal(BREAK, queues[i], time + (4*slump.nextDouble() + 1));
			break;
		}
	}
}