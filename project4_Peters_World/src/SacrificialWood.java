import processing.core.PImage;

import java.util.List;

abstract class SacrificialWood extends Animateables {

    /** Entity's current health level. */
    private int health;

    public SacrificialWood(String id, Point position, List<PImage> images,
                           double animationPeriod, double behaviorPeriod, int health) {
        super(id, position, images, animationPeriod, behaviorPeriod);
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

}
