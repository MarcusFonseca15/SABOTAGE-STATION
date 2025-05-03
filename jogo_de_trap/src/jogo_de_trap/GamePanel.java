package jogo_de_trap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import levelGroup.Level1;
import levelGroup.Level2;
import levelGroup.Level3;

public class GamePanel extends JPanel implements ActionListener {

    private final int LARGURA = 800;
    private final int ALTURA = 600;

    private Timer timer;
    private Player player;
    private Level level;

    private int currentLevel = 1;
    private final int maxLevels = 3; // total de níveis disponíveis

    public GamePanel() {
        this.setPreferredSize(new Dimension(LARGURA, ALTURA));
        this.setBackground(Color.CYAN);
        this.setFocusable(true);

        player = new Player(100, 500);

        loadLevel(currentLevel);

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                player.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                player.keyReleased(e);
            }
        });

        timer = new Timer(16, this); // ~60 fps
        timer.start();
    }

    private void loadLevel(int number) {
        switch (number) {
            case 1 -> level = new Level1();
            case 2 -> level = new Level2();
            case 3 -> level = new Level3();
            default -> {
                System.out.println("Level inválido!");
                System.exit(0);
            }
        }
        player.reset();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        level.draw(g);
        player.draw(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        player.update();
        level.checkPlatformCollision(player);

        if (level.checkTrapCollision(player)) {
            System.out.println("Você caiu em uma armadilha! Resetando...");
            player.reset();
        }

        // Avançar para o próximo level
        if (player.x + player.width >= LARGURA) {
            currentLevel++;
            if (currentLevel <= maxLevels) {
                loadLevel(currentLevel);
                System.out.println("Avançando para o nível " + currentLevel);
            } else {
                System.out.println("Parabéns! Você terminou o jogo!");
                timer.stop(); // Para o jogo
                JOptionPane.showMessageDialog(this, "Você venceu todos os níveis!");
                System.exit(0);
            }
        }

        repaint();
    }
}
