public class Animation extends Action {

    /**
     * Number of animation repeats. A zero indicates indefinite repeats.
     */
    private int repeatCount;

    /**
     * Returns a new 'Animation' type action.
     * Constructor arguments provide hints to data necessary for a subclass.
     *
     * @param entity The entity that the animation is applied to.
     * @param repeatCount The number of animation repeats. A zero indicates indefinite repeats.
     */
    public Animation(Entity entity, int repeatCount) {
        super(entity);
        this.repeatCount = repeatCount;
    }
    /** Performs 'Animation' specific logic. */
    @Override
    public void execute(EventScheduler scheduler) {
        Animateables entity = (Animateables) getEntity();
        entity.updateImage();

        if (repeatCount != 1) {
            scheduler.scheduleEvent(getEntity(), new Animation(getEntity(), Math.max(this.repeatCount - 1, 0)),
                    entity.getAnimationPeriod());
        }
    }
}
