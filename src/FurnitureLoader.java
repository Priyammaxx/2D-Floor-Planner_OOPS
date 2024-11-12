
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class FurnitureLoader {
    private static FurnitureLoader instance;

    BufferedImage kitchenSink;
    BufferedImage shower;
    BufferedImage stove;
    BufferedImage toilet;
    BufferedImage washbasin;
    ArrayList<BufferedImage> furnitureImages;
    
    // NOTE : also add BED, couch, dining Table, Almira !!

    private FurnitureLoader() {
        kitchenSink = loadImage("res/kitchenSink.png");
        shower = loadImage("res/shower.png");
        stove = loadImage("res/stove.png");
        toilet = loadImage("res/toilet.png");
        washbasin = loadImage("res/washbasin.png");
        
        furnitureImages = new ArrayList<>();

        furnitureImages.add(kitchenSink); // index = 0
        furnitureImages.add(shower); // index = 1
        furnitureImages.add(stove); // index = 2
        furnitureImages.add(toilet); // index = 3
        furnitureImages.add(washbasin); // index= 4

    }

    // this was copied
    // don't have an understanding yet
    private BufferedImage loadImage(String fileName) {
        try {
            String absolutePath = new File(fileName).getAbsolutePath();
            File  inputStream = new File(absolutePath);
            return ImageIO.read(inputStream);
            
        } catch (IOException e) {
            System.out.println("File not found: " + fileName);;
        }
        return null;
    }

    public static FurnitureLoader getInstance() {
        if (instance == null) {
            instance = new FurnitureLoader();
        }
        return instance;
    }

    public BufferedImage getFurnitureImage(int index) {
        return this.furnitureImages.get(index);
    }

}
