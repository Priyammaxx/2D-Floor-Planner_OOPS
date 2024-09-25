import java.awt.Rectangle;

public class FurnitureTool extends DrawingTool{

    private int startX;
    private int startY;

    // This shouldn't draw a rectangle
    // Display an image of the furniture PNG while drawing

    @Override
    public void startDrawing(int x, int y) {
        // this will place pngs similar to RectTool
        currentRect = new Rectangle(x,y,0,0);
        startX = x;
        startY = y;
    }

    @Override
    public void continueDrawing(int x, int y) {
        if (currentRect != null) {
            // similar to RectTool methods
            currentRect.width = Math.abs(x - startX);
            currentRect.height = Math.abs(y - startY);
            currentRect.x = Math.min(startX, x);
            currentRect.y = Math.min(startY, y);
        }
    }

    @Override
    public void finishDrawing() {
        // make currentRect null when finished drawing
        currentRect = null;
    }


    
}
