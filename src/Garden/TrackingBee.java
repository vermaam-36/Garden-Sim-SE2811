package Garden;


import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;
import java.util.Random;

/**
 * Class for handling a bee that tracks a given flower
 * @author Dylan Schultz
 */
public class TrackingBee implements Bee {
    private final double maxEnergy = 100;
    private double energy;
    private double xPosition;
    private double yPosition;
    private double nextXPosition;
    private double nextYPosition;
    private boolean isDeleted = true;
    private ImageView beeImage;
    private ProgressBar beeLabel;
    private final String name;
    private Flower flower;
    private final List<Flower> flowers;
    private final Random trackingNumber;

    /**
     * Constructor to instantiate a TrackingBee with the cordinates of the flower
     * it will be tracking
     * @param flowers to be tracked
     * @author Dylan Schultz
     */
    public TrackingBee(List<Flower> flowers){
        this.flowers = flowers;
        this.trackingNumber = new Random();
        this.flower = flowers.get(trackingNumber.nextInt(flowers.size()));
        this.name = "Tracking Bee";
    }

    @Override
    public void die() {
        this.isDeleted = true;
        this.energy = 0;
        this.xPosition = 0;
        this.yPosition = 0;
    }

    @Override
    public double getEnergy() {
        return this.energy;
    }

    @Override
    public void gainEnergy() {
        if(!this.isDeleted) {
            this.energy += 10;
            this.beeLabel.setProgress(energy / maxEnergy);
        }
    }

    @Override
    public void load(double xPosition, double yPosition) {
        this.isDeleted = false;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.nextXPosition = xPosition;
        this.nextYPosition = yPosition;
        this.energy = maxEnergy;
        this.beeImage = new ImageView(new Image("file:C:\\Users\\vermaa\\Documents\\GitHub\\Garden-Sim-SE2811\\bee-1.jpg"));
        beeImage.setPreserveRatio(true);
        beeImage.setFitWidth(50.0);
        beeImage.setFitHeight(50.0);
        this.beeLabel = new ProgressBar();
        beeLabel.setPrefWidth(beeImage.getFitWidth());
        beeLabel.setProgress(1);
    }

    @Override
    public void loseEnergy() {
        if(!isDeleted) {
            this.energy -= 10;
            this.beeLabel.setProgress(energy / maxEnergy);
        }
    }
    private void moveLogic(){
        double flowerX = this.flower.getXPosition();
        double flowerY = this.flower.getYPosition();
        double distance = calculateDistance(flowerX, flowerY, this.xPosition, this.yPosition);
        if(distance > 50) {
            double xMovement = Math.abs(flowerX - this.xPosition) / 10;
            double yMovement = Math.abs(flowerY - this.yPosition) / 10;
            if (!this.isDeleted) {
                if (this.xPosition < flowerX) {
                    this.nextXPosition += xMovement;
                } else if (this.xPosition > flowerX) {
                    this.nextXPosition -= xMovement;
                }
                if (this.yPosition > flowerY) {
                    this.nextYPosition -= yMovement;
                } else if (this.yPosition < flowerY) {
                    this.nextYPosition += yMovement;
                }
            }
        } else{
            this.flower = reTrack();
            moveLogic();
        }
    }
    @Override
    public void move() {
        this.nextXPosition = this.xPosition;
        this.nextYPosition = this.yPosition;
        moveLogic();
        this.xPosition = this.nextXPosition;
        this.yPosition = this.nextYPosition;
    }
    private Flower reTrack(){
        return this.flowers.get(this.trackingNumber.nextInt(this.flowers.size()));
    }
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
    @Override
    public double getXPosition(){
        return this.xPosition;
    }

    @Override
    public double getYPosition(){
        return this.yPosition;
    }

    @Override
    public ImageView getBeeImage() {
        return this.beeImage;
    }

    @Override
    public ProgressBar getBeeLabel() {
        return this.beeLabel;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean getIsDeleted() {
        return this.isDeleted;
    }

    @Override
    public double getNextXPosition() {
        this.nextXPosition = this.xPosition;
        moveLogic();
        return this.nextXPosition;
    }

    @Override
    public double getNextYPosition() {
        this.nextYPosition = this.yPosition;
        moveLogic();
        return this.nextYPosition;
    }
}
