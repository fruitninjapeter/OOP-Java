import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Lava extends Animateables {

    public static final String LAVA_KEY = "lava";

    public static final int LAVA_PARSE_PROPERTY_COUNT = 0;

    public static final double LAVA_ANIMATION_PERIOD = 3.5;

    public static final double LAVA_BEHAVIOR_PERIOD = 1.0; // since water spreads fast

    private int spreads; // amount of times lava can spread to adjacent tiles

    private int lifetime;

    public Lava(String id, Point position, List<PImage> images, int spreads, int lifetime) {
        super(id, position, images, LAVA_ANIMATION_PERIOD, LAVA_BEHAVIOR_PERIOD);
        this.spreads = spreads;
        this.lifetime = lifetime;
    }

    @Override
    public void executeBehavior(World world, ImageLibrary imageLibrary, EventScheduler scheduler) {
        if (transform(world, scheduler, imageLibrary)) {
            scheduleBehavior(scheduler, world, imageLibrary);
        }
    }

    //Background obsidian = new Background("Obsidian from Water + Lava", imageLibrary.get("obsidian"), 0);
    //world.setBackgroundCell(pressed, obsidian);

    public boolean transform(World world, EventScheduler scheduler, ImageLibrary imageLibrary) {
        if (lifetime == 12) {
            lavaSpread(world, scheduler, imageLibrary);
            spreads -= 1;
        } else if (lifetime == 8) {
            lavaSpread(world, scheduler, imageLibrary);
            spreads -= 1;
        } else if (lifetime == 4) {
            lavaSpread(world, scheduler, imageLibrary);
            spreads -= 1;
        }
        if (!makeObsidian(world, imageLibrary)) {
            Background newObsidian = new Background("Obsidian from Water + Lava",
                    imageLibrary.get("obsidian"), 0);
            world.setBackgroundCell(getPosition(), newObsidian);
            world.removeEntity(scheduler, this);
            return false;
        }
        if (lifetime <= 0) {
            world.removeEntity(scheduler, this);
            return false;
        }
        lifetime -= 1;
        return true;
    }

    public void lavaSpread(World world, EventScheduler scheduler, ImageLibrary imageLibrary) {
        List<Point> flammables;
        flammables = Fire.flammable(world, getPosition());

        if (!flammables.isEmpty()) {
            int randNum = (int) (Math.random() * flammables.size());
            Point whereToBurn = flammables.get(randNum);
            Fire.whatBurntBackground(world, imageLibrary, whereToBurn);

            Fire newflame = new Fire(getID() + "it burns! Hot HOt hot!", whereToBurn,
                    imageLibrary.get(Fire.FIRE_KEY), getBehaviorPeriod(),
                    getAnimationPeriod(), spreads);
            world.addEntity(newflame);
            newflame.scheduleActions(scheduler, world, imageLibrary);
        }

        flammables = flammables.stream().
                filter(p -> !world.isOccupied(p)).toList();

        if (!flammables.isEmpty()) {
            int randNum = (int) (Math.random() * flammables.size());
            Point whereToLava = flammables.get(randNum);
            Fire.whatBurntBackground(world, imageLibrary, whereToLava);

            Lava newLava = new Lava(getID() + "it singes! Hot! Hot! Juicy!", whereToLava,
                    imageLibrary.get(Lava.LAVA_KEY),
                    spreads-1, lifetime-4);
            world.addEntity(newLava);
            newLava.scheduleActions(scheduler, world, imageLibrary);
        }
    }

    public boolean makeObsidian(World world, ImageLibrary imageLibrary) {
        Stream<Point> nearPoints = PathingStrategy.CARDINAL_NEIGHBORS.apply(getPosition());

        List<Point> nearEntities = nearPoints
                .filter(p -> world.inBounds(p) && world.isOccupied(p) && world.hasBackground(p)).toList();

        for (Point pew : nearEntities) {
            if (world.getOccupant(pew).get() instanceof Water) {
                world.removeEntityAt(pew);
                Background newObsidian = new Background("Obsidian from Water + Lava",
                        imageLibrary.get("obsidian"), 0);
                world.setBackgroundCell(pew, newObsidian);
                return false;
            }
        }
        return true;
    }
}
