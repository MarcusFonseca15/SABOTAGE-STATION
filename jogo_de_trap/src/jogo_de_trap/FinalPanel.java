package jogo_de_trap;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FinalPanel extends JPanel {

    private JFrame frame;
    boolean win;
    boolean isHover = false;
    JButton actionButton;

    FinalPanel(JFrame frame, boolean win) {
        this.frame = frame;
        this.win = win;
        this.setLayout(null);

        // background
        String backgroundPath = win ? "/assets/telas_e_botoes/VictoryBG.jpg" : "/assets/telas_e_botoes/GameOverBG.jpg";
        ImageIcon icon = new ImageIcon(getClass().getResource(backgroundPath));
        Image bg = icon.getImage();

        // botão
        ImageIcon resizedIcon = new ImageIcon(
                new ImageIcon(getClass().getResource(
                        win ? "/assets/telas_e_botoes/btnPlayAgain.png" : "/assets/telas_e_botoes/btnTryAgain.png"))
                        .getImage().getScaledInstance(180, 70, Image.SCALE_SMOOTH));
        this.actionButton = new JButton(resizedIcon);
        actionButton.setOpaque(false);
        actionButton.setContentAreaFilled(false);
        actionButton.setBorderPainted(false);
        actionButton.setFocusPainted(false);
        actionButton.setBounds(300, 500, 180, 70);

        setupButton(actionButton);
        setupHoverEffect();
    } // <-- fim do construtor

    private void setupHoverEffect() {

        actionButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                actionButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                isHover = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                actionButton.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                isHover = false;
                repaint();
            }
        });
    } // setupHoverEffect

    private void setupButton(JButton actionButton) {
        // x, y, largura, altura

        actionButton.addActionListener(e -> {
            if (win) {
                frame.getContentPane().removeAll();
                StartPanel startPanel = new StartPanel(frame);
                frame.add(startPanel);
                frame.revalidate();
                frame.repaint();
            } else {
                frame.getContentPane().removeAll();
                GamePanel gamePanel = new GamePanel((GameFrame) frame);
                // Forçar início na fase 1
                gamePanel.currentLevel = 1;
                frame.add(gamePanel);
                frame.revalidate();
                frame.repaint();
                gamePanel.requestFocusInWindow();
            }
        });

        this.add(actionButton);
    } // setupbutton

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // background
        String backgroundPath = win ? "/assets/telas_e_botoes/VictoryBG.jpg" : "/assets/telas_e_botoes/GameOverBG.jpg";
        ImageIcon icon = new ImageIcon(getClass().getResource(backgroundPath));
        Image bg = icon.getImage();

        int imgWidth = bg.getWidth(null);
        int imgHeight = bg.getHeight(null);

        int panelWidth = this.getWidth();

        // fixar o topo da img no topo do painel
        double scaleFactor = (double) panelWidth / imgWidth;
        int scaleAltura = (int) (imgHeight * scaleFactor) - 110;

        g.drawImage(bg, 0, 0, panelWidth, scaleAltura, null);

        // Outline de botão
        if (isHover) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(3));
            // botão é (300, 500, 180, 70);
            int m = 1; // margem
            g2d.drawRect(300 - m, 500 - m, 180 + (2 * m), 70 + (2 * m)); // 4px de margem
            g2d.dispose();
        }
    } // fim paint component

}
