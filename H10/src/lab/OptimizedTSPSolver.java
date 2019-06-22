package lab;

/**
 * Aufgabe H1d)-H1e)
 * 
 * Abgabe von: <name>, <name> und <name>
 */

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

import frame.City;

/**
 * This is an optimized TSP solver.
 */
public class OptimizedTSPSolver extends AbstractTSPSolver {

	/**
	 * Initialize the solver with the list of cities and decide which optimisations to use. 
	 */
	public OptimizedTSPSolver(LinkedList<City> cities, boolean useLengthPruning, boolean useIntersectionPruning) {
		super(cities);
	}

	@Override
	protected boolean prune(LinkedList<City> currentList, double currentLength) {
		// TODO
		return false;
	}

	@Override
	protected void notifyNewBest(LinkedList<City> goodSolution, double length) {
		// TODO
	}

}
