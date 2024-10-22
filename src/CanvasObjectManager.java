
import java.util.ArrayList;

// singleton class
// because there should be a single object manager that contains all the canvas objects
public class CanvasObjectManager {
    private ArrayList<CanvasObject> objects;
    private static CanvasObjectManager instance;

    private CanvasObjectManager() {
        objects = new ArrayList<>();
    }

    public static CanvasObjectManager getInstance() {
        if (instance == null) {
            instance = new CanvasObjectManager();
        }
        return instance;
    }

    public void addObject(CanvasObject newObject) {
        if (newObject.width == 0 || newObject.height == 0) {
            return;
        }
        for (CanvasObject object : objects) {
            if (object.intersects(newObject)) {
                System.out.println("Intersection ERROR!");
                return;
            }
        }
        objects.add(newObject);
    }

    public void removeObject(CanvasObject object) {
        objects.remove(object);
    }

    public CanvasObject getObjectAt(int x, int y) {
        for (CanvasObject object : objects) {
            if (object.contains(x,y)) {
                return object;
            }
        }
        return null;
    }

    public ArrayList<CanvasObject> getObjects() {
        return objects;
    }
}
