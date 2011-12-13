package thiefworld.util;

import sim.util.Double2D;
import ec.util.MersenneTwisterFast;

public class Utilities {
	private static MersenneTwisterFast random = new MersenneTwisterFast(
			System.currentTimeMillis());

	public static double theta = 1e-7;

	public static Double2D getRandomPosition(int maxWidth, int maxHeight) {
		return new Double2D(random.nextInt(maxWidth), random.nextInt(maxHeight));
	}

	public static double nextDouble() {
		return random.nextDouble();
	}

	public static double nextDouble(double min, double max) {
		if (min <= max)
			return random.nextDouble() * (max - min) + min;
		else
			return random.nextDouble() * (min - max) + max;
	}

	public static double nextGaussian(double min, double max) {
		if (min <= max)
			return random.nextGaussian() * (max - min) + min;
		else
			return random.nextGaussian() * (min - max) + max;
	}

	public static double nextSigmoid(double t, double min, double max) {
		// suppose the sigmoid takes normal values between [-6, 6]
		// rescale t to fit the interval
		double x = 12 * (t - min) / (max - min) - 6;

		if (min <= max)
			return 1.0 / (1.0 + Math.exp(-x));
		else
			return 1.0 / (1.0 + Math.exp(-x));
	}
}
