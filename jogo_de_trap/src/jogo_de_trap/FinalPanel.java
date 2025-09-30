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

    FinalPanel(JFrame frame, boolean win) {
        this.frame = frame;
        this.win = win;
        this.setLayout(null);

        /*
         * ImageIcon resizedIcon = new ImageIcon(
         * new ImageIcon(getClass().getResource("/assets/btnStart.png"))
         * .getImage().getScaledInstance(180, 70, Image.SCALE_SMOOTH));
         * 
         * JButton startButton = new JButton(resizedIcon);
         * 
         * startButton.setOpaque(false);
         * startButton.setContentAreaFilled(false);
         * startButton.setBorderPainted(false);
         * startButton.setFocusPainted(false);
         * 
         * startButton.setBounds(300, 450, 180, 70);
         * 
         * // 1. ActionListener pra iniciar o jogo
         * startButton.addActionListener(new ActionListener() {
         * 
         * @Override
         * public void actionPerformed(ActionEvent e) {
         * iniciarJogo();
         * }
         * });
         * 
         * // 2. MouseAdapter pra mudar o cursor
         * startButton.addMouseListener(new MouseAdapter() {
         * 
         * @Override
         * public void mouseEntered(MouseEvent e) {
         * startButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
         * }
         * 
         * @Override
         * public void mouseExited(MouseEvent e) {
         * startButton.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
         * }
         * });
         * 
         * this.add(startButton);
         */

        setupButton();
    } // <--- Fechamento do construtor

    private void setupButton() {
        String buttonText = win ? "Jogar de Novo" : "Tente Novamente";
        String backgroundPath = win ? "/assets/VictoryBG.jpg" : "/assets/GameOverBG.jpg";
        ImageIcon icon = new ImageIcon(getClass().getResource(backgroundPath));
        Image bg = icon.getImage();

        JButton actionButton = new JButton(buttonText);
        actionButton.setBounds(300, 500, 180, 70); // x, y, largura, altura

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
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        String backgroundPath = win ? "/assets/VictoryBG.jpg" : "/assets/GameOverBG.jpg";
        ImageIcon icon = new ImageIcon(getClass().getResource(backgroundPath));
        Image bg = icon.getImage();

        int imgWidth = bg.getWidth(null);
        int imgHeight = bg.getHeight(null);

        int panelWidth = this.getWidth();

        // fixar o topo da img no topo do painel
        double scaleFactor = (double) panelWidth / imgWidth;
        int scaleAltura = (int) (imgHeight * scaleFactor) - 110;

        g.drawImage(bg, 0, 0, panelWidth, scaleAltura, null);
    }
}
