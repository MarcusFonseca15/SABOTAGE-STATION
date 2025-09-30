package jogo_de_trap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextLayout;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

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

    private int currentLevel = 1;

    private final int maxLevels = 10;

    private int vida = 5;
    private int MAX_VIDAS = 5;
    private Image[] barraVidaImages = new Image[6];

    private boolean modoVida = false;

    public GamePanel() {
        this.setPreferredSize(new Dimension(LARGURA, ALTURA));
        this.setFocusable(true);

        backgroundImage = new ImageIcon(getClass().getResource("/assets/background2.png")).getImage();

        Gravity gravityInit = new Gravity(1.0);
        player = new Player(100, 500, gravityInit);

        loadLevel(currentLevel);

        // IMGS DA BARRA DE VIDA
        for (int i = 0; i <= MAX_VIDAS; i++) {
            barraVidaImages[i] = new ImageIcon(getClass().getResource("/assets/LifeBar/lfb" + i + ".png"))
                    .getImage();
        }

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

        timer = new Timer(25, this);
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

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }

        level.draw(g);
        player.draw(g);
        // BARRA DE VIDA
        if (barraVidaImages[vida] != null) {
            g.drawImage(barraVidaImages[vida], 10, 550, 192, 48, this); // indica x, y, largura, altura
        }
        // TITULO COM CONTORNO
        Graphics2D g2d = (Graphics2D) g.create();

        String texto = level.getTitulo();
        int x = level.getTitleX();
        int y = level.getTitleY();
        int size = level.getSizeTitle();
        Color fillColor = level.getCorTitle();
        Color outlineColor = Color.BLACK; // Cor do contorno (pode colocar no Level também, se quiser)

        Font font = new Font("Arial", Font.BOLD, size);
        g2d.setFont(font);

        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(texto);
        int textHeight = fm.getAscent();

        // Criar forma do texto
        FontRenderContext frc = g2d.getFontRenderContext();
        TextLayout textLayout = new TextLayout(texto, font, frc);
        Shape outline = textLayout.getOutline(null);

        AffineTransform transform = g2d.getTransform();
        transform.translate(x, y);
        g2d.transform(transform);

        // Contorno
        g2d.setColor(outlineColor);
        g2d.setStroke(new BasicStroke(2.0f)); // Espessura do contorno
        g2d.draw(outline);

        // Texto preenchido
        g2d.setColor(fillColor);
        g2d.fill(outline);

        g2d.dispose();
    }

    // COLISOES E DANOS
    @Override
    public void actionPerformed(ActionEvent e) {
        player.update();
        level.checkPlatformCollision(player);
        level.checkPistaoCollision(player);
        level.updatePistaos(player);
        level.updateEspinhos();

        if (level.checkLaserCollision(player)) {
            System.out.println("Você caiu em uma armadilha! Resetando...");
            // diminuindo vida pós o dano
            perderVida();
            player.reset();
        }

        if (level.checkEspinhosCollision(player)) {
            System.out.println("Você caiu em uma armadilha! Resetando...");
            // diminuindo vida pós o dano
            perderVida();
            player.reset();
        }

        if (level.checkEspinhosPCollision(player)) {
            System.out.println("Voce perdeu pelo espinhoP hehehe");
            // diminuindo vida pós o dano
            perderVida();
            player.reset();
        }

        if (player.x + player.width >= LARGURA) {
            currentLevel++;
            if (currentLevel > 6 || (currentLevel > 9 && currentLevel != 9)) {
                level.pararThread();
            }
            if (currentLevel <= maxLevels) {
                loadLevel(currentLevel);
                System.out.println("Avançando para o nível " + currentLevel);
                vida = MAX_VIDAS;
            } else {
                System.out.println("Parabéns! Você terminou o jogo!");
                timer.stop();
                JOptionPane.showMessageDialog(this, "Você venceu todos os níveis!");
                System.exit(0);
            }
        }

        repaint();
    }

    private void perderVida() {
        vida--;
        if (vida <= 0 && !modoVida) {
            // Volta para fase 1
            System.out.println("Você perdeu todas as vidas! Reiniciando o jogo...");
            currentLevel = 1;
            vida = MAX_VIDAS;
            loadLevel(currentLevel);
        } else {
            player.reset(); // Reset normal da posição
        }
    }
}
