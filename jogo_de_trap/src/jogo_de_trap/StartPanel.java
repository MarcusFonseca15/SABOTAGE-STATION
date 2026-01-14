package jogo_de_trap;

import javax.swing.*;

//import org.w3c.dom.events.MouseEvent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StartPanel extends JPanel {
    private JFrame frame;
    boolean isHover = false;

    public StartPanel(JFrame frame) {
        this.frame = frame;
        this.setLayout(null);

        ImageIcon resizedIcon = new ImageIcon(
                new ImageIcon(getClass().getResource("/assets/telas_e_botoes/btnStart.png"))
                        .getImage().getScaledInstance(180, 70, Image.SCALE_SMOOTH));

        // Apertar espaço também para inciar
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_SPACE) {
                    iniciarJogo();
                }
            }
        });
        // Apertar no botão também pra iniciar
        JButton startButton = new JButton(resizedIcon);

        startButton.setOpaque(false);
        startButton.setContentAreaFilled(false);
        startButton.setBorderPainted(false);
        startButton.setFocusPainted(false);

        startButton.setBounds(300, 450, 180, 70);

        // 1. ActionListener pra iniciar o jogo
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarJogo();
            }
        });

        // 2. MouseAdapter pra mudar o cursor
        startButton.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                startButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                isHover = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                startButton.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                isHover = false;
                repaint();
            }
        });

        this.add(startButton);
    } // <--- Fechamento do construtor

    private void iniciarJogo() {
        frame.getContentPane().removeAll();
        GamePanel gamePanel = new GamePanel((GameFrame) frame);
        frame.add(gamePanel);
        frame.revalidate();
        frame.repaint();

        gamePanel.requestFocusInWindow();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        ImageIcon icon = new ImageIcon(getClass().getResource("/assets/telas_e_botoes/StartBG.jpg"));
        Image bg = icon.getImage();

        int imgWidth = bg.getWidth(null);
        int imgHeight = bg.getHeight(null);

        int panelWidth = this.getWidth();

        // fixar o topo da img no topo do painel
        double scaleFactor = (double) panelWidth / imgWidth;
        int scaleAltura = (int) (imgHeight * scaleFactor) - 110;

        g.drawImage(bg, 0, 0, panelWidth, scaleAltura, null);

        // outline do botão
        // Outline de botão
        if (isHover) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(3));
            // botão é (300, 450, 180, 70)
            int m = 1; // margem
            g2d.drawRect(300 - m, 450 - m, 180 + (2 * m), 70 + (2 * m)); // 4px de margem
            g2d.dispose();
        }
    }

}