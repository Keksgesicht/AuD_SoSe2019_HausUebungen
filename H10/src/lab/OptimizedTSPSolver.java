package lab;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

import frame.City;

/**
 * @author Emre Berber 2957148
 * @author Christoph Berst 2743394
 * @author Jan Braun 2768531
 * 
 * Aufgabe H1d)-H1e)
 * This is an optimized TSP solver.
 */
public class OptimizedTSPSolver extends AbstractTSPSolver {

	private boolean useLengthPruning;
	private boolean useIntersectionPruning;
	
	/**
	 * Initialize the solver with the list of cities and decide which optimisations to use. 
	 */
	public OptimizedTSPSolver(LinkedList<City> cities, boolean useLengthPruning, boolean useIntersectionPruning) {
		super(cities);
		this.useLengthPruning = useLengthPruning;
		this.useIntersectionPruning = useIntersectionPruning;
	}

	@Override
	protected boolean prune(LinkedList<City> currentList, double currentLength) {
		if(useLengthPruning) {	// Falls aktuelle Länge schon länger die bisher beste Länge 
			double dist = currentLength + distance(currentList.getLast(), currentList.getFirst());
			if(dist >= bestLength) 
				return true;	// dann Rekursion nicht weiterverfolgen
		}
		if(useIntersectionPruning) {	// überprüfen, ob letzte Strecke eine Bisherige kreuzt
			Iterator<City> iL = currentList.descendingIterator();
			City c1 = iL.next(); 		// letztes Element
			if(iL.hasNext()) {
				City c2 = iL.next();	// vorletztes Element
				
				for(City c3 : currentList) {
					if(c1 == c3) continue;
					if(c2 == c3) continue;
					
					for(City c4 : currentList) {
						if(c1 == c4) continue;
						if(c2 == c4) continue;
						if(c3 == c4) continue;
						
						double x1 = c1.x();
						double x2 = c2.x();
						double x3 = c3.x();
						double x4 = c4.x();
						double y1 = c1.y();
						double y2 = c2.y();
						double y3 = c3.y();
						double y4 = c4.y();
						
						double tA = ( (y3 - y4) * (x1 - x3) + (x4 - x3) * (y1 - y3) )
								  / ( (x4 - x3) * (y1 - y2) - (x1 - x2) * (y4 - y3) );
						double tB = ( (y1 - y2) * (x1 - x3) + (x2 - x1) * (y1 - y3) )
								  / ( (x4 - x3) * (y1 - y2) - (x1 - x2) * (y4 - y3) );
						
						// Falls 0 < tA < 1 und 0 < tB < 1
						if(0 < tA && tA < 1 && 0 < tB && tB < 1)
							return true; // dann Rekursion nicht weiterverfolgen
					}
				}
			}			
		}
		return false;
	}

	@Override
	protected void notifyNewBest(LinkedList<City> goodSolution, double length) {
		// useless because our bests are accessible in the package
	}

}
