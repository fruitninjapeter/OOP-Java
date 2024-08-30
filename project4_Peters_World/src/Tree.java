import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Tree extends SacrificialWood implements Transformables {
    public static final String TREE_KEY = "tree";
    public static final int TREE_PARSE_PROPERTY_ANIMATION_PERIOD_INDEX = 0;
    public static final int TREE_PARSE_PROPERTY_BEHAVIOR_PERIOD_INDEX = 1;
    public static final int TREE_PARSE_PROPERTY_HEALTH_INDEX = 2;
    public static final int TREE_PARSE_PROPERTY_COUNT = 3;
    public static final double TREE_RANDOM_ANIMATION_PERIOD_MIN = 0.1;
    public static final double TREE_RANDOM_ANIMATION_PERIOD_MAX = 1.0;
    public static final double TREE_RANDOM_BEHAVIOR_PERIOD_MIN = 0.01;
    public static final double TREE_RANDOM_BEHAVIOR_PERIOD_MAX = 0.10;
    public static final int TREE_RANDOM_HEALTH_MIN = 1;
    public static final int TREE_RANDOM_HEALTH_MAX = 3;

    /**
     * Creates an Entity representing a(n) 'Tree'.
     * The parameters provide a hint to data relevant for a 'Tree' class.
     *
     * @param id              A unique string identifier for the entity.
     *                        Typically, the 'Tree' key, but can be anything.
     * @param position        The entity's x/y position in the world.
     * @param images          A list of images that represent an entity and its possible animation.
     * @param animationPeriod The time between when an entity's animation is scheduled and executed.
     * @param behaviorPeriod  The time between when an entity's activity is scheduled and executed.
     */
    public Tree(String id, Point position, List<PImage> images, double animationPeriod, double behaviorPeriod, int health) {
        super(id, position, images, animationPeriod, behaviorPeriod, health);
        setBurnable(true);
    }

    /** Executes Tree specific Logic. */
    @Override
    public void executeBehavior(World world, ImageLibrary imageLibrary, EventScheduler scheduler) {
        if (transform(world, scheduler, imageLibrary)) {
            scheduleBehavior(scheduler, world, imageLibrary);
        }
    }

    /** Checks the Tree's health and transforms accordingly, returning true if successful. */
    @Override
    public boolean transform(World world, EventScheduler scheduler, ImageLibrary imageLibrary) {
        if (getHealth() <= 0) {
            Entity stump = new Stump(Stump.STUMP_KEY + "_" + getID(), getPosition(), imageLibrary.get(Stump.STUMP_KEY));

            world.removeEntity(scheduler, this);

            world.addEntity(stump);

            return false;
        }
        //List<Point> nearPoints = PathingStrategy.CARDINAL_NEIGHBORS.apply(getPosition()).toList();

        //for (Point treeWat : nearPoints) {
        //    if (world.getOccupant(treeWat).get() instanceof Fire) {
        //        Tree treeBurn = new Tree(getID()+ "is burning!", getPosition(), imageLibrary.get("tree_burnt"),
        //                getAnimationPeriod(),getBehaviorPeriod(),getHealth());

        //        world.removeEntity(scheduler, this);

        //        world.addEntity(treeBurn);
        //        return false;
            //}
        //}
        return true;
    }
}
