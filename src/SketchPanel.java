import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

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
                //Condition where rectangle is clicked so that we can move it to another place
                clickedRectangle = rectManager.pointinRect(clickPoint);
                if(!deleteEnabled){
                    RectClicked = clickedRectangle.rect;
                    prevX = snapToGrid(e.getX());
                    prevY = snapToGrid(e.getY());
                }
                //Delete not enabled 
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
                            
                            rectManager.rectangles.remove(i);
                            repaint();
                            break;
                        }
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //Drawing Mode - moving disabled 
                if (!moveEnabled) {
                    
                    rectManager.addRect(drawingTool.getCurrentRect());
                    drawingTool.finishDrawing();
                } else{
                    //Moving mode - moving enabled so the moved rectangle is snapped to grid
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
                    //Drawing mode- moving disabled
                    drawingTool.continueDrawing(snapToGrid(e.getX()), snapToGrid(e.getY()));
                    repaint();
                } else {
                    //Tracks how much the mouse is moving on dragging so that the rectangle would be moved by that much in the same trajectory
                    int deltaX = e.getX() - prevX;
                    int deltaY = e.getY() - prevY;
                    RectClicked.setLocation(RectClicked.x + deltaX, RectClicked.y + deltaY);
                    //By default overlapcheck is false because there is no overlap
                    boolean overlapCheck = false; // If there is overlap it will become true
                    Rectangle overlappedRectangle = new Rectangle(0, 0, 0, 0); 
                    //Default case where no rectangle is getting overlapped by another. To prevent null pointer exception asssigned to zero rectangle

                    //Iterating through the previously drawn rectangles to check if anyone of them are getting overlapped
                    for (Rectangle rect : rectManager.rectangles) {
                        if (rect != RectClicked && RectClicked.intersects(rect)) {
                            overlapCheck = true;
                            overlappedRectangle = rect;
                            break;
                        }
                    }
                    //If there is overlap snap to the side of approach 
                    if (overlapCheck) {
                        
                        snapToAdjacentSide(RectClicked, overlappedRectangle);
                    }
                    //The clicked point becomes the new reference point
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

        // side of approach of top rectangle
        if (r1CenterX < r2Left) {
            //left approach
            r1.setLocation(r2Left - r1.width, r1.y);
        } else if (r1CenterX > r2Right) {
            //right approach
            r1.setLocation(r2Right, r1.y);
        } else if (r1CenterY < r2Top) {
            //approach from top
            r1.setLocation(r1.x, r2Top - r1.height);
        } else if (r1CenterY > r2Bottom) {
            //approach from the bottom
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
            drawRectangleSideLengths(g2d, rect);
        }

        // draw current rectangle as the user drags the mouse
        if (drawingTool.getCurrentRect() != null) {
            // don't know how this works
            // why does repaint need to be called again when this does its work??
            g2d.draw(drawingTool.getCurrentRect());
            drawRectangleSideLengths(g2d, drawingTool.getCurrentRect()); //If length of side is not required to be displayed delete this part.
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

    //If labelling of rectangle is not required delete this functionality

    private void drawRectangleSideLengths(Graphics2D g2d, Rectangle rect) {
        
        
        g2d.setColor(Color.BLACK); 
    
        
        
            int x = rect.x;
            int y = rect.y;
            int width = rect.width;
            int height = rect.height;
            if(width != 0 && height != 0){
            
            int leaderOffset = 10;

            // Top Side Length
                int topCenterX = x + width / 2;
                g2d.drawString("W: " + width, topCenterX - 15, y - leaderOffset);
                g2d.drawLine(topCenterX, y, topCenterX, y - leaderOffset); // leader line
            
                // Bottom side length
                //int bottomCenterX = x + width / 2;
                //g2d.drawString("W: " + width, bottomCenterX - 15, y + height + leaderOffset + 10);
                //g2d.drawLine(bottomCenterX, y + height, bottomCenterX, y + height + leaderOffset); //leader line
            
                //Left side length
                int leftCenterY = y + height / 2;
                g2d.drawLine(x, leftCenterY, x - leaderOffset, leftCenterY); 
            
                g2d.rotate(-Math.PI / 2, x - leaderOffset - 10, leftCenterY + 5); // rotate for vertical text
                g2d.drawString("H: " + height, x - leaderOffset - 10, leftCenterY + 5);
                g2d.rotate(Math.PI / 2, x - leaderOffset - 10, leftCenterY + 5); // reset
            
                //Draw right side length(Not required - always will be equal to left side length so doesn't matter)
                //int rightCenterY = y + height / 2;
                //g2d.drawLine(x + width, rightCenterY, x + width + leaderOffset, rightCenterY); // leader line
            //
                //g2d.rotate(-Math.PI / 2, x + width + leaderOffset + 10, rightCenterY + 5); // rotate for vertical text
                //g2d.drawString("H: " + height, x + width + leaderOffset + 10, rightCenterY + 5);
                //g2d.rotate(Math.PI / 2, x + width + leaderOffset + 10, rightCenterY + 5); 
         // to the left of the rectangle, centered on the height
            }
        
    }

}
