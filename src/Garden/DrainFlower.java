//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Garden;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DrainFlower implements Flower {
    private int turnCount=0;
    private int energy = 0;
    private double xPosition = 0.0D;
    private double yPosition = 0.0D;
    private boolean isDeleted = true;
    private ImageView flowerImage;
    private String name;

    public DrainFlower() {
        this.name = "Drain Flower";
    }

    public double getXPosition() {
        return this.xPosition;
    }

    public double getYPosition() {
        return this.yPosition;
    }

    @Override
    public void setXPosition(double xPosition) {
        this.xPosition = xPosition;
    }

    @Override
    public void setYPosition(double yPosition) {
        this.yPosition = yPosition;
    }

    public boolean getIsDeleted() {
        return this.isDeleted;
    }

    public int getTurnCount() {
        return turnCount;
    }

    public void updateTurnCount() {
        if(turnCount==3){
            turnCount=0;
        }else {
            turnCount++;
        }
    }


    public void delete() {
        this.energy = 10;
        this.xPosition = 0.0D;
        this.yPosition = 0.0D;
    }

    public void gainEnergy() {
        if (this.isDeleted) {
        } else{
            this.energy += 10;
        }

    }

    public void load(double xPosition, double yPosition) {
        this.energy = 50;
        this.isDeleted = false;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.flowerImage = new ImageView(new Image("file:C:\\Users\\vermaa\\Documents\\GitHub\\Garden-Sim-SE2811\\nightshade.jpg"));
        flowerImage.setPreserveRatio(true);
        flowerImage.setFitWidth(50);
    }

    public void loseEnergy() {
        if (!this.isDeleted) {
            this.energy -= 10;
        }

    }

    @Override
    public ImageView getFlowerImage() {
        return this.flowerImage;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getEnergy() {
        return this.energy;
    }
}
