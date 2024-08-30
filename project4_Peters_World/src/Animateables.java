import processing.core.PImage;

import java.util.List;

abstract class Animateables extends Behaveables {

    // Constant string identifiers for the corresponding type of entity.
    // Used to identify entities in the save file and retrieve image information.

    /** Positive (non-zero) time delay between the entity's animations. */
    private double animationPeriod;

    /**
     * Constructs an Entity with specified characteristics.
     * In the base program, this is not called directly.
     * Instead, the encapsulated 'create' methods are used to create specific types of entities.
     * @param id              The entity's identifier.
     * @param position        The entity's x/y position in the world.
     * @param images          The entity's inanimate (singular) or animation (multiple) images.
     * @param animationPeriod The positive (non-zero) time delay between the entity's animations.
     * @param behaviorPeriod  The positive (non-zero) time delay between the entity's behaviors.
     */

    public Animateables(String id, Point position, List<PImage> images,
                        double animationPeriod, double behaviorPeriod) {
        super(id, position, images, behaviorPeriod);
        this.animationPeriod = animationPeriod;
    }

    /** Called when an animation action occurs. */
    //public abstract void updateImage();
    public void updateImage() {
        setImageIndex(getImageIndex()+1);
    }

    /** Begins all animation updates for the entity. */
    public void scheduleAnimation(EventScheduler scheduler) {
        scheduler.scheduleEvent(this, new Animation(this, 0), animationPeriod);
    }

    public double getAnimationPeriod() {
        return animationPeriod;
    }

    public void setAnimationPeriod(double newPeriod) {
        this.animationPeriod = newPeriod;
    }

    public void scheduleActions(EventScheduler scheduler, World world, ImageLibrary imageLibrary) {
        scheduleAnimation(scheduler);
        scheduleBehavior(scheduler, world, imageLibrary);
    }
}
