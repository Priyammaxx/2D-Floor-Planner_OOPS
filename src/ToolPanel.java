
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ToolPanel extends JPanel {
    private SketchApp app;

    public ToolPanel(SketchApp app) {
        this.app = app;
        setLayout(new GridLayout(0,1)); // set single column layout

        // rectTool = new RectTool();
        // furnitureTool = new FurnitureTool();

        JButton rectToolButton = new JButton("Room Tool"); // rename later to a Geneal Room Button
        rectToolButton.addActionListener(e -> app.setDrawingTool(new RoomTool()));
        // above is incomplete, make setDrawingTool in SketchApp

        JButton furnitureToolButton = new JButton("Furniture Tool");
        furnitureToolButton.addActionListener(e -> app.setDrawingTool(new FurnitureTool()));
        // need FurnitureTool class

        add(rectToolButton);
        add(furnitureToolButton);
    }
}
