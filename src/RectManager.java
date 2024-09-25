
import java.awt.Rectangle;
import java.util.ArrayList;

// Rect refers to a single rectangle object
// Rectangles refers to collection of rectangles

public class RectManager {
    private ArrayList<Rectangle> rectangles;

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
}
