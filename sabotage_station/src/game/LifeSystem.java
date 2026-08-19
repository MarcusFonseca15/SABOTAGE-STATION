package game;

import javax.swing.ImageIcon;
import java.awt.*;
import java.net.URL;

/**
 * Callback interface for life-related events.
 * GamePanel implements this so LifeSystem can trigger reactions
 * (game-over, player respawn) without importing GamePanel directly.
 */
interface LifeEventListener {
    /** Called when the player runs out of lives (after the death delay expires). */
    void onGameOver();

    /**
     * Called when the player loses a life but still has lives remaining (respawn).
     */
    void onPlayerReset();
}

/**
 * Manages the player's life system:
 * - Life count and maximum lives per game mode
 * - Loading and caching lifebar sprites
 * - Drawing the lifebar (preserving aspect ratio)
 * - Death delay timer before triggering game-over
 */
public class LifeSystem {

    // ── Vida ──────────────────────────────────────────────────────────────────
    private int vida;
    private final int MAX_VIDAS;
    private Image[] barraVidaImages;

    // ── Delay pós-morte ───────────────────────────────────────────────────────
    private long morteTime = 0;
    private boolean waitMorte = false;
    private static final long MORTE_DELAY = 1000; // ms

    // ── Configurações de desenho ──────────────────────────────────────────────
    private static final int BOX_H = 48; // altura máxima da área da lifebar
    private static final int DRAW_X = 10; // posição X na tela
    private static final int DRAW_Y = 550; // posição Y na tela (topo da área)

    // ── Dependências externas ─────────────────────────────────────────────────
    private final Component observer; // ImageObserver para drawImage
    private final LifeEventListener listener; // callbacks de evento

    // ─────────────────────────────────────────────────────────────────────────
    // Construtores
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Construtor com modo de jogo explícito.
     *
     * @param modo     define o número máximo de vidas
     * @param observer componente usado como ImageObserver no drawImage (geralmente
     *                 o GamePanel)
     * @param listener recebe callbacks de onGameOver() e onPlayerReset()
     */
    public LifeSystem(ModoJogo modo, Component observer, LifeEventListener listener) {
        this.observer = observer;
        this.listener = listener;

        if (modo == ModoJogo.TREINO) {
            this.MAX_VIDAS = 20;
        } else if (modo == ModoJogo.NORMAL) {
            this.MAX_VIDAS = 10;
        } else { // DIFICIL
            this.MAX_VIDAS = 5;
        }

        this.vida = MAX_VIDAS;
        carregarSprites();
    }

    /**
     * Construtor de conveniência para o modo padrão (NORMAL, 10 vidas).
     *
     * @param observer componente usado como ImageObserver no drawImage
     * @param listener recebe callbacks de onGameOver() e onPlayerReset()
     */
    public LifeSystem(Component observer, LifeEventListener listener) {
        this(ModoJogo.NORMAL, observer, listener);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Inicialização
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Carrega os sprites da lifebar do classpath. Erros de sprite ausente são
     * logados sem lançar exceção.
     */
    private void carregarSprites() {
        barraVidaImages = new Image[MAX_VIDAS + 1];
        for (int i = 0; i <= MAX_VIDAS; i++) {
            URL url = getClass().getResource("/assets/LifeBar/lifebar" + i + ".png");
            if (url != null) {
                barraVidaImages[i] = new ImageIcon(url).getImage();
            } else {
                System.err.println("[LifeSystem] Sprite não encontrado: lifebar" + i + ".png");
                barraVidaImages[i] = null;
            }
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // API pública — chamada a cada frame
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Verifica e avança o delay pós-morte. Deve ser chamado no início de cada frame
     * (em {@code actionPerformed}).
     *
     * @return {@code true} se o delay ainda está ativo e o chamador deve pular o
     *         restante
     *         do update; {@code false} se não há morte pendente.
     */
    public boolean tickDelay() {
        if (!waitMorte)
            return false;

        long elapsed = System.currentTimeMillis() - morteTime;
        if (elapsed >= MORTE_DELAY) {
            waitMorte = false;
            vida = MAX_VIDAS;
            listener.onGameOver();
        }
        return true; // ainda aguardando (ou acabou de disparar onGameOver)
    }

    // ─────────────────────────────────────────────────────────────────────────
    // API pública — eventos de vida
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Registra a perda de uma vida. Se ainda houver vidas, chama
     * {@code onPlayerReset()};
     * caso contrário inicia o delay pós-morte que culminará em
     * {@code onGameOver()}.
     */
    public void perderVida() {
        vida--;

        if (vida <= 0) {
            // Inicia o delay antes de acionar o game-over
            waitMorte = true;
            morteTime = System.currentTimeMillis();
            System.out.println("[LifeSystem] Todas as vidas perdidas. Aguardando delay...");
        } else {
            // Ainda tem vidas: respawn imediato
            listener.onPlayerReset();
        }
    }

    /**
     * Reseta as vidas para o máximo (chamado ao avançar de fase).
     */
    public void resetarVidas() {
        vida = MAX_VIDAS;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // API pública — desenho
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Desenha a barra de vida na posição configurada, preservando o aspect ratio
     * do sprite com altura limitada a {@value BOX_H}px. A largura é calculada
     * automaticamente a partir da proporção original da imagem.
     *
     * @param g contexto gráfico do componente pai
     */
    public void draw(Graphics g) {
        if (vida < 0 || vida >= barraVidaImages.length || barraVidaImages[vida] == null)
            return;

        Image img = barraVidaImages[vida];
        int imgW = img.getWidth(observer);
        int imgH = img.getHeight(observer);

        int drawW, drawH;
        if (imgW <= 0 || imgH <= 0) {
            // Dimensões ainda não carregadas — usa a altura máxima como fallback
            drawW = BOX_H; // quadrado como fallback seguro
            drawH = BOX_H;
        } else {
            // Escala apenas pela altura; largura acompanha a proporção original
            double scale = (double) BOX_H / imgH;
            drawW = (int) (imgW * scale);
            drawH = BOX_H;
        }

        // Centraliza verticalmente dentro da área BOX_H
        int drawY = DRAW_Y + (BOX_H - drawH) / 2;
        g.drawImage(img, DRAW_X, drawY, drawW, drawH, observer);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Getters
    // ─────────────────────────────────────────────────────────────────────────

    public int getVida() {
        return vida;
    }

    public int getMaxVidas() {
        return MAX_VIDAS;
    }

    public boolean isWaitingMorte() {
        return waitMorte;
    }
}
