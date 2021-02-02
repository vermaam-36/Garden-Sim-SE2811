//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Garden;

import javafx.scene.image.ImageView;

public interface Flower {
    double getXPosition();

    double getYPosition();

    void setXPosition(double xPosition);

    void setYPosition(double yPosition);

    boolean getIsDeleted();

    void delete();
    /**
     * Method gives a flower energy and updates its health bar
     * @author Amish Verma
     */
    void gainEnergy();

    /**
     * Method acts as a pseudo constructor as attributes are set once a location is given
     * @param xPosition
     * @param yPosition
     * @author Amish Verma
     */
    void load(double xPosition, double yPosition);

    /**
     * Method takes energy away from a flower and updates its health bar
     * @author Amish Verma
     * */
    void loseEnergy();

    /**
     * Updates flowers internal turnCount to determine its behavior
     */
    void updateTurnCount();
    int getTurnCount();
    ImageView getFlowerImage();

    String getName();

    int getEnergy();
}
