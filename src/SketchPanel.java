
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JPanel;

public class SketchPanel extends JPanel{
    private DrawingTool drawingTool;
    private RectManager rectManager;
    private static final int GRID_SIZE = 16; // size in pixels
    private boolean snapEnabled = true;
    private boolean gridEnabled = true;

    public SketchPanel(DrawingTool drawingTool) {
        this.drawingTool = drawingTool;
        this.rectManager = new RectManager();

        addMouseListener(new MouseAdapter() {
            // this is annonymous inner class
            @Override
            public void mousePressed(MouseEvent e) {
                drawingTool.startDrawing(snapToGrid(e.getX()), snapToGrid(e.getY()));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                rectManager.addRect(drawingTool.getCurrentRect());
                drawingTool.finishDrawing();
                repaint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            // this is annonymous inner class
            @Override
            public void mouseDragged(MouseEvent e) {
                drawingTool.continueDrawing(snapToGrid(e.getX()), snapToGrid(e.getY()));
                repaint();
            }
        });
    }

    // snap coordinates to the nearest grid point
    private int snapToGrid(int value) {
        if (snapEnabled) {
            return (value / GRID_SIZE) * GRID_SIZE;
        }
        return value;
    }

    // Undo, specific for Rectangle
    public void undoLastRect() {
        rectManager.undoLastRect();
        repaint();
    }

    // Clear Canvas of all rectangles
    // general clearCanvas method for all shapes yet to be implemented
    public void clearCanvas() {
        rectManager.clearAllRects();
        repaint();
    }

    // Change the drawing tool
    // this method is called by SketchApp, in SkethApp it is called by ToolPanel
    public void setDrawingTool(DrawingTool tool) {
        this.drawingTool = tool;
    }


    // -------Toggle Features Area------------
    
    // toggle snap function
    public void setSnapEnabled(boolean enabled) {
        snapEnabled = enabled;
    }

    // toggle grid
    public void setGridEnabled(boolean enabled) {
        gridEnabled = enabled;
        repaint();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // don't know how it works, why it works
        Graphics2D g2d = (Graphics2D) g;
        if (gridEnabled) {
            // draw grid first then draw other things on top of it
            drawLineGrid(g2d);
            // Dot grid is commented
            // drawDotGrid(g2d);
        }
            

        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2)); // drawing rectangle stroke size

        // get all rectangles stored rectManger and loop through
        // draw all those rectangles
        for (Rectangle rect: rectManager.getRectangles()) {
            g2d.draw(rect);
        }

        // draw current rectangle as the user drawgs the mouse
        if (drawingTool.getCurrentRect() != null) {
            // don't know how this works
            // why does repaint need to be called again when this does its work??
            g2d.draw(drawingTool.getCurrentRect());
        }
    }

    // method for fixed grid
    private void drawLineGrid(Graphics2D g2d) {
        g2d.setColor(Color.lightGray);

        for (int x = 0; x < getWidth(); x += GRID_SIZE) {
            g2d.drawLine(x, 0, x, getHeight());
        }

        for (int y = 0; y < getHeight(); y += GRID_SIZE) {
            g2d.drawLine(0, y, getWidth(), y);
        }
    }

    private void drawDotGrid(Graphics g2d) {
        int ovalSize = 4;
        g2d.setColor(Color.lightGray);
        for (int y = 0; y < getHeight(); y += GRID_SIZE) {
            for (int x = 0; x < getWidth(); x += GRID_SIZE) {
                g2d.fillOval(x-ovalSize/2, y-ovalSize/2, ovalSize, ovalSize);
            }

        }

    }

}
