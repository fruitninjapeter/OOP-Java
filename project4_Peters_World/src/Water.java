import processing.core.PImage;

import java.util.List;

public class Water extends Animateables {

    public static final String WATER_KEY = "water";
    public static final int WATER_PARSE_PROPERTY_COUNT = 0;

    public static final double WATER_ANIMATION_PERIOD = 2.5;

    public static final double WATER_BEHAVIOR_PERIOD = 0.2; // since water spreads fast

    /**
     * Constructs an Entity with specified characteristics.
     * In the base program, this is not called directly.
     * Instead, the encapsulated 'create' methods are used to create specific types of entities.
     * @param id       The entity's identifier.
     * @param position The entity's x/y position in the world.
     * @param images   The entity's inanimate (singular) or animation (multiple) images.
     */
    public Water(String id, Point position, List<PImage> images) {
        super(id, position, images, WATER_ANIMATION_PERIOD, WATER_BEHAVIOR_PERIOD);
    }

    @Override
    public void executeBehavior(World world, ImageLibrary imageLibrary, EventScheduler scheduler) {
        scheduleBehavior(scheduler, world, imageLibrary);
    }
}
