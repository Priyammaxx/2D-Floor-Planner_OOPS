
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ToolPanel extends JPanel {
    private SketchApp app;

    public ToolPanel(SketchApp app) {
        this.app = app;
        setLayout(new GridLayout(0,1)); // set single column layout

        JButton rectToolButton = new JButton("Room Tool"); // rename later to a Geneal Room Button
        rectToolButton.addActionListener(e -> app.setDrawingTool(new RoomTool()));

        JButton furnitureToolButton = new JButton("Furniture Tool");
        furnitureToolButton.addActionListener(e -> app.setDrawingTool(new FurnitureTool()));
        
        JButton selectToolButton = new JButton("Select Tool");
        selectToolButton.addActionListener(e -> app.setDrawingTool(new SelectTool()));
        


        add(rectToolButton);
        add(furnitureToolButton);
        add(selectToolButton);
    }
}
