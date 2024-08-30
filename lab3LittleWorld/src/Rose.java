import processing.core.PImage;

import java.util.List;

public class Rose implements Actor, Waterable {

    public static final String ROSE_KEY = "rose";

    public static final double ROSE_TO_NYMPH_UPDATE_PERIOD = 0.125;

    public static final int ROSE_WATER_LIMIT = 9;

    private Point position;

    private List<PImage> images;

    private int imageIndex;

    private double updatePeriod;

    private int water;

    public Rose(Point position, List<PImage> images, double updatePeriod) {
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.updatePeriod = updatePeriod;
        this.water = 0; // Water always starts at 0
    }

    @Override
    public void executeUpdate(World world, ImageLibrary imageLibrary, EventScheduler eventScheduler) {
        imageIndex = images.size() * water / ROSE_WATER_LIMIT;
        if (water >= ROSE_WATER_LIMIT) {
            Actor nymph = new Nymph(position, imageLibrary.get(Nymph.NYMPH_KEY), ROSE_TO_NYMPH_UPDATE_PERIOD);

            world.removeEntity(eventScheduler, this);

            world.addEntity(nymph);
            nymph.scheduleUpdate(world, imageLibrary, eventScheduler);
        } else {
            scheduleUpdate(world, imageLibrary, eventScheduler);
        }
    }

    @Override
    public void scheduleUpdate(World world, ImageLibrary imageLibrary, EventScheduler eventScheduler) {
        eventScheduler.scheduleEvent(this, new Update(this, world, imageLibrary), updatePeriod);
    }

    @Override
    public int getImageIndex() { return imageIndex; }

    @Override
    public List<PImage> getImages() { return images; }

    @Override
    public Point getPosition() { return position; }

    @Override
    public void setPosition(Point position) { this.position = position; }

    @Override
    public int getWater() { return water; }

    @Override
    public void setWater(int water) { this.water = water; }

}