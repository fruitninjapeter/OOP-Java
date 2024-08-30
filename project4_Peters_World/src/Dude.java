import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Dude extends Animateables implements Moveables, Transformables {

    public static final String DUDE_KEY = "dude";
    // Constant save file column positions for properties corresponding to specific entity types.
    // Do not use these values directly in any constructors or 'create' methods.
    public static final int DUDE_PARSE_PROPERTY_ANIMATION_PERIOD_INDEX = 0;
    public static final int DUDE_PARSE_PROPERTY_BEHAVIOR_PERIOD_INDEX = 1;
    public static final int DUDE_PARSE_PROPERTY_RESOURCE_LIMIT_INDEX = 2;
    public static final int DUDE_PARSE_PROPERTY_COUNT = 3;
    /** Number of resources collected by the entity. */
    private int resourceCount;
    /** Total number of resources the entity may hold. */
    private int resourceLimit;

    public Dude(String id, Point position, List<PImage> images,
                double animationPeriod, double behaviorPeriod, int resourceCount, int resourceLimit) {
        super(id, position, images, animationPeriod, behaviorPeriod);
        this.resourceCount = resourceCount;
        this.resourceLimit = resourceLimit;
    }

    /** Executes Dude specific Logic. */
    @Override
    public void executeBehavior(World world, ImageLibrary imageLibrary, EventScheduler scheduler) {
        Optional<Entity> dudeTarget = findDudeTarget(world);
        if (dudeTarget.isEmpty() || !moveTo(world, dudeTarget.get(), scheduler) || transform(world, scheduler, imageLibrary)) {
            scheduleBehavior(scheduler, world, imageLibrary);
        }
    }

    /** Returns the (optional) entity a Dude will path toward. */
    public Optional<Entity> findDudeTarget(World world) {
        List<Class<?>> potentialTargets;

        if (resourceCount == resourceLimit) {
            potentialTargets = List.of(House.class);
        } else {
            potentialTargets = List.of(Tree.class, Sapling.class);
        }

        return world.findNearest(getPosition(), potentialTargets);
    }

    /** Attempts to move the Dude toward a target, returning True if already adjacent to it. */
    @Override
    public boolean moveTo(World world, Entity target, EventScheduler scheduler) {
        if (getPosition().adjacentTo(target.getPosition())) {
            if (target instanceof SacrificialWood) {
                ((SacrificialWood) target).setHealth(((SacrificialWood) target).getHealth() - 1);
            }
            return true;
        } else {
            Point nextPos = nextPosition(world, target.getPosition());

            if (!getPosition().equals(nextPos)) { // If this next position calculated isn't same position
                world.moveEntity(scheduler, this, nextPos);
            }

            return false;
        }
    }

    /** Determines a Dude's next position when moving. */
    @Override
    public Point nextPosition(World world, Point destination) {
        PathingStrategy doYouKnowDaWae = new AStarPathingStrategy();

        Predicate<Point> traversable = p -> world.inBounds(p) &&
                !(world.isOccupied(p) && !(world.getOccupant(p).get() instanceof Stump));

        BiPredicate<Point, Point> reachable = Point::adjacentTo;

        Function<Point, Stream<Point>> neighbors = PathingStrategy.CARDINAL_NEIGHBORS;

        List<Point> daWae = doYouKnowDaWae.computePath(getPosition(), destination,
                traversable, reachable, neighbors);

        if (!daWae.isEmpty()) {
            return daWae.getFirst();
        }

        return getPosition();
    }

    /** Changes the Dude's graphics. */
    @Override
    public boolean transform(World world, EventScheduler scheduler, ImageLibrary imageLibrary) {
        if (resourceCount < resourceLimit) {
            resourceCount += 1;
            if (resourceCount == resourceLimit) {
                Dude dude = new Dude(getID(), getPosition(), imageLibrary.get(DUDE_KEY + "_carry"), getAnimationPeriod(), getBehaviorPeriod(), resourceCount, resourceLimit);

                world.removeEntity(scheduler, this);

                world.addEntity(dude);
                dude.scheduleActions(scheduler, world, imageLibrary);

                return false;
            }
        } else {
            Dude dude = new Dude(getID(), getPosition(), imageLibrary.get(DUDE_KEY), getAnimationPeriod(), getBehaviorPeriod(), 0, resourceLimit);

            Dude dood = dude;
            world.removeEntity(scheduler, this);

            world.addEntity(dude);
            dude.scheduleActions(scheduler, world, imageLibrary);

            return false;
        }

        return true;
    }
}
