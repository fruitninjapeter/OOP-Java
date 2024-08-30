import processing.core.PImage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Mushroom extends Behaveables {

    public static final String MUSHROOM_KEY = "mushroom";
    public static final int MUSHROOM_PARSE_BEHAVIOR_PERIOD_INDEX = 0;
    public static final int MUSHROOM_PARSE_PROPERTY_COUNT = 1;

    /**
     * Constructs an Entity with specified characteristics.
     * In the base program, this is not called directly.
     * Instead, the encapsulated 'create' methods are used to create specific types of entities.
     * @param id             The entity's identifier.
     * @param position       The entity's x/y position in the world.
     * @param images         The entity's inanimate (singular) or animation (multiple) images.
     * @param behaviorPeriod The positive (non-zero) time delay between the entity's behaviors.
     */
    public Mushroom(String id, Point position, List<PImage> images, double behaviorPeriod) {
        super(id, position, images, behaviorPeriod);
    }

    @Override
    public void scheduleActions(EventScheduler scheduler, World world, ImageLibrary imageLibrary) {
        scheduleBehavior(scheduler, world, imageLibrary);
    }

    /** Executes Mushroom specific Logic. */
    @Override
    public void executeBehavior(World world, ImageLibrary imageLibrary, EventScheduler scheduler) {
        List<Point> adjacentPositions = new ArrayList<>(List.of(
                new Point(getPosition().x - 1, getPosition().y),
                new Point(getPosition().x + 1, getPosition().y),
                new Point(getPosition().x, getPosition().y - 1),
                new Point(getPosition().x, getPosition().y + 1)
        ));
        Collections.shuffle(adjacentPositions);

        List<Point> mushroomBackgroundPositions = new ArrayList<>();
        List<Point> mushroomEntityPositions = new ArrayList<>();
        for (Point adjacentPosition : adjacentPositions) {
            if (world.inBounds(adjacentPosition) && !world.isOccupied(adjacentPosition) && world.hasBackground(adjacentPosition)) {
                Background bg = world.getBackgroundCell(adjacentPosition);
                if (bg.getId().equals("grass")) {   // can add || bg.getId().equals("grass_flowers")
                    mushroomBackgroundPositions.add(adjacentPosition);
                } else if (bg.getId().equals("grass_mushrooms")) {
                    mushroomEntityPositions.add(adjacentPosition);
                }
            }
        }

        if (!mushroomBackgroundPositions.isEmpty()) {
            Point backgroundPosition = mushroomBackgroundPositions.get(0);

            Background background = new Background("grass_mushrooms", imageLibrary.get("grass_mushrooms"), 0);
            world.setBackgroundCell(backgroundPosition, background);
        } else if (!mushroomEntityPositions.isEmpty()) {
            Point position = mushroomEntityPositions.get(0);

            Mushroom mushroom = new Mushroom(MUSHROOM_KEY, position, imageLibrary.get(MUSHROOM_KEY), getBehaviorPeriod() * 4.0);

            world.addEntity(mushroom); // cast before or after we add this entity?
            mushroom.scheduleActions(scheduler, world, imageLibrary);
        }

        scheduleBehavior(scheduler, world, imageLibrary);
    }
}
