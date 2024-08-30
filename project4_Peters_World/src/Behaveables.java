import processing.core.PImage;

import java.util.List;

abstract class Behaveables extends Entity {

    /** Positive (non-zero) time delay between the entity's behaviors. */
    private double behaviorPeriod;

    /**
     * Constructs an Entity with specified characteristics.
     * In the base program, this is not called directly.
     * Instead, the encapsulated 'create' methods are used to create specific types of entities.
     * @param id              The entity's identifier.
     * @param position        The entity's x/y position in the world.
     * @param images          The entity's inanimate (singular) or animation (multiple) images.
     * @param behaviorPeriod  The positive (non-zero) time delay between the entity's behaviors.
     */

    public Behaveables(String id, Point position, List<PImage> images, double behaviorPeriod) {
        super(id, position, images);
        this.behaviorPeriod = behaviorPeriod;
    }

    /** Called to begin animation and/or behavior for an entity. */
    public abstract void scheduleActions(EventScheduler scheduler, World world, ImageLibrary imageLibrary);

    /** Schedules a single behavior update for the entity. */
    public void scheduleBehavior(EventScheduler scheduler, World world, ImageLibrary imageLibrary) {
        scheduler.scheduleEvent(this, new Behavior(this, world, imageLibrary), behaviorPeriod);
    }

    /** Performs the entity's behavior logic. */
    public abstract void executeBehavior(World world, ImageLibrary imageLibrary, EventScheduler scheduler);

    public double getBehaviorPeriod() { return behaviorPeriod; }

    public void setBehaviorPeriod(double newPeriod) {
        this.behaviorPeriod = newPeriod;
    }
}
