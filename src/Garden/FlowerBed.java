package Garden;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

/**
 * Class handles general logic on the FlowerBed
 * @author Dylan Schultz Amish Verma
 */
public class FlowerBed {
    private final Random randomizer = new Random();
    ArrayList<Bee> bees;
    ArrayList<Flower> flowers;
    Hashtable<Double,Double> locations= new Hashtable<>();

    /**
     * Method calculates the distance between any two objects on screen
     * @param x1 of object 1
     * @param y1 of object 1
     * @param x2 of object 2
     * @param y2 of object 2
     * @return the distance between the objects
     * @author Amish Verma
     */
    private double calculateDistance(double x1, double y1, double x2, double y2){
        double xDisplace= x2-x1;
        double yDisplace= y2-y1;
        yDisplace= yDisplace*yDisplace;
        xDisplace=xDisplace*xDisplace;
        double distance= xDisplace+yDisplace;
        distance= Math.sqrt(distance);
        return distance;
    }

    public ArrayList<Bee> getBees() {
        return bees;
    }

    public ArrayList<Flower> getFlowers() {
        return flowers;
    }

    /**
     * Loads random bees into the bee list
     * @author Dylan Schultz
     */
    private void loadBees(){
        bees = new ArrayList<>();
        for(int i = 0; i < 4; i++){ //make four random bees
            if (randomizer.nextInt(2) == 0){
                bees.add(new PatternBee());
            } else{
                bees.add(new TrackingBee(this.flowers));
            }
        }
    }

    /**
     * Loads random flowers into the flower list
     * @author Dylan Schultz
     */
    private void loadFlowers() {
        flowers = new ArrayList<>();
        for (int i = 0; i < 4; i++) { //make four random flowers
            if (randomizer.nextInt(2) == 0) {
                flowers.add(new DrainFlower());
            } else {
                flowers.add(new NectarFlower());
            }

        }
    }

    /**
     * Method wraps flower and bee collision to check for collisions
     * @return if a collision happened for GardenController to handle
     * @author Dylan Schultz and Amish Verma
     */
    public boolean updateBed(){
        //locations.clear();
        for (Flower flower:
             flowers) {
            flower.updateTurnCount();
            if(flower.getName().equals("Nectar Flower")&&flower.getTurnCount()==3  && flower.getEnergy() != 100){
                flower.gainEnergy();
            }
        }
        return flowerCollision();
    }

    /**
     * Method checks if a bee collided with a flower
     * @return if bee collided with flower
     * @author Dylan Schultz
     */
    private boolean flowerCollision(){
        boolean isCollision = false;
        for(Flower flower : this.flowers){
            for(Bee bee : this.bees){
                if(calculateDistance(flower.getXPosition(), flower.getYPosition(), bee.getXPosition(), bee.getYPosition()) <= 50){
                    isCollision = true;
                    if(flower.getName().equals("Nectar Flower") && flower.getEnergy() != 0 && bee.getEnergy() != 100){
                        bee.gainEnergy();
                        flower.loseEnergy();
                    } else if(flower.getName().equals("Drain Flower") && flower.getEnergy() != 0){
                        if (flower.getTurnCount()!=3){
                            bee.loseEnergy();
                            flower.gainEnergy();
                        }else {
                            bee.gainEnergy();
                            flower.loseEnergy();
                        }
                    } if(bee.getEnergy() <= 0){
                        bee.die();
                    }
                }
            }
        }
        return isCollision;
    }

    /**
     * Method checks if a bee collided with a bee
     * @return if a bee collided with a bee
     * @author Dylan Schultz
     */
    public boolean beeCollision(){
        boolean isCollision = false;
        for(Bee beeOne : this.bees){
            for(Bee beeTwo : this.bees){
                double distance = calculateDistance(beeOne.getXPosition(), beeOne.getYPosition(), beeTwo.getXPosition(), beeTwo.getYPosition());
                if(distance != 0 && distance <= 50){
                    isCollision = true;
                    beeOne.loseEnergy();
                    beeTwo.loseEnergy();
                    if(beeTwo.getEnergy() <= 0){
                        beeTwo.die();
                    } else if(beeOne.getEnergy() <= 0){
                        beeOne.die();
                    }
                }
            }
        }
        return isCollision;
    }

    /**
     * Method initializes the locations of all bees and flowers
     * @param xLimit x limit of the screen
     * @param yLimit y limit of the screen
     * @author Amish Verma
     */
    public void setupBed(double xLimit, double yLimit){
        loadFlowers();
        for (Flower flower: flowers){
            double xVal=checkSetUpCollisions(xLimit,yLimit);
            double yVal=locations.get(xVal);
            flower.load(xVal, yVal);
        }
        loadBees();
        for (Bee bee:
             bees) {
            double xVal=checkSetUpCollisions(xLimit,yLimit);
            double yVal=locations.get(xVal);
            bee.load(xVal, yVal);
        }
    }

    /**
     * Method recursively checks if a collisions will happen on setup
     * @param xLimit of the screen
     * @param yLimit of the screen
     * @return where collision happened
     * @author Amish Verma
     */
    private double checkSetUpCollisions(double xLimit, double yLimit){
        double xVal= randomizer.nextDouble()*xLimit;
        double yVal= randomizer.nextDouble()*yLimit;
        if (locations.containsKey(xVal)) {
            if (locations.get(xVal) == yVal) {
                xVal=checkSetUpCollisions(xLimit, yLimit);
                yVal=locations.get(xVal);
            }
        }
        locations.put(xVal, yVal);
        return xVal;
    }
}
