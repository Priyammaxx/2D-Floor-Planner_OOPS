import java.awt.Rectangle;

public class RectTool extends DrawingTool {

    // initial coordinates of where rectangle is dragged is from
    private int startX;
    private int startY;

    @Override
    public void startDrawing(int x, int y) {
        currentRect = new Rectangle(x, y, 0, 0);
        startX = x;
        startY = y;
    }

    @Override
    public void continueDrawing(int x, int y) {
        if (currentRect != null) {
            // implements multi-directional rectangle
            currentRect.width = Math.abs(x - startX);
            currentRect.height = Math.abs(y - startY);
            currentRect.x = Math.min(startX, x);
            currentRect.y = Math.min(startY, y);

        }
    }

    @Override
    public void finishDrawing() {
        // empty for now
        currentRect = null;
    }
    
}
