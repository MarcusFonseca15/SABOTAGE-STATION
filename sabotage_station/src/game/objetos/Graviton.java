package game.objetos;

import game.Gravity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

/*
 *   Tipo 30 → NORMAL    (graviton-normal.png)
 *   Tipo 31 → LUNAR     (graviton-lua.png)
 *   Tipo 32 → SOLAR     (graviton-sol.png)
 *   Tipo 33 → INVERTIDA (graviton-invers.png)
 *
 * Regras:
 *   Apenas o graviton responsável pela gravidade vigente fica ativo.
 *   Ao player passar por outro graviton, o anterior se desativa.
 *   Ao player morrer (reset), todos voltam ao estado desativado.
 */
public class Graviton extends Objeto {

    // ── Constantes de tipo ────────────────────────────────────────────────────
    public static final int TIPO_NORMAL = 30;
    public static final int TIPO_LUNAR = 31;
    public static final int TIPO_SOLAR = 32;
    public static final int TIPO_INVERTIDA = 33;

    // ── Sprites estáticos compartilhados ─────────────────────────────────────
    private static final Map<Integer, BufferedImage> spritesAtivos = new HashMap<>();
    private static BufferedImage spriteDesativ;

    static {
        carregarSprites();
    }

    private static void carregarSprites() {
        try {
            spriteDesativ = ImageIO.read(
                    Graviton.class.getResourceAsStream("/assets/graviton/graviton-desativ.png"));

            spritesAtivos.put(TIPO_NORMAL, ImageIO.read(
                    Graviton.class.getResourceAsStream("/assets/graviton/graviton-normal.png")));
            spritesAtivos.put(TIPO_LUNAR, ImageIO.read(
                    Graviton.class.getResourceAsStream("/assets/graviton/graviton-lua.png")));
            spritesAtivos.put(TIPO_SOLAR, ImageIO.read(
                    Graviton.class.getResourceAsStream("/assets/graviton/graviton-sol.png")));
            spritesAtivos.put(TIPO_INVERTIDA, ImageIO.read(
                    Graviton.class.getResourceAsStream("/assets/graviton/graviton-invers.png")));

        } catch (IOException e) {
            System.err.println("[Graviton] Erro ao carregar sprites: " + e.getMessage());
        }
    }

    private static final Map<Integer, Gravity> gravidadesPorTipo = new HashMap<>();

    static {
        gravidadesPorTipo.put(TIPO_NORMAL, Gravity.NORMAL);
        gravidadesPorTipo.put(TIPO_LUNAR, Gravity.LUNAR);
        gravidadesPorTipo.put(TIPO_SOLAR, Gravity.SOLAR);
        gravidadesPorTipo.put(TIPO_INVERTIDA, Gravity.INVERTIDA);
    }

    private final int tipo;
    private boolean ativo = false;

    public Graviton(int x, int y, int width, int height, int tipo) {
        super(x, y, width, height);
        if (!gravidadesPorTipo.containsKey(tipo)) {
            throw new IllegalArgumentException("[Graviton] Tipo invalido: " + tipo);
        }
        this.tipo = tipo;
    }

    public boolean checkAndActivate(Rectangle playerBounds, Gravity playerGravity,
            List<Graviton> todos) {
        if (getBounds().intersects(playerBounds)) {
            if (!ativo) {
                // Desativa todos os outros gravitons da fase
                for (Graviton g : todos) {
                    if (g != this) {
                        g.ativo = false;
                    }
                }
                ativo = true;

                // Aplica a gravidade deste graviton ao player
                Gravity origem = gravidadesPorTipo.get(tipo);
                playerGravity.setGravity(
                        origem.getDirecao(),
                        origem.getForca(),
                        origem.getForcaPulo());
            }
            return true;
        }
        return false;
    }

    public void desativar() {
        ativo = false;
    }

    private BufferedImage getSpriteAtual() {
        if (ativo) {
            return spritesAtivos.get(tipo);
        }
        return spriteDesativ;
    }

    @Override
    public void draw(Graphics g) {
        BufferedImage sprite = getSpriteAtual();
        if (sprite != null) {
            g.drawImage(sprite, x, y, width, height, null);
        }
    }

    public int getTipo() {
        return tipo;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public Gravity getGravidade() {
        return gravidadesPorTipo.get(tipo);
    }
}
