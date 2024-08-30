public interface Moveables {
    boolean moveTo(World world, Entity entity, EventScheduler eventScheduler);
    Point nextPosition(World world, Point destination);
}