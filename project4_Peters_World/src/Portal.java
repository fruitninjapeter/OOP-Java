import processing.core.PImage;

import java.util.List;
import java.util.Random;

public class Portal extends Animateables implements Transformables {

    public static final String PORTAL_KEY = "portal";
    public static final int PORTAL_PARSE_PROPERTY_COUNT = 0;
    // Constant limits and default values for specific entity types.
    public static final double PORTAL_ANIMATION_PERIOD = 0.5;
    public static final double PORTAL_BEHAVIOR_PERIOD = 4;

    private int lifespan; // portals last 12 seconds: 3 x 4 = 9

    public Portal(String id, Point position, List<PImage> images, int lifespan) {
        super(id, position, images, PORTAL_ANIMATION_PERIOD, PORTAL_BEHAVIOR_PERIOD);
        this.lifespan = lifespan;
    }

    @Override
    public void executeBehavior(World world, ImageLibrary imageLibrary, EventScheduler scheduler) {
        if (transform(world, scheduler, imageLibrary)) {
            scheduleBehavior(scheduler, world, imageLibrary);
        }
    }

    @Override
    public boolean transform(World world, EventScheduler scheduler, ImageLibrary imageLibrary) {
        if (lifespan == 2) {
            portalSpotDarken(world, imageLibrary);
        } else if (lifespan == 1) {
            portalFireSpread(world, scheduler, imageLibrary);
        } else if (lifespan == 0) {
            portalSpawnDemon(world, scheduler, imageLibrary);
            return false;
        }
        lifespan -= 1;
        return true;
    }

    public void portalSpotDarken(World world, ImageLibrary imageLibrary) {
        Background wherePortal = world.getBackgroundCell(getPosition());
        if (wherePortal.getId().contains("grass")) {
            Background burntGrass = new Background("Site of Portal", imageLibrary.get("grass_burnt"), 0);
            world.setBackgroundCell(getPosition(), burntGrass);
        }
    }

    public void portalFireSpread(World world, EventScheduler scheduler, ImageLibrary imageLibrary) {
        List<Point> flammables;
        flammables = Fire.flammable(world, getPosition());

        if (!flammables.isEmpty()) {
            int randNum = (int) (Math.random() * flammables.size());
            Point spawnPoint = flammables.get(randNum);
            Fire.whatBurntBackground(world, imageLibrary, spawnPoint);
            Fire portalFire = new Fire(Fire.FIRE_KEY + "_" + getID(), spawnPoint, imageLibrary.get(Fire.FIRE_KEY),
                    PORTAL_BEHAVIOR_PERIOD * 3, .25, 3);
            world.addEntity(portalFire);
            portalFire.scheduleActions(scheduler, world, imageLibrary);
        }
    }

    public void portalSpawnDemon(World world, EventScheduler scheduler, ImageLibrary imageLibrary) {
        Random rand = new Random();
        String demonID = Demon.setOfDemonNames.get(rand.nextInt(Demon.setOfDemonNames.size()));
        Demon demonSpawn = new Demon(demonID, getPosition(), imageLibrary.get("demon"), 0.2,
                1.5, 3);
        world.removeEntity(scheduler, this);
        world.addEntity(demonSpawn);
        demonSpawn.scheduleActions(scheduler, world, imageLibrary);
    }
}
