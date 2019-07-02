package lab;

import java.util.LinkedList;
import java.util.ListIterator;

import frame.City;

/**
 * @author Emre Berber 2957148
 * @author Christoph Berst 2743394
 * @author Jan Braun 2768531
 * 
 * In this class, you should implement the backtracking algorithm for solving the TSP.
 * This is an abstract class - it can't be used directly, but classes that inherit from
 * this class and implement all the abstract methods can be used. BasicTSPSolver is such
 * a class.
 */
public abstract class AbstractTSPSolver {
	
	private LinkedList<City> _cities;
	private LinkedList<City> _solution;
	private double _length;
	private double[][] _distanceMap;
	private int numberOfCities;
	LinkedList<City> currentBest; 
	double bestLength;
	
	/**
	 * Create a solver on the given set of cities.
	 */
	public AbstractTSPSolver(LinkedList<City> cities) {
		_cities = cities;
		_solution = null;
		_length = -1;
		_distanceMap = new double[cities.size()][cities.size()];
		numberOfCities = _cities.size();
	}
	
	/**
	 * Return the cities this TSP is defined on.
	 */
	public LinkedList<City> cities() {
		return _cities;
	}
	
	/**
	 * Return the optimal roundtrip as a list of cities.
	 */
	public LinkedList<City> solution() {
		return _solution;
	}
	
	/**
	 * Return the length of the optimal solution.
	 */
	public double length() {
		return _length;
	}
	
	/**
	 * Pre-calculate a map for all distances between cities.
	 */
	public void buildDistanceMap() {
		for(City c1 : _cities) {	// iteriert über alle Städte
			int cID = c1.id();
			for(int i=cID; i<numberOfCities; i++) {	// iteriert über die Verbleibenden um Dopplungen zu vermeiden
				City c2 = _cities.get(i);
				double dist = Math.hypot(c2.x() - c1.x(), c2.y() - c1.y());	// 2-Norm (Euklid)
				_distanceMap[cID][i] = dist;	// Distanz sowohl zwischen c1 und c2
				_distanceMap[i][cID] = dist;	// als auch zwischen c2 und c1 speichern
			}
		}
	}
	
	/**
	 * Return the distance between City a and City b.
	 */
	public double distance(City a, City b) {
		return _distanceMap[a.id()][b.id()];
	}
	
	/**
	 * Solve the TSP.
	 */
	public void solve() {
		currentBest = new LinkedList<City>();
		bestLength = Double.MAX_VALUE;
		LinkedList<City> currentList = new LinkedList<City>();
		double currentLength = 0;
		
		currentList.add(_cities.get(0));
		_solution = backtracker(currentList, currentLength);
	}
	
	private LinkedList<City> backtracker(LinkedList<City> currentList, double currentLength){
		if(!prune(currentList, currentLength)) {
			if(numberOfCities == currentList.size()) {
				double dist = distance(currentList.getLast(), currentList.getFirst());
				currentLength += dist;
				if(currentLength < bestLength) {
					currentBest = (LinkedList<City>) currentList.clone();
					_length = bestLength = currentLength;
					notifyNewBest(currentBest, bestLength);
				}
				currentLength -= dist;
			}
			else {
				for(int i = 1; i < numberOfCities; i++) {
					City city17 = _cities.get(i);
					if(!currentList.contains(city17)){
						double dist = distance(currentList.getLast(), city17);
						currentLength += dist; 
						currentList.add(city17);
						currentBest = backtracker(currentList, currentLength);
						currentList.remove(city17);
						currentLength -= dist; 
					}
				}
			}
		}
		
		return currentBest;
	}
	
	/**
	 * This should be called for every recursion step in the solver. DO NOT IMPLEMENT THIS
	 * METHOD HERE!
	 * @param currentList the current list of cities that should become the roundtrip 
	 * @param currentLength the distance needed to travel through all the cities in the given order
	 * @return true if this direction of the backtracking should be stopped; false, if it should continue
	 */
	protected abstract boolean prune(LinkedList<City> currentList, double currentLength);
	
	/**
	 * Always call this method if your solver found a solution that is better than all previous
	 * solutions. DO NOT IMPLEMENT THIS METHOD HERE!
	 * @param goodSolution The roundtrip (should start at ID 0 and contain every city exactly once).
	 * @param length The length of the roundtrip.
	 */
	protected abstract void notifyNewBest(LinkedList<City> goodSolution, double length);
}
