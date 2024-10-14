import java.awt.*;
import javax.swing.*;

public class BottomBar {
    private JPanel bottomBar;
    private JToggleButton gridToggle;
    private JToggleButton snapToggle;

    // Constructor for BottomBar
    public BottomBar(SketchPanel sketchPanel, boolean gridEnabled, boolean snapEnabled,JLabel statusLabel) {
        bottomBar = new JPanel();
        bottomBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        // Toggle Grid Button
        gridToggle = new JToggleButton("Toggle Grid", gridEnabled);
        gridToggle.addActionListener(e -> {
            boolean selected = gridToggle.isSelected();  // Grid state
            sketchPanel.setGridEnabled( selected );
            statusLabel.setText("Grid " + (selected ? "Enabled" : "Disabled"));
        });

        // Toggle Snap Button
        snapToggle = new JToggleButton("Toggle Snap", snapEnabled);
        snapToggle.addActionListener(e -> {
            boolean selected = snapToggle.isSelected();  // Snap state
            sketchPanel.setSnapEnabled(selected);
            statusLabel.setText("Snap " + (selected ? "Enabled" : "Disabled"));
        });

        // Add toggle buttons to the bottom bar
        bottomBar.add(gridToggle);
        bottomBar.add(snapToggle);
        bottomBar.add(statusLabel);
    }

    // Method to return bottom bar to Main Sketching frame
    public JPanel getBottomBar() {
        return bottomBar;
    }
}
