
import java.awt.Rectangle;
import java.util.ArrayList;
import java.awt.Point;
// Rect refers to a single rectangle object
// Rectangles refers to collection of rectangles

public class RectManager {
    protected ArrayList<Rectangle> rectangles;

    

    public RectManager() {
        rectangles = new ArrayList<>();
    }

    public void addRect(Rectangle newRect) {
        for (Rectangle rect: rectangles){
            if (newRect.intersects(rect)) {
                System.out.println("Cannot add RECTANGLE. Intersection ERROR!");
                return;
            }
        }
        rectangles.add(newRect);
    }

    public void undoLastRect() {
        if (!rectangles.isEmpty()) {
            rectangles.remove(rectangles.size() - 1);
        }
    }

    public void clearAllRects() {
        rectangles.clear();
    }

    public ArrayList<Rectangle> getRectangles() {
        return rectangles;
    }

    public class ClickedRectangle{
        public boolean rectisClicked;
        public Rectangle rect;
    }

    public ClickedRectangle pointinRect(Point p){
        ClickedRectangle clickedrect = new ClickedRectangle();
        clickedrect.rectisClicked = false;
        for (int i = 0; i < rectangles.size(); i++){
            Rectangle rect = rectangles.get(i);
            if (rect.contains(p)) {
                clickedrect.rectisClicked = true;
                clickedrect.rect = rect;
            }
        }
        return clickedrect;
    }
}
