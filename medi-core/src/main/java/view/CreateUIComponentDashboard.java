package view;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class CreateUIComponentDashboard {
    static class RoundedJTextField extends JTextField {
        private Shape shape;

        public RoundedJTextField(int size) {
            super(size);
            setBounds(520, 284, 190, 25);
            setBackground(new Color(0, 0, 0, 0));
            setOpaque(false);
            setMargin(new Insets(5, 20, 5, 20));
        }

        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();

            // Enable anti-aliasing for smooth rendering
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Draw background
            g2.setColor(new Color(0, 0, 0, 0)); // Transparent background
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 40, 40);

            super.paintComponent(g);
            g2.dispose();
        }

        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();

            // Enable anti-aliasing for smoother edges
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Set border color
            g2.setColor(Color.DARK_GRAY); // Example: Blue border

            // Set border thickness
            float borderWidth = 1.0f; // Adjust border thickness here
            g2.setStroke(new BasicStroke(borderWidth));

            // Draw rounded rectangle border
            g2.drawRoundRect(
                    (int) (borderWidth / 2),
                    (int) (borderWidth / 2),
                    getWidth() - (int) borderWidth,
                    getHeight() - (int) borderWidth,
                    20, 20
            );

            g2.dispose();
        }

        public boolean contains(int x, int y) {
            if (shape == null || !shape.getBounds().equals(getBounds())) {
                shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 40, 40);
            }
            return shape.contains(x, y);
        }
    }

    static class CustomButton extends JButton {

        private Color targetColor;
        private Color currentColor;
        private Timer colorTransitionTimer;

        // Constructor
        public CustomButton(String text) {
            super(text);
            setContentAreaFilled(false);  // Disable the default background
            setFocusPainted(false);  // Disable the focus border
            setBorderPainted(false);  // Disable the border
            setOpaque(false);  // Make the button transparent

            // Set initial background color and text color
            currentColor = new Color(255, 255, 255);  // White background by default
            targetColor = currentColor;

            // Add a mouse listener to handle hover effects
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {

                    targetColor = new Color(89, 226, 89);// Change color on hover (dark blue)
                    setForeground(Color.WHITE);  // Change text color to white
                    startColorTransition();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    targetColor = new Color(255, 255, 255);  // Revert to original color (white)
                    setForeground(Color.BLACK);  // Change text color to black
                    startColorTransition();
                }
            });
        }

        // Method to start the color transition using a Timer
        private void startColorTransition() {
            if (colorTransitionTimer != null && colorTransitionTimer.isRunning()) {
                colorTransitionTimer.stop();  // Stop the previous timer if it was running
            }

            colorTransitionTimer = new Timer(10, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Gradually change the color
                    int r = currentColor.getRed() + (targetColor.getRed() - currentColor.getRed()) / 10;
                    int g = currentColor.getGreen() + (targetColor.getGreen() - currentColor.getGreen()) / 10;
                    int b = currentColor.getBlue() + (targetColor.getBlue() - currentColor.getBlue()) / 10;

                    currentColor = new Color(r, g, b);

                    // Update the background color
                    setBackground(currentColor);

                    // Stop the timer when the color has reached the target color
                    if (currentColor.equals(targetColor)) {
                        colorTransitionTimer.stop();
                    }
                }
            });

            colorTransitionTimer.start();
        }

        // Override the paintComponent method to draw custom button shape and color
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;

            // Enable anti-aliasing for smooth drawing
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Set the color based on button state
            if (getModel().isArmed()) {
                g2d.setColor(new Color(1, 93, 139));  // Pressed color
            } else if (getModel().isRollover()) {
                g2d.setColor(currentColor);  // Smooth color (hover or transition)
            } else {
                g2d.setColor(currentColor);  // Default color
            }

            // Draw a rounded rectangle with smooth edges
            int arcSize = 15;  // The degree of roundness for the corners
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), arcSize, arcSize);  // Smooth corners

            // Set text color and font
            g2d.setColor(new Color(0, 0, 0));  // Black text
            g2d.setFont(new Font("Arial", Font.BOLD, 15));  // Set font
            FontMetrics metrics = g2d.getFontMetrics();

            // Calculate text position
            int x = (getWidth() - metrics.stringWidth(getText())) / 2;
            int y = (getHeight() + metrics.getAscent()) / 2;


            super.paintComponent(g);
        }
    }

    static class CustomTableCellRenderer extends DefaultTableCellRenderer {
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        // Get the default component
        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (row % 2 == 0) {
            cellComponent.setBackground(new Color(220, 240, 255));
        } else {
            cellComponent.setBackground(Color.WHITE);
        }

        // Highlight selected row
        if (isSelected) {
            cellComponent.setBackground(new Color(142, 210, 119));
            cellComponent.setForeground(Color.WHITE);
        } else {
            cellComponent.setForeground(Color.BLACK);
        }

        return cellComponent;
    }
}

    static class CustomTextArea extends JTextArea {
        public CustomTextArea(int rows, int columns) {
            super(rows, columns);

            // Set fixed size for the text area
            setPreferredSize(new Dimension(150, 100)); // Width: 300px, Height: 150px
            setMinimumSize(new Dimension(150, 100));
            setMaximumSize(new Dimension(150, 100));

            // Other properties
            setOpaque(false);
            setFont(new Font("Arial", Font.PLAIN, 14));
            setLineWrap(true);
            setWrapStyleWord(true);
            setMargin(new Insets(10, 10, 10, 10));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Draw rounded background
            g2.setColor(new Color(240, 240, 240));
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);

            super.paintComponent(g);
            g2.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Draw rounded border
            g2.setColor(Color.GRAY);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);

            g2.dispose();
        }
    }

    static class CustomRadioButton extends JRadioButton {
        private Color baseColor = new Color(236, 236, 236, 255);
        private Color hoverColor = new Color(157, 198, 232);
        private Color selectedColor = Color.BLACK;
        private Color borderColor = Color.DARK_GRAY;

        // Constructor
        public CustomRadioButton(String text) {
            super(text);
            setOpaque(false); // Make the button transparent
            setFocusPainted(false); // Remove focus painting
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            // Add mouse listeners for hover effects
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    baseColor = hoverColor; // Change to hover color
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    baseColor = new Color(236, 236, 236, 255); // Reset to default color
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Draw the button
            int diameter = 20; // Circle size
            int padding = 0; // Padding around the circle

            // Outer circle (border)
            g2.setColor(borderColor);
            g2.fillOval(padding, (getHeight() - diameter) / 2, diameter, diameter);

            // Inner circle (background)
            g2.setColor(baseColor);
            g2.fillOval(padding + 2, (getHeight() - diameter) / 2 + 2, diameter - 4, diameter - 4);

            // Draw selected state
            if (isSelected()) {
                g2.setColor(selectedColor);
                int innerDiameter = diameter - 8;
                g2.fillOval(padding + 4, (getHeight() - innerDiameter) / 2, innerDiameter, innerDiameter);
            }

            // Draw the text
            g2.setColor(getForeground());
            g2.setFont(getFont());
            FontMetrics metrics = g2.getFontMetrics();
            int textX = padding + diameter + 5; // Position text after the circle
            int textY = (getHeight() + metrics.getAscent() - metrics.getDescent()) / 2;
            g2.drawString(getText(), textX, textY);
        }
    }

    static class CustomComboBox extends JComboBox<String> {

        public CustomComboBox() {
            super();

            // Set a custom UI for the ComboBox
            setUI(new CustomComboBoxUI());
            setRenderer(new CustomComboBoxRenderer());

            // Optional: Adjust the size
            setPreferredSize(new Dimension(200, 30));
        }


        // Custom ComboBox UI for styling
        private static class CustomComboBoxUI extends BasicComboBoxUI {
            @Override
            protected JButton createArrowButton() {
                // Create a custom arrow button
                JButton button = new JButton("▼");
                button.setFont(new Font("Arial", Font.BOLD, 12));
                button.setFocusPainted(false);
                button.setBorderPainted(false);
                button.setContentAreaFilled(false);
                button.setForeground(new Color(100, 100, 100)); // Gray arrow color
                return button;
            }

            @Override
            public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(new Color(240, 240, 240)); // Light gray for the background
                g2d.fillRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, 20, 20); // Rounded corners
            }

            @Override
            protected void installDefaults() {
                super.installDefaults();
                comboBox.setOpaque(false);
                comboBox.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1)); // Border color
            }
        }

        // Custom Renderer for dropdown items
        private static class CustomComboBoxRenderer extends BasicComboBoxRenderer {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setOpaque(true);

                // Customize background and text color
                if (isSelected) {
                    label.setBackground(new Color(0, 119, 182)); // Blue for selection
                    label.setForeground(Color.WHITE); // White text for selected item
                } else {
                    label.setBackground(Color.WHITE); // White background for unselected items
                    label.setForeground(new Color(50, 50, 50)); // Dark gray text
                }

                // Optional: Padding for better spacing
                label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                return label;
            }
        }
    }
}
