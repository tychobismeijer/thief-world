package thiefworld.util;

import ec.util.MersenneTwisterFast;
import sim.util.Double2D;

/**
 * Miscellaneous math utilities used throughout the project.
 * 
 * @author Stefan Adrian Boronea
 * 
 */
public class Utilities {
	/**
	 * Smallest allowed distance between two points.
	 */
	public static double theta = 1e-7;

	/**
	 * Random number generator.
	 * 
	 * @see <a
	 *      href="http://cs.gmu.edu/~sean/research/mersenne/ec/util/MersenneTwisterFast.html">Sean
	 *      Luke Mersenne Twister algorithm implementation.</a>
	 */
	private static MersenneTwisterFast random = new MersenneTwisterFast(
			System.currentTimeMillis());

	/**
	 * Chooses a random 2D point between the specified coordinates.
	 * 
	 * @param maxWidth
	 *            the maximum x coordinate.
	 * @param maxHeight
	 *            the maximum y coordinate.
	 * @return the random 2D point.
	 */
	public static Double2D getRandomPosition(int maxWidth, int maxHeight) {
		return new Double2D(random.nextInt(maxWidth), random.nextInt(maxHeight));
	}

	/**
	 * Random double value generator.
	 * 
	 * @return a random double value.
	 */
	public static double nextDouble() {
		return random.nextDouble();
	}

	/**
	 * Random double value generator between two specified values.
	 * 
	 * @param min
	 *            minimum value.
	 * @param max
	 *            maximum value.
	 * @return a random double value between [min, max].
	 */
	public static double nextDouble(double min, double max) {
		if (min <= max)
			return random.nextDouble() * (max - min) + min;
		else
			return random.nextDouble() * (min - max) + max;
	}

	/**
	 * Random double value generator within a Gaussian distribution between two
	 * specified values.
	 * 
	 * @param min
	 *            minimum value.
	 * @param max
	 *            maximum value
	 * @return a random double value between [min, max].
	 */
	public static double nextGaussian(double min, double max) {
		if (min <= max)
			return random.nextGaussian() * (max - min) + min;
		else
			return random.nextGaussian() * (min - max) + max;
	}

	/**
	 * Sigmoid function calculator S(t) for a specified value t.
	 * 
	 * @param t
	 *            the value for which the sigmoid is computed.
	 * @param min
	 *            the minimum value of S(t).
	 * @param max
	 *            the maximum value of S(t).
	 * @return the value of S(t).
	 * @see <a href="http://en.wikipedia.org/wiki/Sigmoid_function">Sigmoid
	 *      function</a>
	 */
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
