
import java.awt.Rectangle;

public abstract class DrawingTool {
    protected Rectangle currentRect;

    public abstract void startDrawing(int x, int y);

    public abstract void continueDrawing(int x, int y);

    public abstract void finishDrawing();

    // return the current shape / line
    public Rectangle getCurrentRect() {
        return currentRect;
    }
    
}
