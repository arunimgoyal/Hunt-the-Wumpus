import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;

public class HexagonButton extends JButton {
    // PROPERTIES
    private Shape hexagon;

    // CONSTRUCTOR
    public HexagonButton(String label) {
        super(label);
        // Set the content area to be non-rectangular
        setContentAreaFilled(false);
        // Set focus painting to false for a cleaner look
        setFocusPainted(false);
        // Set border painting to false
        setBorderPainted(false);
    }

    // METHODS
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        // Enable anti-aliasing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Create hexagon shape based on current size
        hexagon = createHexagon(getWidth(), getHeight());

        // Fill the hexagon
        if (getModel().isArmed()) {
            g2.setColor(Color.LIGHT_GRAY);
        } else {
            g2.setColor(getBackground());
        }
        g2.fill(hexagon);

        // Draw the border
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2.0f));
        g2.draw(hexagon);

        // Draw the label
        g2.setColor(getForeground());
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(getText());
        int textHeight = fm.getAscent();
        g2.drawString(getText(), getWidth() / 2 - textWidth / 2, getHeight() / 2 + textHeight / 4);

        g2.dispose();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(100, 100);  // Return a default size if necessary
    }

    @Override
    public boolean contains(int x, int y) {
        if (hexagon == null) {
            hexagon = createHexagon(getWidth(), getHeight());
        }
        return hexagon.contains(x, y);
    }

    private Shape createHexagon(int width, int height) {
        double radius = Math.min(width, height) / 2.0;
        double centerX = width / 2.0;
        double centerY = height / 2.0;

        Path2D.Double hexagon = new Path2D.Double();

        // Adjust the angles as needed for custom vertex positions

        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians(60 * i);  // Convert degrees to radians
            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);
            if (i == 0) {
                hexagon.moveTo(x, y);
            } else {
                hexagon.lineTo(x, y);
            }
        }

        hexagon.closePath();
        return hexagon;
    }

}

