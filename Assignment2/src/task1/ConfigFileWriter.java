package task1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ConfigFileWriter {

	public static void main(String[] args) throws IOException {
		
		int CITY_LENGTH = 10000; // Meters

		String N = "";
		String R = "";
		String Tp = "";
		String Ts = "";

		Scanner in = new Scanner(System.in);
		System.out.println("Enter the wanted name of the configfile");
		String filename = in.nextLine();
		File file = new File("config/" + filename);
		file.getParentFile().mkdirs();
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		System.out.println("Press \"Enter\" if you have entered the right parameters in code.");
		System.out.println("Otherwise, enter the parameters on a single line as \"N, R, Tp, Ts\"");

		String parameters = in.nextLine();
		in.close();
		if (parameters.equals("")) {
			N = "1000";
			R = "7000";
			Tp = "1";
			Ts = "4000";
		} else {
			String[] parts = parameters.split(", |,");
			if (parts.length != 4) {
				writer.close();
				throw new IOException();
			}
			N  = parts[0];
			R  = parts[1];
			Tp = parts[2];
			Ts = parts[3];
		}
		
		writer.append("N " + N + "\n");
		writer.append("R " + R + "\n");
		writer.append("Tp " + Tp + "\n");
		writer.append("Ts " + Ts + "\n");
		
		// Now fill coordinates for sensors.
		Random rand = new Random();
		ArrayList<String> list = new ArrayList<String>(); // Checking for same coordinates, not very efficient.
		
		for (int i = 0; i < Integer.parseInt(N); i++) {
			String x = Integer.toString(rand.nextInt(CITY_LENGTH));
			String y = Integer.toString(rand.nextInt(CITY_LENGTH));
			String coordinate = x + ", " + y;
			while (list.contains(coordinate)) {
				x = Integer.toString(rand.nextInt(CITY_LENGTH));
				y = Integer.toString(rand.nextInt(CITY_LENGTH));
				coordinate = x + ", " + y;
			}
			list.add(coordinate);
			writer.append(coordinate + "\n");
		}
		
		writer.close();
		System.out.println("Config file finished!");
	}
}
