import processing.core.PImage;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Demon extends Animateables implements Transformables {

    public static final String DEMON_KEY = "demon";

    public static final int DEMON_PARSE_PROPERTY_ANIMATION_PERIOD_INDEX = 0;
    public static final int DEMON_PARSE_PROPERTY_BEHAVIOR_PERIOD_INDEX = 1;
    public static final int DEMON_PARSE_PROPERTY_COUNT = 2;

    public static final List<String> setOfDemonNames =
            Arrays.asList("Kane", "The Undertaker", "Beelzebuubies", "AsmodeezNuts", "Sister Abigail",
                    "Jack the Ripper", "The False Prophet", "Daeron Da Destroyah", "Skarbrand", "Lucifer",
                    "Be'lakor", "N'Kari", "The Fiend", "Uncle Howdy", "REEEEEEEEEEEEE", "Dirty Dom");

    private int spreads;

    private int soulsCollected = 0;

    private boolean ascended = false;

    public Demon(String id, Point position, List<PImage> images,
                double animationPeriod, double behaviorPeriod, int spreads) {
        super(id, position, images, animationPeriod, behaviorPeriod);
        this.spreads = spreads;
    }

    @Override
    public void executeBehavior(World world, ImageLibrary imageLibrary, EventScheduler scheduler) {
        Optional<Entity> demonTarget = findDemonTarget(world);

        if (demonTarget.isEmpty() || !moveTew(world, demonTarget.get(), scheduler, imageLibrary) ||
                transform(world, scheduler, imageLibrary)) {
            scheduleBehavior(scheduler, world, imageLibrary);
        }
    }

    public Optional<Entity> findDemonTarget(World world) {
        List<Class<?>> potentialObjectives = null;

        if (!ascended) {
            potentialObjectives = List.of(Fairy.class);
        } else {
            potentialObjectives = List.of(Tree.class, Dude.class, Stump.class, Fairy.class);
        }
        return world.findNearest(getPosition(), potentialObjectives);
    }

    public boolean moveTew(World world, Entity target, EventScheduler scheduler, ImageLibrary imageLibrary) {
        if (getPosition().adjacentTo(target.getPosition())) {
            world.removeEntity(scheduler, target);
            soulsCollected += 1;
            return true;
        } else {
            Point nextSpot = nextPosition(world, target.getPosition());
            if (!getPosition().equals(nextSpot)) {
                world.moveEntity(scheduler, this, nextSpot);
                if (spreads > 0) {
                    demonFireSpread(world, scheduler, imageLibrary);
                }
            }
            return false;
        }
    }


    public Point nextPosition(World world, Point destination) {
        PathingStrategy doYouKnowDaWae = new AStarPathingStrategy();

        Predicate<Point> traversable = p -> world.inBounds(p) &&
                //!(world.isOccupied(p) && !(world.getOccupant(p).get() instanceof Fairy)) &&
                !(world.isOccupied(p) && !((world.getOccupant(p).get() instanceof Fire) ||
                        (world.getOccupant(p).get() instanceof Lava)));

        BiPredicate<Point, Point> reachable = Point::adjacentTo;

        Function<Point, Stream<Point>> neighbors = PathingStrategy.CARDINAL_NEIGHBORS;

        List<Point> daWae = doYouKnowDaWae.computePath(getPosition(), destination,
                traversable, reachable, neighbors);

        if (!daWae.isEmpty()) {
            return daWae.getFirst();
        }

        return getPosition();
    }

    @Override
    public boolean transform(World world, EventScheduler scheduler, ImageLibrary imageLibrary) {
        if (soulsCollected > 0 && !ascended) {
            Demon ascendedDemon = new Demon(getID(), getPosition(), imageLibrary.get(DEMON_KEY + "_ascend"),
                    getAnimationPeriod()*.75, getBehaviorPeriod()*.75, spreads + 2);
            ascendedDemon.setAscended(true);
            world.removeEntity(scheduler, this);
            world.addEntity(ascendedDemon);
            ascendedDemon.scheduleActions(scheduler, world, imageLibrary);
            return true;
        }
        return false;
    }

    public void setAscended(boolean b) {
        this.ascended = b;
    }

    public void demonFireSpread(World world, EventScheduler scheduler, ImageLibrary imageLibrary) {
        List<Point> flammables;
        flammables = Fire.flammable(world, getPosition());
        if (!flammables.isEmpty()) {
            int randNum = (int) (Math.random() * flammables.size());
            Point spawnPoint = flammables.get(randNum);
            Fire.whatBurntBackground(world, imageLibrary, spawnPoint);
            Fire portalFire = new Fire(Fire.FIRE_KEY + "_" + getID(), spawnPoint, imageLibrary.get(Fire.FIRE_KEY),
                    getBehaviorPeriod() * 3, .25, 3);
            world.addEntity(portalFire);
            portalFire.scheduleActions(scheduler, world, imageLibrary);
            spreads -= 1;
        }
        flammables = flammables.stream().
                filter(p -> !world.isOccupied(p)).toList();

        if (!flammables.isEmpty()) {
            int randNum = (int) (Math.random() * flammables.size());
            Point whereToLava = flammables.get(randNum);
            Fire.whatBurntBackground(world, imageLibrary, whereToLava);

            Lava newLava = new Lava(getID() + "it singes! Hot! Hot! Juicy!", whereToLava,
                    imageLibrary.get(Lava.LAVA_KEY),
                    3, 12);
            world.addEntity(newLava);
            newLava.scheduleActions(scheduler, world, imageLibrary);
        }
    }
}