
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Door extends CanvasObject {
    
    public Door(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        layer = 2;
    }

    @Override
    void draw(Graphics2D g2d) {
        // below code is directly copied from ChatGPT, may need changes

         // Save the original composite setting to restore later
        // Composite originalComposite = g2d.getComposite();

        // Step 1: Clear all content within the feature rectangle area
        // g2d.setComposite(AlphaComposite.Clear); // Set to clear mode
        g2d.setColor(new Color(255,255,255,150));
        g2d.fill(this); // Clear the specified area

        // // Step 2: Restore the composite to normal and draw a faint border around the feature rectangle
        // g2d.setComposite(AlphaComposite.SrcOver);
        // // g2d.setColor(new Color(0, 0, 0, 64)); // Semi-transparent border color
        // g2d.draw(this); // Draw the border

        // // Restore the original composite for any further drawing
        // g2d.setComposite(originalComposite);
    }

    @Override
    public boolean contains(int x, int y) {
        // this is temporary, may not even be required in the future
        // delte if not being used
        Rectangle tempRect = new Rectangle(this.x, this.y, this.width, this.height);
        return tempRect.contains(x,y);
        // if there is new logic that comes below
    }

    @Override
    public void move(int deltaX, int deltaY) {
        this.x += deltaX;
        this.y += deltaY;
    }
    
}
