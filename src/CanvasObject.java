import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class CanvasObject extends Rectangle{
    abstract void draw(Graphics2D g2d); // get g2d component and display it
    public abstract boolean contains(int x, int y); // check if the selection point is inside the object
    public abstract void move(int deltaX, int deltaY); // move object by give deltas
    // deltas to be taken from change in relative mouse positions

    // These rotate functions are still broken
    // +90 degree
    public void rotateAntiClockwise() {
        int center_x = this.x + width / 2;
        int center_y = this.y + height / 2;
        int x_new = (this.y - center_y) + center_x;
        int y_new = (this.x - center_x) + center_y;
        this.x = x_new;
        // this.y = Math.min(y_new, y_new-this.width);
        this.y = y_new;
        int temp = this.width;
        this.width = this.height;
        this.height = temp;
    }
    // -90 degree
    public void rotateClockwise() {
        int center_x = this.x + width / 2;
        int center_y = this.y + height / 2;
        int x_new = (this.y - center_y) + center_x;
        int y_new = (this.x - center_x) + center_y;
        // this.x = Math.min(x_new, x_new-this.height);
        this.x = x_new;
        this.y = y_new;
        int temp = this.width;
        this.width = this.height;
        this.height = temp;
    }
}
