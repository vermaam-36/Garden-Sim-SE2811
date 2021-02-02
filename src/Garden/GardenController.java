package Garden;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


import java.util.ArrayList;
import java.util.List;

/**
 * Class controls the garden display
 * @author Dylan Schultz, Amish Verma
 */
public class GardenController {

    private List<Flower> flowers;
    private List<Bee> bees;
    private List<Pane> flowerImages;
    private List<Pane> beeImages;
    private FlowerBed flowerBed;

    @FXML
    private Pane theGarden; // capture the pane we are drawing on from JavaFX
    @FXML
    private ImageView trackingBee;
    @FXML
    private ImageView patternBee;
    @FXML
    private ImageView nectarFlower;
    @FXML
    private ImageView drainFlower;

    /**
     * Method sets up all variables and calls helper methods to set variables up for control
     * @author Dylan Schultz
     */
    @FXML
    public void initialize() {              // executed after scene is loaded but before any methods
        flowerBed = new FlowerBed();
        // for fun, set up a gradient background; could probably do in SceneBuilder as well
        // note the label has a Z index of 2 so it is drawn above the panel, otherwise it may be displayed "under" the panel and not be visible
        theGarden.setStyle("-fx-background-color: linear-gradient(to bottom right, derive(cyan, 20%), derive(cyan, -40%));");
        trackingBee.setImage(new Image("file:bee-2.jpg"));
        patternBee.setImage(new Image("file:bee-1.jpg"));
        nectarFlower.setImage(new Image("file:daisy.jpg"));
        drainFlower.setImage(new Image("file:nightshade.jpg"));
        locator();
        this.bees = flowerBed.getBees();
        this.flowers = flowerBed.getFlowers();
        fillBeeImages();
        fillFlowerImages();
        display();
    }

    /**
     * Method wraps the setupBed bed method in FlowerBed to initialize all bee and flower locations
     */
    private void locator() {
        this.flowerBed.setupBed(700, 650);
    }

    /**
     * Method displays all flowers and bee on screen. Helper method is implemented for bees
     * @author Dylan Schultz
     */
    private void display() {
        int flowerCounter = 0;
        displayBee();
        for (Pane flower : flowerImages) {
            Flower thisFlower = this.flowers.get(flowerCounter);
            flowerCounter++;
            double xPos = thisFlower.getXPosition();
            double yPos = thisFlower.getYPosition();
            if (xPos > theGarden.getPrefWidth() - 50) {
                xPos -= theGarden.getPrefWidth() - 50;
            } else if (xPos < 50) {
                xPos += 50;
            }
            if (yPos > theGarden.getPrefHeight() - 50) {
                yPos -= theGarden.getPrefHeight() - 50;
            } else if (yPos < 50) {
                yPos += 50;
            }
            thisFlower.setXPosition(xPos);
            thisFlower.setYPosition(yPos);
            flower.setLayoutX(xPos);
            flower.setLayoutY(yPos);
        }
    }

    /**
     * Method displays the bees on screen
     * @author Dylan Schultz
     */
    private void displayBee() {
        int beeCounter = 0;
        for (Pane bee : beeImages) {
            Bee thisBee = this.bees.get(beeCounter);
            beeCounter++;
            double xPos = thisBee.getXPosition();
            double yPos = thisBee.getYPosition();
            if (xPos > theGarden.getPrefWidth() - 50) {
                xPos -= theGarden.getPrefWidth() - 50;
            } else if (xPos < 50) {
                xPos += 50;
            }
            if (yPos > theGarden.getPrefHeight() - 50) {
                yPos -= theGarden.getPrefHeight() - 50;
            } else if (yPos < 50) {
                yPos += 50;
            }
            bee.setLayoutX(xPos);
            bee.setLayoutY(yPos);
        }
    }

    /**
     * Method preps all flower objects to be displayed on screen
     *
     * @author Dylan Schultz
     */
    private void fillFlowerImages() {
        this.flowerImages = new ArrayList<>();
        for (Flower flower : this.flowers) {
            Pane flowerPane = new VBox();
            flowerPane.getChildren().add(flower.getFlowerImage());
            this.flowerImages.add(flowerPane);
        }
        theGarden.getChildren().addAll(flowerImages);
    }

    /**
     * Method preps all bee objects to be displayed on screen
     *
     * @author Dylan Schultz
     */
    private void fillBeeImages() {
        this.beeImages = new ArrayList<>();
        for (Bee bee : this.bees) {
            Pane beePane = new VBox();
            beePane.getChildren().add(bee.getBeeImage());
            beePane.getChildren().add(bee.getBeeLabel());
            this.beeImages.add(beePane);
        }
        theGarden.getChildren().addAll(beeImages);
    }

    /**
     * Method handles the key being pressed ticking time
     * @param keyEvent the key being pressed
     * @author Dylan Schultz
     */
    @FXML
    public void onKeyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.RIGHT) {
            for (Bee bee : bees) {
                bee.move();
            }
            moveBees();
        }
    }

    /**
     * private method moves all bees and checks for collision with updateBed
     * @author Dylan Schultz
     */
    private void moveBees() {
        int counter = 0;
            for (Pane bee : this.beeImages) {
                Bee thisBee = this.bees.get(counter);
                counter++;
                bee.setLayoutX(thisBee.getXPosition());
                bee.setLayoutY(thisBee.getYPosition());
            }
            if (flowerBed.updateBed()) {
                refreshImage();
            } if(flowerBed.beeCollision()){
                refreshImage();
        }
            if(checkDead()){
                Alert dead = new Alert(Alert.AlertType.INFORMATION);
                dead.setTitle("Bees Are Dead!");
                dead.setContentText("All Your Bees Died");
                dead.showAndWait();
            }
    }

    /**
     * Private method handles a collision if detected
     * @author Dylan Schultz
     */
    private void refreshImage() {
        this.bees = flowerBed.getBees();
        this.flowers = flowerBed.getFlowers();
        for (int i = 0; i < this.beeImages.size(); i++) {
            Bee bee = this.bees.get(i);
            Pane beePane = this.beeImages.get(i);
            if(!bee.getIsDeleted()) {
                beePane.getChildren().remove(1);
                beePane.getChildren().add(bee.getBeeLabel());
            } else {
                beePane.getChildren().clear();
            }
        }
    }
    private boolean checkDead(){
        int areDead = 0;
        for(Bee bee : this.bees){
            if(bee.getIsDeleted()){
                areDead++;
            }
        }
        return areDead == this.bees.size() - 1;
    }
}

