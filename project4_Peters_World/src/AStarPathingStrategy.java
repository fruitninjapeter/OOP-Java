import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class AStarPathingStrategy implements PathingStrategy {

    /**
     * Return a list containing a single point representing the next step toward a goal
     * If the start is within reach of the goal, the returned list is empty.
     *
     * @param start the point to begin the search from
     * @param end the point to search for a point within reach of
     * @param canPassThrough a function that returns true if the given point is traversable
     * @param withinReach a function that returns true if both points are within reach of each other
     * @param potentialNeighbors a function that returns the neighbors of a given point, as a stream
     */
    public List<Point> computePath(Point start, Point end,
            Predicate<Point> canPassThrough,
            BiPredicate<Point, Point> withinReach,
            Function<Point, Stream<Point>> potentialNeighbors
    ) {
        List<Point> openSet = new ArrayList<>(); // "seen"
        List<Point> closedSet = new ArrayList<>(); // "processed"
        HashMap<Point, Integer> GarryScore = new HashMap<>(); // Points : associated g-score
        HashMap<Point, Integer> FluffyScore = new HashMap<>(); // Points : associated f-score
        HashMap<Point, Point> PrecedingPoints = new HashMap<>(); // Points : Linked Point
        List<Point> finalPath = new ArrayList<>(); // final generated path

        GarryScore.put(start, 0); // Record G value of start node
        FluffyScore.put(start, start.manhattanDistanceTo(end));   // Record F value of start node

        openSet.add(start);   // Add start node to the open set

        while (!openSet.isEmpty()) {    // While open set doesn't equal null:
            // Choose node from open set with best F-value as current node
            Point current = openSet.getFirst();
            for (Point point : openSet) {
                if (FluffyScore.get(point) < FluffyScore.get(current)) {
                    current = point;
                }
            }

            Point finalCurrent = current;   // Move current from open set
            openSet.removeIf(p -> p.equals(finalCurrent));
            closedSet.add(current); // to closed set: indicates it being processed

            if (current.adjacentTo(end)) {  // If current node neighbors end node
                while(!current.equals(start)) { // reconstruct + return path using previous mappings
                    finalPath.add(current);
                    current = PrecedingPoints.get(current); // traverse through previous nodes
                }   // until start node is reached
                return finalPath.reversed();
            }

            List<Point> eyNeighbors = potentialNeighbors.apply(current)
                    .filter(canPassThrough)    // is it traversable?
                    .filter(p -> !closedSet.contains(p))    // is it not in the closed set?
                    .toList();
            for (Point p: eyNeighbors) {
                if (!openSet.contains(p)) { openSet.add(p); }   // If neighbor isn't already in open set, add it here
                int gNeighbor = GarryScore.get(current) + 1; // increment G-value by +1
                // If G-value of neighbor < previously calculated, or first time calculated
                if ((!GarryScore.containsKey(p)) || // update all records to this better version
                        (GarryScore.containsKey(p) && (GarryScore.get(p) > gNeighbor))) {
                    GarryScore.put(p, gNeighbor);   // record G-value of neighbor
                    FluffyScore.put(p, p.manhattanDistanceTo(end) + gNeighbor); // record F-value of neighbor
                    PrecedingPoints.put(p, current);    // record neighbor's preceding point
                }
            }
        }   // Return to while condition
        return List.of();   // If no path was found: return an empty list, or null, or start node, etc.
    }
}
