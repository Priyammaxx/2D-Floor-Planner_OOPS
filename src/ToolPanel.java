
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ToolPanel extends JPanel {
    private SketchApp app;
    private JPanel mainToolPanel; // holds room, furniture, door, window
    private JPanel optionsPanel; // holds extra options for above selected
    private CardLayout cardLayout;

    public ToolPanel(SketchApp app) {
        this.app = app;
        setLayout(new BorderLayout());

        mainToolPanel = new JPanel(new GridLayout(0,1)); // single column
        
        JButton selectToolButton = new JButton("Select Tool");
        JButton roomToolButton = new JButton("Room Tool");        
        JButton furnitureToolButton = new JButton("Furniture Tool");
        JButton doorToolButton = new JButton("Door Tool");
        JButton windowToolButton = new JButton("Window Tool");
        
        selectToolButton.addActionListener(e -> {
            app.setDrawingTool(new SelectTool());
            optionsPanel.setVisible(false);
        });
        roomToolButton.addActionListener(e -> {
            app.setDrawingTool(new RoomTool());
            showOptions("Rooms");
        });
        furnitureToolButton.addActionListener(e -> {
            app.setDrawingTool(new FurnitureTool());
            showOptions("Furniture");
        });
        doorToolButton.addActionListener(e -> {
            app.setDrawingTool(new DoorTool());
            optionsPanel.setVisible(false);
        });
        windowToolButton.addActionListener(e -> {
            app.setDrawingTool(new WindowTool());
            optionsPanel.setVisible(false);
        });

        //  Main Buttons go here
        mainToolPanel.add(selectToolButton);
        mainToolPanel.add(roomToolButton);
        mainToolPanel.add(furnitureToolButton);
        mainToolPanel.add(doorToolButton);
        mainToolPanel.add(windowToolButton);
            
        // Subsections for visible options by each main tool button -----------
        cardLayout = new CardLayout();
        optionsPanel = new JPanel(cardLayout);
        optionsPanel.setVisible(false);

        // -------Rooms-----------
        JPanel roomOptionsPanel = new JPanel(new GridLayout(0,1));
        JButton bedRoomButton = new JButton("Bedroom"); // green
        JButton bathRoomButton = new JButton("Bathroom"); // blue
        JButton kitchenRoomButton = new JButton("Kitchen"); // red
        JButton drawingRoomButton = new JButton("Drawing Room"); // yellow or orange
        
        // color functionality to different types of rooms
        bedRoomButton.addActionListener(e -> {
            if (app.getDrawingTool() instanceof RoomTool roomTool) {
                    roomTool.setColor(Color.green);
            }
        });
        bathRoomButton.addActionListener(e -> {
            if (app.getDrawingTool() instanceof RoomTool roomTool) {
                    roomTool.setColor(Color.blue);
            }
        });
        kitchenRoomButton.addActionListener(e -> {
            if (app.getDrawingTool() instanceof RoomTool roomTool) {
                    roomTool.setColor(Color.red);
            }
        });
        drawingRoomButton.addActionListener(e -> {
            if (app.getDrawingTool() instanceof RoomTool roomTool) {
                    roomTool.setColor(Color.orange);
            }
        });
        bedRoomButton.setBackground(Color.green);
        bathRoomButton.setBackground(Color.blue);

        roomOptionsPanel.add(bedRoomButton);
        roomOptionsPanel.add(bathRoomButton);
        roomOptionsPanel.add(kitchenRoomButton);
        roomOptionsPanel.add(drawingRoomButton);
        
        // Furniture
        JPanel furniturOptionsPanel = new JPanel(new GridLayout(0,1));
        JButton type1 = new JButton("type 1");
        JButton type2 = new JButton("type 2");
        JButton type3 = new JButton("type 3");
        
        furniturOptionsPanel.add(type1);
        furniturOptionsPanel.add(type2);
        furniturOptionsPanel.add(type3);
        
        // adding to options panel
        optionsPanel.add(roomOptionsPanel, "Rooms");
        optionsPanel.add(furniturOptionsPanel, "Furniture");
        
        // add main tool panel and options panel to Tool Panel
        add(mainToolPanel, BorderLayout.WEST);
        add(optionsPanel, BorderLayout.CENTER);
    }
    
    private void showOptions(String optionsKey) {
        cardLayout.show(optionsPanel, optionsKey); // show specific options panel
        optionsPanel.setVisible(true);
    }
}
