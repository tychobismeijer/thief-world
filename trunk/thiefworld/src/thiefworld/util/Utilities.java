package thiefworld.util;

import ec.util.MersenneTwisterFast;
import sim.util.Double2D;

public class Utilities {
	private static MersenneTwisterFast random = new MersenneTwisterFast(
			System.currentTimeMillis());

	public static Double2D getRandomPosition(int maxWidth, int maxHeight) {
		return new Double2D(random.nextInt(maxWidth), random.nextInt(maxHeight));
	}
	
	public static double nextDouble(){
		return random.nextDouble();
	}
	
	public static double nextDouble(double min, double max){
		if(min <= max)
			return random.nextDouble() * (max - min) + min;
		else
			return random.nextDouble() * (min - max) + max;
	}
}
