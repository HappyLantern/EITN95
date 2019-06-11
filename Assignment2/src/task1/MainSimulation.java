package task1;

import java.io.*;
import java.awt.geom.Point2D;

public class MainSimulation extends Global {

	public static void main(String[] args) throws IOException {

		File folder = new File("config");
		File outputfile = new File("output.txt");
		BufferedWriter alloutputWriter = new BufferedWriter(new FileWriter(outputfile));

		for (final File file : folder.listFiles()) {

			Signal actSignal;
			new SignalList();

			// Read input and create sensors
			BufferedReader reader = new BufferedReader(new FileReader(file));

			String[] parameters = new String[4];
			for (int i = 0; i < parameters.length; i++)
				parameters[i] = reader.readLine();

			int N = Integer.parseInt(parameters[0].substring(2));
			int R = Integer.parseInt(parameters[1].substring(2));
			int Tp = Integer.parseInt(parameters[2].substring(3));
			int Ts = Integer.parseInt(parameters[3].substring(3));

			Gateway gateway = new Gateway(R, Ts);
			Sensor[] sensors = new Sensor[N];
			
			int s = 0;
			String coordinate = null;
			while ((coordinate = reader.readLine()) != null) {
				String coordinates[] = coordinate.split(", ");
				sensors[s] = new Sensor();
				sensors[s].sendTo = gateway;
				sensors[s].x = Integer.parseInt(coordinates[0]);
				sensors[s].y = Integer.parseInt(coordinates[1]);
				s++;
			}
			reader.close();

			// Add all sensors that are within range of each sensor.
			// I guess better solution is to keep all sensors that are active in a list and check which are in range when adding a new one.
			for (int i = 0; i < sensors.length; i++) {
				int x1 = sensors[i].x;
				int y1 = sensors[i].y;
				for (int k = 0; k < sensors.length; k++) {
					int x2 = sensors[k].x;
					int y2 = sensors[k].y;
					double distance = Point2D.distance(x1, y1, x2, y2);
					if (i != k && distance < R)
						sensors[i].withinRange.add(sensors[k]);
				}
			}

			// Start the simulation
			Gen Generator = new Gen(Ts);
			Generator.sendTo = sensors;

			SignalList.SendSignal(READY, null, Generator, time);
			System.out.println("---------------------------------------------------------");
			System.out.println("Starting simulation for file: " + file.getName());
			while (gateway.numberReceived < 100000) {
				actSignal = SignalList.FetchSignal();
				time = actSignal.arrivalTime;
				actSignal.destination.TreatSignal(actSignal);
			}

			// Write results to file
		
			int blocked = gateway.numberAttemptSend - gateway.numberReceived;
			int load = gateway.numberAttemptSend;
			int received = gateway.numberReceived;

			double lambda = load / (1.0 * time);
			double Tput = received / (1.0 * load);
			
			double t_Tput = (N / (1.0 * Ts)) * Tp * Math.exp(-2 * (N / (1.0 * Ts)) * Tp);
			
			File f = new File("output/" + file.getName());
			f.getParentFile().mkdirs();
			BufferedWriter writer = new BufferedWriter(new FileWriter(f));
			
			writer.append("Time: " + Double.toString(time) + "\n");
			writer.append("Lost: " + Integer.toString(blocked) + "\n");
			writer.append("Received: " + Integer.toString(received) + "\n");
			writer.append("Load: " + Integer.toString(load) + "\n");
			writer.append("Lambda: " + Double.toString(lambda) + "\n");
			writer.append("Tput: " + Double.toString(Tput) + "\n");
			writer.append("Theoretical Tput: " + Double.toString(t_Tput));
			writer.close();
			
			alloutputWriter.append("N: " + Double.toString(N) + " ");
			alloutputWriter.append("Time: " + Double.toString(time) + " ");
			alloutputWriter.append("Lost: " + Integer.toString(blocked) + " ");
			alloutputWriter.append("Received: " + Integer.toString(received) + " ");
			alloutputWriter.append("Load: " + Integer.toString(load) + " ");
			alloutputWriter.append("Lambda: " + Double.toString(lambda) + " ");
			alloutputWriter.append("Tput: " + Double.toString(Tput) + " ");
			alloutputWriter.append("Theoretical Tput: " + Double.toString(t_Tput) + "\n");
			
			
			// Print results
		
			
			System.out.println("LambdaEff: " + received / (1.0 * time));
			System.out.println("Time: " + time);
			System.out.println("Lost: " + blocked);
			System.out.println("Received: " + received);
			System.out.println("Load: " + load);
			System.out.println("Tput = " + Tput);
			System.out.println("Lambda: " + lambda);
			System.out.println("Theoretical Tput: " + t_Tput);

			// reset för next simulation
			time = 0;
		}
		alloutputWriter.close();
		// configuring everything by default
	

		// 0.1516

	}
}