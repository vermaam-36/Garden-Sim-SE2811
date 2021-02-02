package Garden;


import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;

/**
 * Interface to control bees
 * @author Dylan Schultz
 */
public interface Bee {
    /**
     * Method will set a bees attributes to essentially zero for death
     * @author Dylan Schultz
     */
    void die();
    double getEnergy();

    /**
     * Method gives a bee energy and updates its health bar
     * @author Dylan Schultz
     */
    void gainEnergy();

    /**
     * Method acts as a pseudo constructor as attributes are set once a location is given
     * @param xPosition of bee
     * @param yPosition of bee
     * @author Dylan Schultz
     */
    void load(double xPosition, double yPosition);

    /**
     * Method takes energy away from a bee and updates its health bar
     * @author Dylan Schultz
     */
    void loseEnergy();

    /**
     * Method moves a bee according to its behavior
     * @author Dylan Schultz
     */
    void move();
    double getXPosition();
    double getYPosition();
    ImageView getBeeImage();
    ProgressBar getBeeLabel();
    String getName();
    boolean getIsDeleted();
    double getNextXPosition();
    double getNextYPosition();
}
