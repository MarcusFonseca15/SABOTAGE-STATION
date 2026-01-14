package jogo_de_trap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextLayout;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

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

    public int currentLevel = 1;
    int proximoLevel = 0; // apenas de inicialização
    private final int maxLevels = 10;
    int numFase = 1;

    private boolean godMode = false;
    private boolean modoVida = false;

    private GameFrame gameFrame;

    // DIMENSÕES FIXAS
    private final int LARGURA = 800;
    private final int ALTURA = 600;

    private Timer timer;
    private Player player;
    private Level level;
    private Image backgroundImage;

    private List<Integer> filaFases;

    private int vida = 10;
    private int MAX_VIDAS = 10;
    private Image[] barraVidaImages = new Image[11];

    // VARIAVEIS DE TRANSIÇÃO DE TELA ESMAECER
    private enum EstadoTrans {
        NORMAL, FADEOUT, FADEIN
    }

    float alphaFade = 0.0f;
    EstadoTrans estadoTrans = EstadoTrans.NORMAL;

    public GamePanel(GameFrame frame) {

        this.gameFrame = frame;

        this.setPreferredSize(new Dimension(LARGURA, ALTURA));
        this.setFocusable(true);

        backgroundImage = new ImageIcon(getClass().getResource("/assets/background2.png")).getImage();

        Gravity gravityInit = new Gravity(1.0);
        player = new Player(100, 500, gravityInit);

        initRandomFases();
        loadLevel(currentLevel);

        // IMGS DA BARRA DE VIDA
        for (int i = 0; i <= MAX_VIDAS; i++) {
            barraVidaImages[i] = new ImageIcon(getClass().getResource("/assets/LifeBar/lifebar" + i + ".png"))
                    .getImage();
        }

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (currentLevel == 1 && level instanceof Level01) {
                    ((Level01) level).keyPressed(e);
                }
                // GOD MODE =====================
                if (e.getKeyCode() == KeyEvent.VK_F1) {
                    godMode = !godMode;
                    String status = godMode ? "ATIVADO" : "DESATIVADO";
                    System.out.println(">>> GOD MODE " + status + " <<<");
                }
                // ===============================

                player.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                player.keyReleased(e);
            }

        });

        timer = new Timer(19, this);
        setLayout(null);
        timer.start();
    }

    private void loadLevel(int number) {
        player.g.setGravity(1.0);
        player.g.setPulo(-15);
        player.onGround = true;
        player.jumping = false;
        player.velY = 0;

        switch (number) {
            case 1 -> level = new Level01(player, this);
            case 2 -> level = new Level02(player, this);
            case 3 -> level = new Level03(player, this);
            case 4 -> level = new Level04(player, this);
            case 5 -> level = new Level05(player, this);
            case 6 -> level = new Level06(player, this);
            case 7 -> level = new Level07(player, this);
            case 8 -> level = new Level08(player, this);
            case 9 -> level = new Level09(player, this);
            case 10 -> level = new Level10(player, this);
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

        // ---------------- ELEMENTOS DA FASE

        // BARRA DE VIDA
        if (barraVidaImages[vida] != null) {
            g.drawImage(barraVidaImages[vida], 10, 550, 200, 48, this); // x, y, largura, altura
        }

        // IMAGEM DE EXIT
        if (level.isShowExit() && level.getExitImage() != null) {
            g.drawImage(level.getExitImage(),
                    level.getExitX(),
                    level.getExitY(),
                    level.getExitWidth(),
                    level.getExitHeight(),
                    this);
        }

        ///////// PLAYER /////////////
        player.draw(g);

        // ------------ TITULO COM CONTORNO
        {
            Graphics2D g2d = (Graphics2D) g.create();

            String texto = level.getTitulo();
            int x = level.getTitleX();
            int y = level.getTitleY();
            int size = level.getSizeTitle();
            Color fillColor = level.getCorTitle();

            Font font = new Font("Arial", Font.BOLD, size);
            // g2d.setFont(font);

            // Preparação da forma do texto
            FontRenderContext frc = g2d.getFontRenderContext();
            TextLayout textLayout = new TextLayout(texto, font, frc);
            Shape outline = textLayout.getOutline(null);
            Rectangle2D bounds = textLayout.getBounds();

            g2d.translate(x, y);

            // Fundo do texto
            int padding = 10;
            g2d.setColor(new Color(0, 0, 0, 100));
            g2d.fillRect(
                    (int) bounds.getX() - padding,
                    (int) bounds.getY() - padding,
                    (int) bounds.getWidth() + (padding * 2),
                    (int) bounds.getHeight() + (padding * 2));

            // Contorno
            g2d.setStroke(new BasicStroke(2.0f)); // Espessura do contorno
            g2d.setColor(Color.BLACK);
            g2d.draw(outline);

            // Texto preenchido
            g2d.setColor(fillColor);
            g2d.fill(outline);

            g2d.dispose();
        }
        // TRANSIÇÃO
        if (estadoTrans != EstadoTrans.NORMAL) {
            Graphics2D g2dTrans = (Graphics2D) g.create();
            g2dTrans.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaFade));
            g2dTrans.setColor(Color.BLACK);
            g2dTrans.fillRect(0, 0, getWidth(), getHeight());
            g2dTrans.dispose();
        }
        updateTransition();

        if (currentLevel == 1 && level instanceof Level01) {
            ((Level01) level).drawInstructions(g);
        }
    }

    private void initRandomFases() {
        // Inicializa a fila de fases aleatórias (3 a 7)
        filaFases = new ArrayList<>();
        for (int i = 3; i <= 7; i++) {
            filaFases.add(i);
        }
        Collections.shuffle(filaFases);
    }

    // COLISOES E PASSAR FASE
    @Override
    public void actionPerformed(ActionEvent e) {
        player.update();
        level.checkPlatformCollision(player);
        level.checkPistaoCollision(player);
        level.updatePistaos(player);
        level.updateEspinhos();

        if (currentLevel == 1 && level instanceof Level01) {
            ((Level01) level).updateInstructions();
        }

        if (!godMode && level.checkLaserCollision(player)) {
            System.out.println("Você caiu em uma armadilha! Resetando...");
            // diminuindo vida pós o dano
            perderVida();
            player.reset();
        }

        if (!godMode && level.checkEspinhosCollision(player)) {
            System.out.println("Você caiu em uma armadilha! Resetando...");
            // diminuindo vida pós o dano
            perderVida();
            player.reset();
        }

        if (!godMode && level.checkEspinhosPCollision(player)) {
            System.out.println("Voce perdeu pelo espinhoP hehehe");
            // diminuindo vida pós o dano
            perderVida();
            player.reset();
        }

        if (player.x + player.width >= LARGURA) {

            if (estadoTrans == EstadoTrans.NORMAL) {

                // Aleatorização de fases da 3 a 7
                if (currentLevel == 2) {
                    proximoLevel = filaFases.remove(0);
                } else if (currentLevel >= 3 && currentLevel <= 7) {
                    // Se ainda houver fases na fila, continua sorteando
                    if (!filaFases.isEmpty()) {
                        proximoLevel = filaFases.remove(0);
                    } else {
                        proximoLevel = 8;
                    }
                } else {
                    proximoLevel = currentLevel + 1;
                }

                estadoTrans = EstadoTrans.FADEOUT;
                alphaFade = 0.0f;
            }
        }

        repaint();
    }

    private void perderVida() {
        vida--;
        gameFrame.vibrarTela(300, 5);
        if (vida <= 0 && !modoVida) {
            // Volta para fase 1
            System.out.println("Você perdeu todas as vidas! Reiniciando o jogo...");
            currentLevel = 1;
            vida = MAX_VIDAS;
            toFinalPanel(false);
            // loadLevel(currentLevel);
        } else {
            player.reset(); // Reset normal da posição
        }
    }

    private void updateTransition() {
        if (estadoTrans == EstadoTrans.FADEOUT) {
            alphaFade += 0.05f;
            if (alphaFade >= 1.0f) {
                alphaFade = 1.0f;

                if (level != null) {
                    level.pararThread();
                }

                // === LÓGICA DE AVANÇO DE NÍVEL ===
                if (proximoLevel <= maxLevels) {
                    currentLevel = proximoLevel;
                    numFase++;
                    loadLevel(currentLevel);
                    System.out.println("Avançando para o nível " + currentLevel);
                    vida = MAX_VIDAS;
                    estadoTrans = EstadoTrans.FADEIN; // INICIA O ESMAECER PARA FORA
                } else {
                    // Fim do jogo
                    System.out.println("Parabéns! Você terminou o jogo!");
                    estadoTrans = EstadoTrans.NORMAL; // Volta ao normal para não tentar FADEIN
                    timer.stop();
                    toFinalPanel(true);
                }
                // ===========================================

            }
        } else if (estadoTrans == EstadoTrans.FADEIN) {
            alphaFade -= 0.05f;
            if (alphaFade <= 0.0f) {
                alphaFade = 0.0f;
                estadoTrans = EstadoTrans.NORMAL;
            }
        }
    }

    private void toFinalPanel(boolean win) {
        gameFrame.getContentPane().removeAll();
        FinalPanel finalPanel = new FinalPanel(gameFrame, win);
        gameFrame.add(finalPanel);
        gameFrame.revalidate();
        gameFrame.repaint();
    }

    public int getNumFase() {
        return numFase;
    }

}
