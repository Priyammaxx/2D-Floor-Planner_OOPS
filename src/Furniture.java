import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

// This will be changed later
// Furniture will render PNGS

public class Furniture extends CanvasObject{
    public String type = "Furniture";
    int imageIndex;
    
    public Furniture(int x, int y, int width, int height, int imageIndex) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.imageIndex = imageIndex;
        layer = 1;
    }

    @Override
    public void draw(Graphics2D g2d) {
        Graphics g = (Graphics) g2d;
        // g2d.setColor(Color.orange);
        // g2d.fill(this); // to color inside of rectangle
        // // remove above part later
        // g2d.setColor(Color.BLACK);
        // g2d.draw(this);
        Image image = FurnitureLoader.getInstance().getFurnitureImage(imageIndex);
        if (image != null) {
            g.drawImage(image, this.x, this.y, this.width, this.height, null);
        } else {
            System.out.println("not loaded");
        }
        
    }

    @Override
    public boolean contains(int x, int y) {
        Rectangle tempRect = new Rectangle(this.x, this.y, this.width, this.height); // if temp not created then this.contains() calls itself recursively
        return tempRect.contains(x, y);
    }

    @Override
    public void move(int deltaX, int deltaY) {
        this.x += deltaX;
        this.y += deltaY;
    }

    @Override
    public String getType() {
        return this.type;
    }

    // temp function, if deleted make make imageIndex public, changes in CanvasObjectTypeAdapter
    public int getImageIndex() {
        return this.imageIndex;
    }

    

}
