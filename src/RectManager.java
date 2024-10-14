
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.JLabel;

// Rect refers to a single rectangle object
// Rectangles refers to collection of rectangles

public class RectManager {
    private ArrayList<Rectangle> rectangles;
    private JLabel statusLabel;

    public RectManager(JLabel statusLabel) {
        rectangles = new ArrayList<>();
        this.statusLabel = statusLabel;
    }

    public void addRect(Rectangle newRect) {
        for (Rectangle rect: rectangles){
            if (newRect.intersects(rect)) {
                statusLabel.setText("Intersection Error!");
                return;
            }
            else{
                statusLabel.setText("Status: Ready");
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
