package jogo_de_trap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import levelGroup.Level01;
import levelGroup.Level10;
import levelGroup.Level05;
import levelGroup.Level06;
import levelGroup.Level07;
import levelGroup.Level08;
import levelGroup.Level09;
import levelGroup.Level02;
import levelGroup.Level03;
import levelGroup.Level04;

public class GamePanel extends JPanel implements ActionListener {

    private final int LARGURA = 800;
    private final int ALTURA = 600;

    private Timer timer;
    private Player player;
    private Level level;
    private Image backgroundImage;

    private int currentLevel = 6;
    private final int maxLevels = 10;

    public GamePanel() {
        this.setPreferredSize(new Dimension(LARGURA, ALTURA));
        this.setFocusable(true);

        // Carregar imagem de fundo corretamente
        backgroundImage = new ImageIcon(getClass().getResource("/assets/background2.png")).getImage();

        Gravity gravityInit = new Gravity(1.0);
        player = new Player(100, 500, gravityInit);

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

        timer = new Timer(16, this);
        setLayout(null);
        timer.start();
    }

    private void loadLevel(int number) {
        switch (number) {
            case 1 -> level = new Level01(player);
            case 2 -> level = new Level02(player);
            case 3 -> level = new Level03(player);
            case 4 -> level = new Level04(player);
            case 5 -> level = new Level05(player);
            case 6 -> level = new Level06(player);
            case 7 -> level = new Level07(player);
            case 8 -> level = new Level08(player);
            case 9 -> level = new Level09(player);
            case 10 -> level = new Level10(player);
            default -> {
                System.out.println("Level inválido!");
                System.exit(0);
            }
        }
        player.reset();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Desenhar imagem de fundo
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }

        level.draw(g);
        player.draw(g);

        // TITULO
        g.setColor(level.getCorTitle());
        g.setFont(new Font("Arial", Font.BOLD, level.getSizeTitle()));
        g.drawString(level.getTitulo(), level.getTitleX(), level.getTitleY());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        player.update();
        level.checkPlatformCollision(player);
        level.checkPistaoCollision(player);
        level.updatePistaos(player);
        level.updateEspinhos();

        if (level.checkLaserCollision(player)) {
            System.out.println("Você caiu em uma armadilha! Resetando...");
            player.reset();
        }

        if (level.checkEspinhosCollision(player)) {
            System.out.println("Você caiu em uma armadilha! Resetando...");
            player.reset();
        }

        if (level.checkEspinhosPCollision(player)) {
            System.out.println("Voce perdeu pelo espinhoP hehehe");
            player.reset();
        }

        if (player.x + player.width >= LARGURA) {
            currentLevel++;
            if (currentLevel <= maxLevels) {
                loadLevel(currentLevel);
                System.out.println("Avançando para o nível " + currentLevel);
            } else {
                System.out.println("Parabéns! Você terminou o jogo!");
                timer.stop();
                JOptionPane.showMessageDialog(this, "Você venceu todos os níveis!");
                System.exit(0);
            }
        }

        repaint();
    }
}
