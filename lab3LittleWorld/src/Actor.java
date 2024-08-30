import java.util.*;
import processing.core.PImage;

/** Represents an actor that exists in the virtual world. */
public interface Actor {

    // Constant save file column positions for properties required by actors.
    int ACTOR_PROPERTY_KEY_INDEX = 0;
    int ACTOR_PROPERTY_POSITION_X_INDEX = 1;
    int ACTOR_PROPERTY_POSITION_Y_INDEX = 2;
    int ACTOR_PROPERTY_UPDATE_PERIOD_INDEX = 3;
    int ACTOR_PROPERTY_COUNT = 4;

    static Optional<Actor> findByKind(World world, Class<?> actorKind) {

        for (int y = 0; y < world.getNumRows(); y++) {
            for (int x = 0; x < world.getNumCols(); x++) {
                Point point = new Point(x, y);

                Optional<Actor> potentialOccupant = world.getOccupant(point);
                if (potentialOccupant.isPresent()) {
                    Actor occupant = potentialOccupant.get();

                    if (actorKind.isInstance(occupant)) {
                        return potentialOccupant;
                    }
                }
            }
        }

        return Optional.empty();
    }

    /**
     * Schedules the next update for the actor.
     */
    void scheduleUpdate(World world, ImageLibrary imageLibrary, EventScheduler eventScheduler);

    /**
     * Calls the actor specific update.
     */
    void executeUpdate(World world, ImageLibrary imageLibrary, EventScheduler eventScheduler);

    // Actor specific logic

    // Getters and Setters

    int getImageIndex();

    List<PImage> getImages();

    Point getPosition();

    void setPosition(Point position);
}
