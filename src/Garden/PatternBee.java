package Garden;


import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 * Class is responsible for bees that move in a back and forth patter
 * @author Dylan Schultz
 */
public class PatternBee implements Bee {
    private final double maxEnergy = 100;
    private double energy;
    private double xPosition;
    private double yPosition;
    private double nextXPosition;
    private double nextYPosition;
    private boolean isDeleted = true;
    private boolean isRight = true;
    private ImageView beeImage;
    private ProgressBar beeLabel;
    private final String name;
    private boolean isDown = true;

    public PatternBee(){
        this.name = "Pattern Bee";
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
        if(!isDeleted) {
            this.energy += 10;
            this.beeLabel.setProgress(energy / maxEnergy);
        }
    }

    @Override
    public void load(double xPosition, double yPosition) {
        this.energy = maxEnergy;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.isDeleted = false;
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
        if(!isDeleted) {
            checkXBounds();
            if (this.isRight) {
                this.nextXPosition += 30;
            }
            if (!this.isRight) {
                this.nextXPosition -= 30;
            }
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

    @Override
    public ImageView getBeeImage(){
        return this.beeImage;
    }

    @Override
    public ProgressBar getBeeLabel(){
        return this.beeLabel;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean getIsDeleted() {
        return this.isDeleted;
    }

    /**
     * Private method checks the X Bounds of the window so the bee doesn't fly off screen
     * If bounds are met method also calls moveVertical method to move bee downwards
     * @author Dylan Schultz
     */
    private void checkXBounds() {
        final double xUpperBound = 650;
        if (this.xPosition >= xUpperBound) {
            moveVertical();
            this.isRight = false;
        } else if (this.xPosition <= 0) {
            moveVertical();
            this.isRight = true;
        }
    }

    /**
     * Private Method moves the bee vertically
     * @author Dylan Schultz
     */
    private void moveVertical() {
        final double yUpperBound = 630;
        if(this.yPosition <= 0) {
            this.isDown = true;
        } else if(this.yPosition >= yUpperBound){
            this.isDown = false;
        }
        if(this.isDown){
            this.nextYPosition += 30;
        }
        if(!this.isDown){
            this.nextYPosition -= 30;
        }
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
    public double getNextXPosition() {
        this.nextXPosition = this.xPosition;
        moveLogic();
        return this.nextXPosition;
    }

    @Override
    public double getNextYPosition() {
        this.nextYPosition = this.yPosition;
        moveLogic();
        return nextYPosition;
    }

}
