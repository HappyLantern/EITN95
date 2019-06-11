package task7;
import java.util.*;
import java.io.*;

// This class defines a simple queuing system with one server. It inherits Proc so that we can use time and the
// signal names without dot notation
class QS extends Proc{
	public Proc sendTo[];
	public boolean broken = false;
	Random slump = new Random();
	

	public void TreatSignal(Signal x){
		switch (x.signalType){
			case BREAK:
				broken = true;
				if (sendTo != null) {
					for (int i = 0; i < sendTo.length; i++) {
						SignalList.SendSignal(BREAK, sendTo[i], time);
					}
				}				
				break;
		}
	}
}