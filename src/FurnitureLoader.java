
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class FurnitureLoader {
    private static FurnitureLoader instance;

    BufferedImage kitchenSink;
    BufferedImage shower;
    BufferedImage stove;
    BufferedImage toilet;
    BufferedImage washbasin;
    
    // NOTE : also add BED, couch, dining Table, Almira !!

    private FurnitureLoader() {
        kitchenSink = loadImage("res/kitchenSink.png");
        shower = loadImage("res/shower.png");
        stove = loadImage("res/stove.png");
        toilet = loadImage("res/toilet.png");
        washbasin = loadImage("res/washbasin.png");
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

    public BufferedImage getKitchenSink() {
        return this.kitchenSink;
    }

    public BufferedImage getShower() {
        return this.shower;
    }

    public BufferedImage getStove() {
        return this.stove;
    }

    public BufferedImage getToilet() {
        return this.toilet;
    
    }
    public BufferedImage getWashbasin() {
        return this.washbasin;
    }

}
