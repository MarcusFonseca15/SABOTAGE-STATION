package game;

/**
 * Representa o perfil de gravidade aplicado ao player.
 *
 * Separação explícita dos três conceitos:
 *   direcao   – sentido da gravidade: 1 = normal (para baixo), -1 = invertida (para cima)
 *   forca     – taxa de aceleração por frame, SEMPRE positiva (padrão 1.0)
 *   forcaPulo – velocidade inicial do pulo,   SEMPRE positiva (padrão 15.0)
 *
 * Perfis prontos:
 *   Gravity.NORMAL    → direcao=1, forca=1.0, forcaPulo=15.0
 *   Gravity.LUNAR     → direcao=1, forca=0.3, forcaPulo=15.0
 *   Gravity.SOLAR     → direcao=1, forca=4.0, forcaPulo=15.0
 *   Gravity.INVERTIDA → direcao=-1, forca=1.0, forcaPulo=15.0
 */
public class Gravity {

    // ── Perfis estáticos ──────────────────────────────────────────────────────
    public static final Gravity NORMAL    = new Gravity(1,  1.0, 15.0);
    public static final Gravity LUNAR     = new Gravity(1,  0.3, 15.0);
    public static final Gravity SOLAR     = new Gravity(1,  4.0, 15.0);
    public static final Gravity INVERTIDA = new Gravity(-1, 1.0, 15.0);

    // ── Campos ───────────────────────────────────────────────────────────────
    private int    direcao;    // 1 = normal, -1 = invertida
    private double forca;      // magnitude da aceleração por frame (sempre positiva)
    private double forcaPulo;  // magnitude da velocidade inicial do pulo (sempre positiva)

    // ── Construtores ─────────────────────────────────────────────────────────

    /** Construtor padrão: gravidade normal (Terra). */
    public Gravity() {
        this(1, 1.0, 15.0);
    }

    /** Construtor completo com todos os parâmetros. */
    public Gravity(int direcao, double forca, double forcaPulo) {
        this.direcao   = (direcao >= 0) ? 1 : -1;
        this.forca     = Math.abs(forca);
        this.forcaPulo = Math.abs(forcaPulo);
    }

    /**
     * Construtor de compatibilidade com a assinatura antiga: new Gravity(double vInit).
     * O sinal de vInit define a direção; a magnitude define a força.
     * Exemplos: new Gravity(1.0) → normal; new Gravity(-1.0) → invertida.
     */
    public Gravity(double vInit) {
        this(vInit >= 0 ? 1 : -1, Math.abs(vInit) == 0 ? 0.0 : Math.abs(vInit), 15.0);
    }

    // ── Setter unificado (novo) ───────────────────────────────────────────────

    /**
     * Define todos os parâmetros de uma vez.
     * @param direcao   1 = normal, -1 = invertida
     * @param forca     magnitude da aceleração (positiva)
     * @param forcaPulo magnitude da velocidade do pulo (positiva)
     */
    public void setGravity(int direcao, double forca, double forcaPulo) {
        this.direcao   = (direcao >= 0) ? 1 : -1;
        this.forca     = Math.abs(forca);
        this.forcaPulo = Math.abs(forcaPulo);
    }

    // ── Setters individuais (compatibilidade retroativa) ─────────────────────

    /**
     * Altera apenas a força da gravidade, mantendo a direção atual.
     * Aceita 0 para "desligar" a gravidade (usado no Level06).
     * @param forca magnitude (o sinal é ignorado; use 0 para desligar)
     */
    public void setGravity(double forca) {
        this.forca = Math.abs(forca);
    }

    /**
     * Altera apenas a força do pulo.
     * @param magnitude SEMPRE positivo — o sentido é calculado automaticamente pelo Player
     */
    public void setPulo(double magnitude) {
        this.forcaPulo = Math.abs(magnitude);
    }

    // ── Getters ──────────────────────────────────────────────────────────────

    /** Direção da gravidade: 1 = normal (para baixo), -1 = invertida (para cima). */
    public int getDirecao() {
        return direcao;
    }

    /** Força de aceleração por frame (sempre positiva). */
    public double getForca() {
        return forca;
    }

    /** Magnitude da velocidade inicial do pulo (sempre positiva). */
    public double getForcaPulo() {
        return forcaPulo;
    }

    // ── Aliases retroativos ──────────────────────────────────────────────────

    /** @deprecated Use {@link #getDirecao()} — mantido para ColisionManager. */
    @Deprecated
    public int getDirection() {
        return direcao;
    }

    /** @deprecated Use {@link #getForca()} — mantido para Level06 (monitorarPulo). */
    @Deprecated
    public double getGravity() {
        return forca;
    }
}
