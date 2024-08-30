import processing.core.PImage;

import java.util.List;

public class Sapling extends SacrificialWood implements Transformables {
    public static final String SAPLING_KEY = "sapling";
    public static final int SAPLING_PARSE_PROPERTY_COUNT = 0;
    // Constant limits and default values for specific entity types.
    public static final int SAPLING_HEALTH_LIMIT = 5;
    public static final double SAPLING_ANIMATION_PERIOD = 0.0125; // Very small to react to health changes
    public static final double SAPLING_BEHAVIOR_PERIOD = 2.0;

    /**
     * Creates an Entity representing a(n) 'Sapling'.
     * The parameters provide a hint to data relevant for a 'Sapling' class.
     *
     * @param id A unique string identifier for the entity.
     *           Typically, the constant 'Sapling' key, but can be anything.
     * @param position The entity's x/y position in the world.
     * @param images A list of images that represent an entity and its possible animation.
     */
    public Sapling(String id, Point position, List<PImage> images) {
        super(id, position, images, SAPLING_ANIMATION_PERIOD, SAPLING_BEHAVIOR_PERIOD, 0);
    }

    /** Called when an animation action occurs. */
    @Override
    public void updateImage() {
        if (getHealth() <= 0) {
            setImageIndex(0);
        } else if (getHealth() < Sapling.SAPLING_HEALTH_LIMIT) {
            setImageIndex(getImages().size() * getHealth() / Sapling.SAPLING_HEALTH_LIMIT);
        } else {
            setImageIndex(getImages().size() - 1);
        }
    }

    /** Executes Sapling specific Logic. */
    @Override
    public void executeBehavior(World world, ImageLibrary imageLibrary, EventScheduler scheduler) {
        setHealth(getHealth()+1);
        if (transform(world, scheduler, imageLibrary)) {
            scheduleBehavior(scheduler, world, imageLibrary);
        }
    }

    /** Checks the Sapling's health and transforms accordingly, returning true if successful. */
    @Override
    public boolean transform(World world, EventScheduler scheduler, ImageLibrary imageLibrary) {
        if (getHealth() <= 0) {
            Entity stump = new Stump(Stump.STUMP_KEY + "_" + getID(), getPosition(), imageLibrary.get(Stump.STUMP_KEY));

            world.removeEntity(scheduler, this);

            world.addEntity(stump);

            return false;
        } else if (getHealth() >= Sapling.SAPLING_HEALTH_LIMIT) {
            Tree tree = new Tree(
                    Tree.TREE_KEY + "_" + getID(),
                    getPosition(),
                    imageLibrary.get(Tree.TREE_KEY),
                    NumberUtil.getRandomDouble(Tree.TREE_RANDOM_ANIMATION_PERIOD_MIN, Tree.TREE_RANDOM_ANIMATION_PERIOD_MAX), NumberUtil.getRandomDouble(Tree.TREE_RANDOM_BEHAVIOR_PERIOD_MIN, Tree.TREE_RANDOM_BEHAVIOR_PERIOD_MAX),
                    NumberUtil.getRandomInt(Tree.TREE_RANDOM_HEALTH_MIN, Tree.TREE_RANDOM_HEALTH_MAX)
            );

            world.removeEntity(scheduler, this);

            world.addEntity(tree);
            tree.scheduleActions(scheduler, world, imageLibrary);

            return false;
        }

        return true;
    }
}
