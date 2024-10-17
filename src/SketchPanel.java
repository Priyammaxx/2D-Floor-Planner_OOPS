
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JPanel;

public class SketchPanel extends JPanel{
    private DrawingTool drawingTool;
    // private RectManager rectManager;
    private CanvasObjectManager objectManager = CanvasObjectManager.getInstance();
    private static final int GRID_SIZE = 16; // size in pixels
    private boolean snapEnabled = true;
    private boolean gridEnabled = true;
    private int offsetX, offsetY; // offset when dragging an object

    public SketchPanel(DrawingTool DrawingTool) {
        this.drawingTool = DrawingTool;

        addMouseListener(new MouseAdapter() {
            // this is annonymous inner class
            @Override
            public void mousePressed(MouseEvent e) {
                drawingTool.startDrawing(snapToGrid(e.getX()), snapToGrid(e.getY()));
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                CanvasObject finishedObject = drawingTool.getCurrentObject();
                drawingTool.finishDrawing();
                if (finishedObject != null) {
                    objectManager.addObject(finishedObject);
                }
                // if (drawingTool instanceof SelectTool) {
                //     selectedObject = null;
                // } else {
                //     objects.add(drawingTool.getCurrentObject());
                //     drawingTool.finishDrawing();
                // }
                repaint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            // this is annonymous inner class
            @Override
            public void mouseDragged(MouseEvent e) {
                drawingTool.continueDrawing(snapToGrid(e.getX()), snapToGrid(e.getY()));
                // if (selectedObject != null) {
                //     // move selected object
                //     selectedObject.setPosition(e.getX() - offsetX, e.getY() - offsetY);
                // } else {
                //     drawingTool.continueDrawing(snapToGrid(e.getX()), snapToGrid(e.getY()));
                // }
                repaint();
            }
        });

        // handle deletion of an object
        // but does this work??
        // getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
        //     KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "deleteObject");
        // getActionMap().put("deleteObject", new AbstractAction() {
        //     // @Override
        //     public void actionPerformed(ActionEvent e) {
        //         if (selectedObject != null) {
        //             objects.remove(selectedObject);
        //             selectedObject = null;
        //             repaint();
        //         }
        //     }
        // });
    }

    // snap coordinates to the nearest grid point
    private int snapToGrid(int value) {
        if (snapEnabled) {
            return (value / GRID_SIZE) * GRID_SIZE;
        }
        return value;
    }

    // Undo, specific for Rectangle
    // public void undoLastPath() {
    //     pathManager.undoLastPath();
    //     repaint();
    // }

    // Clear Canvas of all rectangles
    // general clearCanvas method for all shapes yet to be implemented
    // public void clearCanvas() {
    //     rectManager.clearAllRects();
    //     repaint();
    // }

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
        for (CanvasObject object: objectManager.getObjects()) {
            object.draw(g2d);
        }

        // draw current rectangle as the user drawgs the mouse
        // if (drawingTool.getCurrentRect() != null) {
        //     // don't know how this works
        //     // why does repaint need to be called again when this does its work??
        //     g2d.draw(drawingTool.getCurrentRect());
        // }

        // if (selectedObject != null) {
        //     g2d.setColor(Color.RED); // highlight in red
        //     g2d.draw(selectedObject.getBoundingBox());
        // }
        if (drawingTool.getCurrentObject() != null) {
            drawingTool.getCurrentObject().draw(g2d);
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

    // find object at the given coordinates
    // private CanvasObject getObjectAt(int x, int y) {
    //     for (CanvasObject obj : objects) {
    //         if(obj.contains(x, y)) {
    //             return obj;
    //         }
    //     }
    //     return null;
    // }

}
