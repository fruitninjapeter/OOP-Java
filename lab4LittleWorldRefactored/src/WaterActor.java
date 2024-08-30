import processing.core.PImage;

import java.util.List;

abstract class WaterActor extends Actor {

    private int water;

    public WaterActor(Point position, List<PImage> images, double updatePeriod, int water) {
        super(position, images, updatePeriod);
        this.water = water;
    }

    public int getWater() { return water; }

    public void setWater(int water) { this.water = water; }
}