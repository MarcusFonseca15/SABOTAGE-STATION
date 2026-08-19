package game.util;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * Utilitário estático para manipulação de spritesheets e transformações de
 * imagem.
 *
 * Todas as operações de flip/rotação são armazenadas em cache estático para que
 * cada
 * transformação seja calculada uma única vez por sessão do jogo, não a cada
 * frame.
 */
public final class SpriteUtils {

    // Cache de imagens transformadas: chave = descrição textual da operação +
    // hashCode da imagem
    private static final Map<String, BufferedImage> cache = new HashMap<>();

    private SpriteUtils() {
    }

    public static BufferedImage[] cortarFrames(BufferedImage spritesheet,
            int frameWidth,
            int frameHeight,
            int totalFrames) {
        int esperado = frameWidth * totalFrames;
        if (spritesheet.getWidth() != esperado) {
            throw new IllegalArgumentException(
                    "Spritesheet com largura " + spritesheet.getWidth() +
                            " px, mas esperado " + esperado + " px (" +
                            totalFrames + " frames × " + frameWidth + " px).");
        }

        BufferedImage[] frames = new BufferedImage[totalFrames];
        for (int i = 0; i < totalFrames; i++) {
            frames[i] = spritesheet.getSubimage(i * frameWidth, 0, frameWidth, frameHeight);
        }
        return frames;
    }

    // Flip horizontal

    public static BufferedImage flipHorizontal(BufferedImage original) {
        String chave = "flip_" + System.identityHashCode(original);
        return cache.computeIfAbsent(chave, k -> {
            int w = original.getWidth();
            int h = original.getHeight();
            BufferedImage resultado = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = resultado.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
            // Escala -1 em X + translação para colocar a imagem de volta na área visível
            AffineTransform at = AffineTransform.getScaleInstance(-1.0, 1.0);
            at.translate(-w, 0);
            g2.drawImage(original, at, null);
            g2.dispose();
            return resultado;
        });
    }
    // Rotação 90°

    /**
     * Retorna uma cópia da imagem rotacionada 90° (sentido horário ou
     * anti-horário).
     * O BufferedImage resultante tem largura e altura trocadas.
     * Resultado é cacheado.
     *
     * @param clockwise {@code true} = 90° CW (quadrante 1),
     *                  {@code false} = 90° CCW (quadrante 3)
     */
    public static BufferedImage rotate90(BufferedImage original, boolean clockwise) {
        String chave = "rot_" + (clockwise ? "cw" : "ccw") + "_" + System.identityHashCode(original);
        return cache.computeIfAbsent(chave, k -> {
            int w = original.getWidth();
            int h = original.getHeight();
            // Largura e altura trocadas no destino
            BufferedImage resultado = new BufferedImage(h, w, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = resultado.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

            if (clockwise) {
                // 90° CW: translada pelo novo W (= h original) e depois rotaciona +90°
                g2.translate(h, 0);
                g2.rotate(Math.PI / 2);
            } else {
                // 90° CCW: translada pelo novo H (= w original) e depois rotaciona -90°
                g2.translate(0, w);
                g2.rotate(-Math.PI / 2);
            }
            g2.drawImage(original, 0, 0, null);
            g2.dispose();
            return resultado;
        });
    }
}
