package game;

/**
 * Gravity.NORMAL → direcao=1, forca=1.0, forcaPulo=15.0
 * Gravity.LUNAR → direcao=1, forca=0.3, forcaPulo=15.0
 * Gravity.SOLAR → direcao=1, forca=4.0, forcaPulo=15.0
 * Gravity.INVERTIDA → direcao=-1, forca=1.0, forcaPulo=15.0
 */
public class Gravity {

    // ── Perfis estáticos ──────────────────────────────────────────────────────
    public static final Gravity NORMAL = new Gravity(1, 1.0, 15.0);
    public static final Gravity LUNAR = new Gravity(1, 0.1, 15.0);
    public static final Gravity SOLAR = new Gravity(1, 4.0, 15.0);
    public static final Gravity INVERTIDA = new Gravity(-1, 1.0, 15.0);

    // ── Campos ───────────────────────────────────────────────────────────────
    private int direcao; // 1 = normal, -1 = invertida
    private double forca;
    private double forcaPulo;

    // ── Construtores ─────────────────────────────────────────────────────────

    // Construtor padrão: gravidade normal (Terra)
    public Gravity() {
        this(1, 1.0, 15.0);
    }

    // Construtor completo com todos os parâmetros
    public Gravity(int direcao, double forca, double forcaPulo) {
        this.direcao = (direcao >= 0) ? 1 : -1;
        this.forca = Math.abs(forca);
        this.forcaPulo = Math.abs(forcaPulo);
    }

    public Gravity(double vInit) {
        this(vInit >= 0 ? 1 : -1, Math.abs(vInit) == 0 ? 0.0 : Math.abs(vInit), 15.0);
    }

    /**
     * Define todos os parâmetros de uma vez.
     * 
     * @param direcao   1 = normal, -1 = invertida
     * @param forca     magnitude da aceleração (positiva)
     * @param forcaPulo magnitude da velocidade do pulo (positiva)
     */
    public void setGravity(int direcao, double forca, double forcaPulo) {
        this.direcao = (direcao >= 0) ? 1 : -1;
        this.forca = Math.abs(forca);
        this.forcaPulo = Math.abs(forcaPulo);
    }

    // ── Setters individuais (compatibilidade retroativa) ─────────────────────

    /**
     * Altera apenas a força da gravidade, mantendo a direção atual.
     * Aceita 0 para "desligar" a gravidade (usado no Level06).
     * 
     * @param forca magnitude (o sinal é ignorado; use 0 para desligar)
     */
    public void setGravity(double forca) {
        this.forca = Math.abs(forca);
    }

    /**
     * Altera apenas a força do pulo.
     * 
     * @param magnitude SEMPRE positivo — o sentido é calculado automaticamente pelo
     *                  Player
     */
    public void setPulo(double magnitude) {
        this.forcaPulo = Math.abs(magnitude);
    }

    public int getDirecao() {
        return direcao;
    }

    public double getForca() {
        return forca;
    }

    public double getForcaPulo() {
        return forcaPulo;
    }

    /** @deprecated Use {@link #getDirecao()} — mantido para ColisionManager. */
    @Deprecated
    public int getDirection() {
        return direcao;
    }

    /**
     * @deprecated Use {@link #getForca()} — mantido para Level06 (monitorarPulo).
     */
    @Deprecated
    public double getGravity() {
        return forca;
    }
}
