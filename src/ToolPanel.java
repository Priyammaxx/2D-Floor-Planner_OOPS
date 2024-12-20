
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ToolPanel extends JPanel {
    private SketchApp app;
    private JPanel mainToolPanel; // holds room, furniture, door, window
    private JPanel optionsPanel; // holds extra options for above selected
    private CardLayout cardLayout;
    private FurnitureLoader furnitureLoader;
    JButton prevSelectedButton;

    public ToolPanel(SketchApp app) {
        this.app = app;

        // this is experimental, fix later !!
        furnitureLoader = FurnitureLoader.getInstance();
        
        setLayout(new BorderLayout());

        mainToolPanel = new JPanel(new GridLayout(0,1)); // single column
        
        JButton selectToolButton = new JButton("Select Tool");
        JButton roomToolButton = new JButton("Room Tool");        
        JButton furnitureToolButton = new JButton("Furniture Tool");
        JButton doorToolButton = new JButton("Door Tool");
        JButton windowToolButton = new JButton("Window Tool");

        ArrayList<JButton> mainButtons = new ArrayList<>();
        mainButtons.add(selectToolButton);
        mainButtons.add(roomToolButton);
        mainButtons.add(furnitureToolButton);
        mainButtons.add(doorToolButton);
        mainButtons.add(windowToolButton);

        for (JButton button : mainButtons) {
            button.setFocusable(false);
            button.setBackground(new Color(226,235,245));
        }
        selectToolButton.setFocusable(false);
        roomToolButton.setFocusable(false);
        furnitureToolButton.setFocusable(false);
        doorToolButton.setFocusable(false);
        windowToolButton.setFocusable(false);
        
        selectToolButton.addActionListener(e -> {
            changeButtorColor(selectToolButton);
            app.setDrawingTool(new SelectTool());
            optionsPanel.setVisible(false);
        });
        roomToolButton.addActionListener(e -> {
            changeButtorColor(roomToolButton);
            app.setDrawingTool(new RoomTool());
            showOptions("Rooms");
        });
        furnitureToolButton.addActionListener(e -> {
            changeButtorColor(furnitureToolButton);
            app.setDrawingTool(new FurnitureTool());
            showOptions("Furniture");
        });
        doorToolButton.addActionListener(e -> {
            changeButtorColor(doorToolButton);
            app.setDrawingTool(new DoorTool());
            optionsPanel.setVisible(false);
        });
        windowToolButton.addActionListener(e -> {
            changeButtorColor(windowToolButton);
            app.setDrawingTool(new WindowTool());
            optionsPanel.setVisible(false);
        });

        //  Main Buttons go here
        for (JButton button : mainButtons) {
            mainToolPanel.add(button);
        }
            
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
        // how does this if statement work
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

        // room button colors
        bedRoomButton.setBackground(new Color(144,245,144));
        bathRoomButton.setBackground(new Color(144,145,244));
        kitchenRoomButton.setBackground(new Color(244,144,145));
        drawingRoomButton.setBackground(new Color(244,223,144));

        roomOptionsPanel.add(bedRoomButton);
        roomOptionsPanel.add(bathRoomButton);
        roomOptionsPanel.add(kitchenRoomButton);
        roomOptionsPanel.add(drawingRoomButton);
        
        // ------ Furniture ----------
        JPanel furnitureOptionsPanel = new JPanel(new GridLayout(0,1));
        JButton bedButton = new JButton("Bed");
        JButton diningTableButton = new JButton("Dining Table");
        JButton kitchenSinkButton = new JButton("Kitchen Sink");
        JButton showerButton = new JButton("Shower");
        JButton stoveButton = new JButton("Stove");
        JButton toiletButton = new JButton("Toilet");
        JButton washbasinButton = new JButton("Washbasin");

        bedButton.addActionListener(e -> {
            if (app.getDrawingTool() instanceof FurnitureTool furnitureTool) {
                furnitureTool.setImageIndex(0);
            }
        });
        diningTableButton.addActionListener(e -> {
            if (app.getDrawingTool() instanceof FurnitureTool furnitureTool) {
                furnitureTool.setImageIndex(1);
            }
        });
        kitchenSinkButton.addActionListener(e -> {
            if (app.getDrawingTool() instanceof FurnitureTool furnitureTool) {
                furnitureTool.setImageIndex(2);
            }
        });
        stoveButton.addActionListener(e -> {
            if (app.getDrawingTool() instanceof FurnitureTool furnitureTool) {
                furnitureTool.setImageIndex(3);
            }
        });
        showerButton.addActionListener(e -> {
            if (app.getDrawingTool() instanceof FurnitureTool furnitureTool) {
                furnitureTool.setImageIndex(4);
            }
        });
        toiletButton.addActionListener(e -> {
            if (app.getDrawingTool() instanceof FurnitureTool furnitureTool) {
                furnitureTool.setImageIndex(5);
            }
        });
        washbasinButton.addActionListener(e -> {
            if (app.getDrawingTool() instanceof FurnitureTool furnitureTool) {
                furnitureTool.setImageIndex(6);
            }
        });
        
        furnitureOptionsPanel.add(bedButton);
        furnitureOptionsPanel.add(diningTableButton);
        furnitureOptionsPanel.add(kitchenSinkButton);
        furnitureOptionsPanel.add(stoveButton);
        furnitureOptionsPanel.add(showerButton);
        furnitureOptionsPanel.add(toiletButton);
        furnitureOptionsPanel.add(washbasinButton);
        
        // adding to options panel
        optionsPanel.add(roomOptionsPanel, "Rooms");
        optionsPanel.add(furnitureOptionsPanel, "Furniture");
        
        // add main tool panel and options panel to Tool Panel
        add(mainToolPanel, BorderLayout.WEST);
        add(optionsPanel, BorderLayout.CENTER);
    }
    
    private void showOptions(String optionsKey) {
        cardLayout.show(optionsPanel, optionsKey); // show specific options panel
        optionsPanel.setVisible(true);
    }

    private void changeButtorColor(JButton button) {
        if (prevSelectedButton != null) {
            prevSelectedButton.setBackground(new Color(226,235,245));
            prevSelectedButton = button;
            prevSelectedButton.setBackground(new Color(206,222,237));
        }
    }
}
