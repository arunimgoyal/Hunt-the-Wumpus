import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;


public class HexagonalLayout implements LayoutManager {
    // PROPERTIES
    private final int numCells;  // Number of hexagonal cells
    private final Map<Component, Rectangle> componentMap = new HashMap<>();

    // CONSTRUCTOR
    public HexagonalLayout(int numCells) {
        this.numCells = numCells;
    }

    // METHODS
    @Override
    public void addLayoutComponent(String name, Component comp) {
        // Not used
    }

    @Override
    public void removeLayoutComponent(Component comp) {
        componentMap.remove(comp);
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return calculateSize(parent);
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return calculateSize(parent);
    }

    @Override
    public void layoutContainer(Container parent) {
        synchronized (parent.getTreeLock()) {
            int n = parent.getComponentCount();
            if (n == 0) return;

            int parentWidth = 475; //parent.getWidth();
            int parentHeight = 475; //parent.getHeight();

            int columns = 6;  // Number of columns per row
            int rows = (int) Math.ceil((double) n / columns);

            // Calculate the size of each hexagon to fit the available space
            int hexHeight = parentHeight / rows;
            int hexWidth = (int) (hexHeight / Math.sqrt(3) * 2);
            int hexSize = Math.min(hexWidth, hexHeight);

            int yOffset = (int) (hexSize * Math.sqrt(3) / 2);

            // Adjust horizontal and vertical spacing
            int horizontalSpacing = hexWidth * 3 / 4; // Adjust as needed
            int verticalSpacing = hexHeight; // Adjust as needed

            // Offset to center the hexagonal grid within the parent container
            int startX = (parentWidth - columns * horizontalSpacing + hexWidth / 2) / 2;
            int startY = (parentHeight - rows * verticalSpacing + hexHeight / 2) / 2;

            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < columns; col++) {
                    int index = row * columns + col;
                    if (index >= n) break;

                    Component c = parent.getComponent(index);

                    // Calculate x and y positions
                    int x = startX + col * horizontalSpacing;
                    int y = startY + row * verticalSpacing;

                    // Offset y for odd columns
                    if (col % 2 == 1) {
                        y += yOffset;
                    }

                    // Adjust y for specific columns (1, 3, 5)
                    if (col == 1 || col == 3 || col == 5) {
                        y -= yOffset / 2; // Shift up by half the vertical spacing
                    }

                    componentMap.put(c, new Rectangle(x, y, hexSize, hexSize));
                    c.setBounds(x, y, hexSize, hexSize);
                }
            }
        }
    }


    private Dimension calculateSize(Container parent) {
        int parentWidth = parent.getWidth();
        int parentHeight = parent.getHeight();

        int columns = (int) Math.ceil(Math.sqrt(numCells));
        int rows = (int) Math.ceil((double) numCells / columns);

        int hexHeight = parentHeight / rows;
        int hexWidth = (int) (hexHeight / Math.sqrt(3) * 2);
        int hexSize = Math.min(hexWidth, hexHeight);

        int width = columns * hexSize + (hexSize / 2);
        int height = rows * (int) (hexSize * Math.sqrt(3) / 2) + (int) (hexSize * Math.sqrt(3) / 2);

        return new Dimension(width, height);
    }
}

