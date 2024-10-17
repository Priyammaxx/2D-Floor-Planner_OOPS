
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class RoomObject extends CanvasObject {
    private int width, height;

    public RoomObject(int x, int y, int width, int height) {
        super(x,y);
        this.width = width;
        this.height = height;
    }
    
    @Override
    public void draw(Graphics2D g) {
        g.drawRect(x,y, width, height);
    }

    @Override
    public boolean contains(int x, int y) {
        return new Rectangle(this.x, this.y, width, height).contains(x,y);
    }

    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(x, y, width, height);
    }
    
}
