import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class CanvasObject extends Rectangle{
    abstract void draw(Graphics2D g2d); // get g2d component and display it
    public abstract boolean contains(int x, int y); // check if the selection point is inside the object
    public abstract void move(int deltaX, int deltaY); // move object by give deltas
    // deltas to be taken from change in relative mouse positions
}
