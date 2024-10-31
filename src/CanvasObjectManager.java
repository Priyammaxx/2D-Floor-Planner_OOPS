
import java.util.ArrayList;
import javax.swing.JLabel;

// singleton class
// because there should be a single object manager that contains all the canvas objects
public class CanvasObjectManager {
    private ArrayList<CanvasObject> objects;
    private static CanvasObjectManager instance;
    private JLabel statusLabel;

    private CanvasObjectManager() {
        objects = new ArrayList<>();
    }

    public void getLabel( JLabel statusLabel){
        this.statusLabel = statusLabel;
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
            if (object.intersects(newObject) ){//&& (object instanceof Room && newObject instanceof Furniture && !object.contains(newObject))) {
                statusLabel.setText("Intersection ERROR!");
                return;
            }
            else statusLabel.setText("Status: Ready");
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
