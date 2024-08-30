import processing.core.PImage;

import java.util.List;

public class Well implements Actor {

    public static final String WELL_KEY = "well";

    private Point position;

    private List<PImage> images;

    private int imageIndex;

    private double updatePeriod;

    public Well(Point position, List<PImage> images, double updatePeriod) {
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.updatePeriod = updatePeriod;
    }

    @Override
    public void executeUpdate(World world, ImageLibrary imageLibrary, EventScheduler eventScheduler) {
        imageIndex += 1;
        scheduleUpdate(world, imageLibrary, eventScheduler);
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

}