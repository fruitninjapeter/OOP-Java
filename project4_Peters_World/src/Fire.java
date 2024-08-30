import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Fire extends Animateables {
    public static final String FIRE_KEY = "fire";

    public static final int FIRE_PARSE_PROPERTY_ANIMATION_PERIOD_INDEX = 0;

    public static final int FIRE_PARSE_PROPERTY_BEHAVIOR_PERIOD_INDEX = 1;

    public static final int FIRE_PARSE_PROPERTY_COUNT = 2;

    private int spreads;

    private final int maxSpreads;

    public Fire(String id, Point position, List<PImage> images,
                double behaviorPeriod, double AnimationPeriod, int spreads) {
        super(id, position, images, behaviorPeriod, AnimationPeriod);
        this.spreads = spreads;
        this.maxSpreads = spreads;
    }

    @Override
    public void executeBehavior(World world, ImageLibrary imageLibrary, EventScheduler scheduler) {
        if (stillBurns(world, scheduler, imageLibrary)) {
            scheduleBehavior(scheduler, world, imageLibrary);
        }
    }

    public boolean stillBurns(World world, EventScheduler scheduler, ImageLibrary imageLibrary) {
        if (spreads <= maxSpreads - 1) {
            fireSpread(world, scheduler, imageLibrary);
        }
        if (spreads <= 0) {
            destroyBridge(world, imageLibrary, getPosition());
            world.removeEntity(scheduler, this);
            return false;
        }
        spreads -= 1;
        return true;
    }

    public void fireSpread(World world, EventScheduler scheduler, ImageLibrary imageLibrary) {
        List<Point> flammables;
        flammables = flammable(world, getPosition());

        if (!flammables.isEmpty()) {
            int randNum = (int) (Math.random() * flammables.size());
            Point whereToBurn = flammables.get(randNum);
            whatBurntBackground(world, imageLibrary, whereToBurn);

            Fire newflame = new Fire(getID() + "it burns! Hot HOt hot!", whereToBurn,
                    imageLibrary.get(FIRE_KEY), getBehaviorPeriod(),
                    getAnimationPeriod(), spreads-1);
            world.addEntity(newflame);
            newflame.scheduleActions(scheduler, world, imageLibrary);
        }
    }

    public static void whatBurntBackground(World world, ImageLibrary imageLibrary, Point whereToBurn) {
        String whatBurnt = world.getBackgroundCell(whereToBurn).getId();
        String whatWas;
        if (whatBurnt.contains("grass")) {
            whatWas = "grass";
        } else {
            if (whatBurnt.contains("horizontal")) {
                whatWas = "bridge_horizontal";
            } else {
                whatWas = "bridge_vertical";
            }
        }
        Background burntBackground = new Background(whatWas + "_burnt",
                imageLibrary.get(whatWas + "_burnt"), 0);
        world.setBackgroundCell(whereToBurn, burntBackground);
    }

    public void destroyBridge(World world, ImageLibrary imageLibrary, Point whereBridge) {
        if (world.getBackgroundCell(getPosition()).getId().contains("bridge_horizontal_burnt")) {
            Background bridgeGoneHorizontal = new Background("destroyed bridge",
                    imageLibrary.get("bridge_horizontal_destroyed"), 0);
            world.setBackgroundCell(getPosition(), bridgeGoneHorizontal);
        } else if (world.getBackgroundCell(whereBridge).getId().contains("bridge_vertical_burnt")) {
            Background bridgeGoneVertical = new Background("destroyed bridge",
                    imageLibrary.get("bridge_horizontal_destroyed"), 0);
            world.setBackgroundCell(getPosition(), bridgeGoneVertical);
        }
    }

    public static List<Point> flammable(World world, Point origin) {
        Stream<Point> nearPoints = PathingStrategy.CARDINAL_NEIGHBORS.apply(origin);

        List<Point> occupiable = nearPoints
                .filter(p -> world.inBounds(p) && !world.isOccupied(p) && world.hasBackground(p)).toList();

        List<Point> flammables = new ArrayList<>();

        for (Point pee : occupiable) {
            Background watDis = world.getBackgroundCell(pee);
            if (watDis.getId().contains("grass") || watDis.getId().contains("bridge")) {
                flammables.add(pee);
            }
        }
        return flammables;
    }

    public void burnEntity(World world, EventScheduler scheduler, ImageLibrary imageLibrary, Entity whoDis) {
        if (!whoDis.getBurnable()) {
            return;
        }
        if (whoDis instanceof Tree) {
            ((SacrificialWood) whoDis).setHealth(((SacrificialWood) whoDis).getHealth() - 1);
        }
    }
}