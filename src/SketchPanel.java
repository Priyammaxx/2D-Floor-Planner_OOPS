import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JPanel;
import java.awt.Point;

public class SketchPanel extends JPanel{
    private DrawingTool drawingTool;
    private RectManager rectManager;
    private static final int GRID_SIZE = 16; // size in pixels
    Rectangle RectClicked;
    private boolean snapEnabled = true;
    private boolean gridEnabled = true;
    private boolean deleteEnabled = false;
    public static boolean moveEnabled = true;
    int prevX;
    int prevY;
    int changeX;
    int changeY;

    RectManager.ClickedRectangle clickedRectangle;

    public SketchPanel(DrawingTool drawingTool) {
        this.drawingTool = drawingTool;
        this.rectManager = new RectManager();



        addMouseListener(new MouseAdapter() {
            // this is annonymous inner class
            @Override
            public void mousePressed(MouseEvent e) {
                Point clickPoint = e.getPoint();
                clickedRectangle = rectManager.pointinRect(clickPoint);
                RectClicked = clickedRectangle.rect;
                prevX = snapToGrid(e.getX());
                prevY = snapToGrid(e.getY());
                if(!deleteEnabled && !(clickedRectangle.rectisClicked)){
                    drawingTool.startDrawing(snapToGrid(e.getX()), snapToGrid(e.getY()));
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if(deleteEnabled){
                    Point clickPoint = e.getPoint();
                    for (int i = 0; i < rectManager.rectangles.size(); i++) {
                        Rectangle rect = rectManager.rectangles.get(i);
                        if (rect.contains(clickPoint)) {
                            System.out.println("Clicked rectangle index: " + i);
                            // Perform your action here (e.g., removing the rectangle)
                            rectManager.rectangles.remove(i);
                            repaint();
                            break;
                        }
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (!moveEnabled) {
                    // Stop drawing and add the rectangle to the manager
                    rectManager.addRect(drawingTool.getCurrentRect());
                    drawingTool.finishDrawing();
                } else if (RectClicked != null) {
                    // Stop moving and snap the rectangle to the grid
                    int snappedX = snapToGrid(RectClicked.x);
                    int snappedY = snapToGrid(RectClicked.y);
                    RectClicked.setLocation(new Point(snappedX, snappedY));
                    clickedRectangle.rectisClicked = false;
                }
                repaint();
            }

        });

        addMouseMotionListener(new MouseMotionAdapter() {
            // this is annonymous inner class
            @Override
            public void mouseDragged(MouseEvent e) {
                if (!moveEnabled) {
                    // Continue drawing
                    drawingTool.continueDrawing(snapToGrid(e.getX()), snapToGrid(e.getY()));
                    repaint();
                } else if (RectClicked != null) {
                    // Move the rectangle
                    int deltaX = e.getX() - prevX;
                    int deltaY = e.getY() - prevY;
                    RectClicked.setLocation(RectClicked.x + deltaX, RectClicked.y + deltaY);
                    
                    boolean overlapCheck = false;
                    Rectangle overlappedRectangle = new Rectangle(0, 0, 0, 0);

                    // Check for overlaps
                    for (Rectangle rect : rectManager.rectangles) {
                        if (rect != RectClicked && RectClicked.intersects(rect)) {
                            overlapCheck = true;
                            overlappedRectangle = rect;
                            break;
                        }
                    }

                    if (overlapCheck) {
                        // Snap to adjacent side if there is an overlap
                        snapToAdjacentSide(RectClicked, overlappedRectangle);
                    }

                    prevX = e.getX();
                    prevY = e.getY();
                    repaint();
                }
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

    public void deleteItems(boolean enabled){
        deleteEnabled = enabled;
        repaint();
    }

    public void setmoveEnabled(boolean enabled){
        moveEnabled = enabled;
        repaint();
    }

    public void snapToAdjacentSide(Rectangle r1, Rectangle r2){
        int r1CenterX = r1.x + r1.width / 2;
        int r1CenterY = r1.y + r1.height / 2;

        int r2Left = r2.x;
        int r2Right = r2.x + r2.width;
        int r2Top = r2.y;
        int r2Bottom = r2.y + r2.height;

        // Determine which side r1 is approaching from
        if (r1CenterX < r2Left) {
            // Snap to the left side
            r1.setLocation(r2Left - r1.width, r1.y);
        } else if (r1CenterX > r2Right) {
            // Snap to the right side
            r1.setLocation(r2Right, r1.y);
        } else if (r1CenterY < r2Top) {
            // Snap to the top side
            r1.setLocation(r1.x, r2Top - r1.height);
        } else if (r1CenterY > r2Bottom) {
            // Snap to the bottom side
            r1.setLocation(r1.x, r2Bottom);
        }
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
