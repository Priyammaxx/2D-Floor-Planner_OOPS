

public class DoorTool extends DrawingTool {

    private int startX;
    private int startY;

    @Override
    public void startDrawing(int x, int y) {
        currentObject = new Door(x, y, 0, 0);
        startX = x;
        startY = y;
    }

    @Override
    public void continueDrawing(int x, int y) {
        if (currentObject != null) {
            currentObject.width = Math.abs(x - startX);
            currentObject.height = Math.abs(y - startY);
            currentObject.x = Math.min(x, startX);
            currentObject.y = Math.min(y, startY);
        }
    }

    @Override
    public void finishDrawing() {
        if (!borderCheck() && currentObject != null) 
            CanvasObjectManager.getInstance().removeObject(currentObject);
        currentObject = null;
        
    }

    // bug: only checks for intersection of room in it area
    //      doesn't check for anything else, goes out of bound of house region
    private boolean borderCheck() {
        int count = 0;
        Room lastIntersected = null;
        for (CanvasObject object: CanvasObjectManager.getInstance().getObjects()) {
            if (currentObject.intersects(object) && currentObject != object) {
                if (object instanceof Room) {
                    lastIntersected = (Room)object;
                    count += 1;
                } else if (object instanceof Door || object instanceof Window) {
                    return false;
                }
            }
        }
        if (count == 0) return false;
        return (count == 2 || (count == 1 && (lastIntersected.getColor().getGreen() != 255 && lastIntersected.getColor().getBlue() != 255)) );
    }
    
}
