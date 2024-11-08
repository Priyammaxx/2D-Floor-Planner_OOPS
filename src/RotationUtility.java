
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class RotationUtility {
    public static void RotateWithContained(CanvasObjectManager objectManager, CanvasObject currentObject, int angle) {
        // if (currentObject == null) return;

        int pivotX = currentObject.x + currentObject.width / 2;
        int pivotY = currentObject.y + currentObject.height / 2;

        for (CanvasObject object: objectManager.getObjects()) {
            if (isInsideOrOnBorder(currentObject, object)) { //changed function to add case of touching border
                rotateObject(object, pivotX, pivotY, angle);
            }
        }

    }


    public static void rotateObject(CanvasObject object, int pivotX, int pivotY, int angle) {
        double objectCenterX = object.getX() + object.getWidth() / 2;
        double objectCenterY = object.getY() + object.getHeight() / 2;

        AffineTransform transform = AffineTransform.getRotateInstance(Math.toRadians(angle), pivotX, pivotY);

        Point2D rotatedCenter = transform.transform(new Point2D.Double(objectCenterX, objectCenterY), null);

        int newX = (int) (rotatedCenter.getX() - object.height / 2);
        int newY = (int) (rotatedCenter.getY() - object.width / 2);

        // object.setLocation(newX, newY);
        object.x = newX;
        object.y = newY;

        int temp = object.width;
        object.width = object.height;
        object.height = temp;

    }


    //Function to check if furniture is inside room
    private static boolean isInsideOrOnBorder(CanvasObject container, CanvasObject object) {
        boolean fullyContained = (object.x >= container.x && object.x + object.width <= container.x + container.width) &&
                                 (object.y >= container.y && object.y + object.height <= container.y + container.height);

        boolean touchingInsideBorders = (object.x <= container.x + container.width && object.x + object.width >= container.x) &&
                                         (object.y <= container.y + container.height && object.y + object.height >= container.y);


        // This is not working because of MATH logic error
        // boolean bothCases = (( object.x >= container.x && (object.x + object.width <= container.x + container.width))
        //                     || (object.y >= container.y && (object.y + object.height <= container.y + container.height)) );

        // return bothCases; 
        if (fullyContained){
            System.out.println("fully contained");
        }                  
        else {
            if(touchingInsideBorders){
             System.out.println("touching borders"); 
            }
            else System.out.println("outside probably or IDK WTH IS GOING ONN");
        }
        return fullyContained || touchingInsideBorders;
    }
}
