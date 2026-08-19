package game.objetos;

import game.util.SpriteUtils;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Laser extends Objeto {

    private static final int TOTAL_FRAMES = 4;

    public static final int TICKS_POR_FRAME = 8;

    private static BufferedImage[] framesFeixes;

    private static BufferedImage[] framesBases;

    static {
        carregarSpritesheets();
    }

    private static void carregarSpritesheets() {
        try {
            BufferedImage sheetFeixe = ImageIO.read(
                    Laser.class.getResourceAsStream("/assets/laserSprites/laserRed-sheet.png"));
            framesFeixes = SpriteUtils.cortarFrames(sheetFeixe, 50, 50, TOTAL_FRAMES);

            BufferedImage sheetBase = ImageIO.read(
                    Laser.class.getResourceAsStream("/assets/laserSprites/laserRedBase-sheet.png"));
            framesBases = SpriteUtils.cortarFrames(sheetBase, 50, 50, TOTAL_FRAMES);

        } catch (IOException e) {
            System.err.println("[Laser] Erro ao carregar spritesheets: " + e.getMessage());
        }
    }

    private static int frameGlobalIndex = 0;
    private static int ticksDesdeUltimoFrame = 0;

    public static void updateFrameGlobal() {
        ticksDesdeUltimoFrame++;
        if (ticksDesdeUltimoFrame >= TICKS_POR_FRAME) {
            frameGlobalIndex = (frameGlobalIndex + 1) % TOTAL_FRAMES;
            ticksDesdeUltimoFrame = 0;
        }
    }

    boolean debug = false;
    public boolean visible = true;

    private final int tipo;

    // ─── Movimento parametrizável ────────────────────────────────────────────
    private boolean movHabil = false;
    private boolean movVertical;

    private int movMin, movMax, movVel;

    // Número de ticks de pausa antes de inverter a direção.
    // 0 = inversão imediata.
    private int movPausaTicks;

    // Contador de ticks da pausa atual (0 quando não está em pausa)
    private int movContPausa = 0;

    // true quando movendo em direção ao máximo; false em direção ao mínimo
    private boolean movParaMax = true;

    // true quando aguardando a pausa de inversão.
    private boolean emPausa = false;

    public Laser(int x, int y, int width, int height, int tipo) {
        super(x, y, width, height);
        this.tipo = tipo;

        if (tipo < 12 || tipo > 20) {
            throw new IllegalArgumentException("Tipo de Laser inválido: " + tipo);
        }
    }

    /**
     * Habilita movimento horizontal para este laser.
     *
     * @param posMin         posição X mínima (limite esquerdo)
     * @param posMax         posição X máxima (limite direito)
     * @param velocidade     pixels por tick
     * @param intervaloTicks ticks de pausa antes de inverter (0 = imediato)
     */

    public void configMovHorizontal(int posMin, int posMax, int velocidade, int intervaloTicks) {
        this.movVertical = false;
        this.movMin = posMin;
        this.movMax = posMax;
        this.movVel = velocidade;
        this.movPausaTicks = intervaloTicks;
        this.movHabil = true;
    }

    /**
     * movimento vertical
     * 
     * @param altMin         posição Y mínima (limite superior)
     * @param altMax         posição Y máxima (limite inferior)
     * @param velocidade     pixels por tick
     * @param intervaloTicks ticks de pausa antes de inverter (0 = imediato)
     */
    public void configMovVertical(int altMin, int altMax, int velocidade, int intervaloTicks) {
        this.movVertical = true;
        this.movMin = altMin;
        this.movMax = altMax;
        this.movVel = velocidade;
        this.movPausaTicks = intervaloTicks;
        this.movHabil = true;
    }

    // Update (game loop) ────────────────────────────────────────────────
    public void update() {
        if (!movHabil)
            return;

        if (emPausa) {
            movContPausa++;
            if (movContPausa >= movPausaTicks) {
                emPausa = false;
                movContPausa = 0;
                movParaMax = !movParaMax; // inverte a direção após a pausa
            }
            return;
        }

        if (movParaMax) {
            // Movendo em direção ao máximo
            if (movVertical)
                y += movVel;
            else
                x += movVel;

            int posAtual = movVertical ? y : x;
            if (posAtual >= movMax) {
                if (movVertical)
                    y = movMax;
                else
                    x = movMax;
                iniciarPausaOuInverter();
            }
        } else {
            // Movendo em direção ao mínimo
            if (movVertical)
                y -= movVel;
            else
                x -= movVel;

            int posAtual = movVertical ? y : x;
            if (posAtual <= movMin) {
                if (movVertical)
                    y = movMin;
                else
                    x = movMin;
                iniciarPausaOuInverter();
            }
        }
    }

    private void iniciarPausaOuInverter() {
        if (movPausaTicks > 0) {
            emPausa = true;
            movContPausa = 0;
        } else {
            movParaMax = !movParaMax;
        }
    }

    // Colisão ──────────────────────────────────────────────────────────

    public boolean checkCollision(Player player) {
        Rectangle playerBounds = new Rectangle(player.x, player.y, player.width, player.height);
        return getBounds().intersects(playerBounds);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    @Override
    public void draw(Graphics g) {
        if (!visible && !debug)
            return;
        if (framesFeixes == null || framesBases == null)
            return;

        int fi = frameGlobalIndex % TOTAL_FRAMES;

        switch (tipo) {
            // Feixes horizontais
            case 12, 15 -> {
                BufferedImage frame = framesFeixes[fi];
                desenhaTileH(g, frame, x, y, width);
            }

            // Base direta
            case 13, 16 -> g.drawImage(framesBases[fi], x, y, width, height, null);

            // Base esquerda
            case 14, 17 -> {
                BufferedImage flipped = SpriteUtils.flipHorizontal(framesBases[fi]);
                g.drawImage(flipped, x, y, width, height, null);
            }

            // Base topo (rotação 90° CCW
            case 18 -> {
                BufferedImage rot = SpriteUtils.rotate90(framesBases[fi], false);
                g.drawImage(rot, x, y, width, height, null);
            }

            // Feixe vertical (rotação 90° CW)
            case 19 -> {
                BufferedImage rot = SpriteUtils.rotate90(framesFeixes[fi], true);
                desenhaTileV(g, rot, x, y, height);
            }

            // Base bottom (rotação 90° CW)
            case 20 -> {
                BufferedImage rot = SpriteUtils.rotate90(framesBases[fi], true);
                g.drawImage(rot, x, y, width, height, null);
            }
        }
    }

    private void desenhaTileH(Graphics g, BufferedImage frame, int ox, int oy, int totalWidth) {
        int fw = frame.getWidth();
        int fh = frame.getHeight();
        for (int dx = 0; dx < totalWidth; dx += fw) {
            int segW = Math.min(fw, totalWidth - dx);
            // Recorta o frame se o último segmento for menor que um frame completo
            if (segW < fw) {
                BufferedImage parcial = frame.getSubimage(0, 0, segW, fh);
                g.drawImage(parcial, ox + dx, oy, segW, height, null);
            } else {
                g.drawImage(frame, ox + dx, oy, fw, height, null);
            }
        }
    }

    private void desenhaTileV(Graphics g, BufferedImage frame, int ox, int oy, int totalHeight) {
        int fw = frame.getWidth();
        int fh = frame.getHeight();
        for (int dy = 0; dy < totalHeight; dy += fh) {
            int segH = Math.min(fh, totalHeight - dy);
            if (segH < fh) {
                BufferedImage parcial = frame.getSubimage(0, 0, fw, segH);
                g.drawImage(parcial, ox, oy + dy, width, segH, null);
            } else {
                g.drawImage(frame, ox, oy + dy, width, fh, null);
            }
        }
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setVisible(boolean v) {
        this.visible = v;
    }

    public boolean isVisible() {
        return visible;
    }
}
