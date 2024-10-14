
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

public class SketchApp extends JFrame{
    private SketchPanel sketchPanel;
    private ToolPanel toolPanel;
    private JButton rectButton; // not implemented
    private JButton clearButton;
    private JButton undoRect;
    boolean snapEnabled = true;
    boolean gridEnabled = true;
    public JLabel statusLabel;

    public SketchApp() {
        setTitle("2D Floor Planner");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.LIGHT_GRAY);


        //Creating label before putting in BottomBar
        statusLabel = new JLabel("Status: Ready");
        // bottom panel with toggle buttons
        //createBottomBar();
        



        // ----------- Initialising Area ---------------
        // the sidebat panel for switching between tools 
        toolPanel = new ToolPanel(this);
        // pass in RectTool, abstract class DrawingTool handles the implementation
        sketchPanel = new SketchPanel(new RectTool(),statusLabel); 
        

        BottomBar bottomBar = new BottomBar(sketchPanel, gridEnabled, snapEnabled,statusLabel);
        add(bottomBar.getBottomBar(), BorderLayout.SOUTH);
        
        // ----------- Adding Area ---------------
        add(toolPanel, BorderLayout.WEST);
        add(sketchPanel, BorderLayout.CENTER); // display main sketch area in center

        // Adding clear canvas button
        // clearButton = new JButton("Clear Canvas");
        // clearButton.addActionListener(e -> sketchPanel.clearCanvas()); // how do lambda functions work??
        // add(clearButton, BorderLayout.SOUTH);

        // undoRect = new JButton("Undo Last Rectangle");
        // undoRect.addActionListener(e -> sketchPanel.undoLastRect());
        // add(undoRect, BorderLayout.EAST);

        // top menu bar
        createMenuBar();
        
        

        setVisible(true);

    }

    // ToDo : make menuBar and BottomBar external classes
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        
        // idea : try adding Menu item directly to menu bar
        JMenuItem newFile = new JMenuItem("New");
        JMenuItem openFile = new JMenuItem("Open");
        JMenuItem saveFile = new JMenuItem("Save");
        JMenuItem closeFile = new JMenuItem("Close");

        // Functionality of these buttons comes here
        // 
        // 
        // 
        // will be implemented shortly

        fileMenu.add(newFile);
        fileMenu.add(openFile);
        fileMenu.add(saveFile);
        fileMenu.add(closeFile);

        JMenu editMenu = new JMenu("Edit");
        JMenuItem undoItem = new JMenuItem("Undo");
        // undo function implemented here
        // 
        // will be implemented shortly

        editMenu.add(undoItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        setJMenuBar(menuBar);
    }

    // method to change drawing tool
    // right now does nothing? Is it being called somewhere
    public void setDrawingTool(DrawingTool tool) {
        sketchPanel.setDrawingTool(tool);
    }

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(SketchApp::new);
    }
}
