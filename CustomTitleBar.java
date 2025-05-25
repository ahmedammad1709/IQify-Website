package bukc.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomTitleBar extends JPanel {

    private int height = 30;
    private JLabel maximizeIconLabel;
    private boolean isMaximized = false;
    private String urlPrefix = "/images/";

    public CustomTitleBar(JFrame frame) {

        setLayout(new BorderLayout());
        setBackground(new Color(17, 24, 40)); // Match app color
        setPreferredSize(new Dimension(frame.getWidth(), height)); // Set height to 20

        // Title with Logo
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        titlePanel.setOpaque(false);
        titlePanel.setPreferredSize(new Dimension(300, height));

        // Logo
        JLabel logoLabel = new JLabel();
        java.net.URL logoURL = getClass().getResource(urlPrefix + "icon.png"); // Make sure this file exists
        if (logoURL != null) {
            int iconSize = height - 8; // a bit smaller than bar height
            ImageIcon logoIcon = new ImageIcon(logoURL);
            Image scaled = logoIcon.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
            logoLabel.setIcon(new ImageIcon(scaled));
        } else {
            System.err.println("Logo image not found!");
        }
        logoLabel.setPreferredSize(new Dimension(height, height));

        // Title
        JLabel titleLabel = new JLabel("AI Student Assistant");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Roboto", Font.PLAIN, 12));
        titleLabel.setVerticalAlignment(SwingConstants.CENTER);

        titlePanel.add(logoLabel);
        titlePanel.add(titleLabel);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setPreferredSize(new Dimension((height + 20) * 3, height));

        // Close
        JPanel closePanel = createIconButton(urlPrefix + "close.png", () -> System.exit(0));

        // Maximize panel with dynamic icon handling
        maximizeIconLabel = new JLabel();
        JPanel maximizePanel = createDynamicIconButton(maximizeIconLabel, () -> {
            int state = frame.getExtendedState();
            if (state == JFrame.MAXIMIZED_BOTH) {
                frame.setExtendedState(JFrame.NORMAL);
                isMaximized = false;
                updateMaximizeIcon(urlPrefix + "maximize.png");
            } else {
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                isMaximized = true;
                updateMaximizeIcon(urlPrefix + "restore.png");
            }
        });
        isMaximized = frame.getExtendedState() == JFrame.MAXIMIZED_BOTH;
        updateMaximizeIcon(isMaximized ? urlPrefix + "restore.png" : urlPrefix + "maximize.png");

        // Minimize
        JPanel minimizePanel = createIconButton(urlPrefix + "minimize.png", () -> frame.setState(JFrame.ICONIFIED));

        // Add in reverse order: minimize → maximize → close
        buttonPanel.add(minimizePanel);
        buttonPanel.add(maximizePanel);
        buttonPanel.add(closePanel);

        add(titlePanel, BorderLayout.WEST);
        add(buttonPanel, BorderLayout.EAST);

        // Make the title bar draggable
        MouseAdapter dragger = new MouseAdapter() {
            Point initialClick;

            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
            }

            public void mouseDragged(MouseEvent e) {
                Point location = frame.getLocation();
                frame.setLocation(
                        location.x + e.getX() - initialClick.x,
                        location.y + e.getY() - initialClick.y
                );

                if (isMaximized) {
                    frame.setExtendedState(JFrame.NORMAL);
                    isMaximized = false;
                    updateMaximizeIcon(urlPrefix + "maximize.png");
                }
            }
        };

        addMouseListener(dragger);
        addMouseMotionListener(dragger);
    }

    private JPanel createIconButton(String iconPath, Runnable onClick) {
        int width = height + 20;

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(width, height));
        panel.setBackground(new Color(17, 24, 40));
        panel.setLayout(new BorderLayout());

        JLabel iconLabel = new JLabel();
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabel.setVerticalAlignment(SwingConstants.CENTER);
        iconLabel.setPreferredSize(new Dimension(width, height)); // Force full fill for proper centering

        java.net.URL imgURL = getClass().getResource(iconPath);
        if (imgURL != null) {
            int iconSize = height - 16; // Adjust based on padding
            ImageIcon rawIcon = new ImageIcon(imgURL);
            Image scaledImg = rawIcon.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
            iconLabel.setIcon(new ImageIcon(scaledImg));
        } else {
            System.err.println("Image not found: " + iconPath);
        }

        panel.add(iconLabel, BorderLayout.CENTER);

        panel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                panel.setBackground(new Color(32, 41, 56));
            }

            public void mouseExited(MouseEvent e) {
                panel.setBackground(new Color(17, 24, 40));
            }

            public void mouseClicked(MouseEvent e) {
                onClick.run();
            }
        });

        return panel;
    }

    private void updateMaximizeIcon(String iconPath) {
        java.net.URL imgURL = getClass().getResource(iconPath);
        if (imgURL != null) {
            int iconSize = height - 16;
            ImageIcon rawIcon = new ImageIcon(imgURL);
            Image scaledImg = rawIcon.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
            maximizeIconLabel.setIcon(new ImageIcon(scaledImg));
        } else {
            System.err.println("Icon not found: " + iconPath);
        }
    }

    private JPanel createDynamicIconButton(JLabel iconLabel, Runnable onClick) {
        int width = height + 20;

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(width, height));
        panel.setBackground(new Color(17, 24, 40));
        panel.setLayout(new BorderLayout());

        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabel.setVerticalAlignment(SwingConstants.CENTER);
        iconLabel.setPreferredSize(new Dimension(width, height));
        panel.add(iconLabel, BorderLayout.CENTER);

        panel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                panel.setBackground(new Color(32, 41, 56));
            }

            public void mouseExited(MouseEvent e) {
                panel.setBackground(new Color(17, 24, 40));
            }

            public void mouseClicked(MouseEvent e) {
                onClick.run();
            }

        });

        return panel;
    }

}
