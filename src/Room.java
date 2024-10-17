
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Room extends CanvasObject{

    public Room(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.cyan);
        g2d.fill(this); // to color inside of rectangle
        // remove above part later
        g2d.setColor(Color.BLACK);
        g2d.draw(this);
    }

    @Override
    public boolean contains(int x, int y) {
        Rectangle tempRect = this; // if temp not created then this.contains() calls itself recursively
        return tempRect.contains(x, y);
    }

    @Override
    public void move(int deltaX, int deltaY) {
        this.x += deltaX;
        this.y += deltaY;
    }


}
