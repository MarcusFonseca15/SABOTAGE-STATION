package jogo_de_trap;

import javax.imageio.ImageIO;
import java.awt.*;
import javax.swing.*;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Pistao extends Objeto {
    int nivelForca; // 1, 2, 3
    boolean ativo = false;
    int frameAtual = 0;
    int contFrame = 0;
    int periodoFrames = 3; // tempo entre frames

    BufferedImage[] sprites;

    Player player;

    public Pistao(int x, int y, int nivelForca, boolean camufla) {
        super(x, y, 50, 50);
        this.nivelForca = nivelForca;

        // Mostra frames de acordo com nivel de força
        int totalFrames = nivelForca == 1 ? 3 : (nivelForca == 2 ? 5 : 7);
        sprites = new BufferedImage[totalFrames];

        String tipoSup = camufla ? "pistaoCam" : "pistaoNormal";

        for (int i = 0; i < totalFrames; i++) {
            try {
                String path = "/jogo_de_trap/assets/pistaoSprites/" + tipoSup + (i + 1) + ".png";
                var stream = getClass().getResourceAsStream(path);

                if (stream == null) {
                    System.out.println("Imagem não encontrada: " + path);
                } else {
                    sprites[i] = ImageIO.read(stream);
                    System.out.println("Imagem carregada com sucesso: " + path);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void update(Player p) {
        this.player = p;
        Rectangle playerBounds = new Rectangle(p.x, p.y, p.width, p.height);
        Rectangle pistaoBounds = new Rectangle(x, y, width, height);

        // se player está em cima, ativa o pistão
        if (playerBounds.intersects(pistaoBounds)) {
            ativo = true;
            propulsionar();
        }

        if (ativo) {
            contFrame++;
            if (contFrame >= periodoFrames) {
                if (frameAtual < sprites.length - 1) {
                    frameAtual++;
                } else {
                    ativo = false;
                    frameAtual = 0;
                }
                contFrame = 0;
            }
        }
    }

    private void propulsionar() {
        // se player está em cima, ativa o pistão
        if (player.y + player.height <= y + 10) {
            int impulso = nivelForca == 1 ? -12 : (nivelForca == 2 ? -18 : -24);
            player.velY = impulso;
            player.jumping = true;
            player.onGround = false;
        }
    }

    @Override
    public void draw(Graphics g) {
        BufferedImage img = sprites[frameAtual];
        if (img == null)
            return; // evitar o NullPointerException
        int alturaImg = img.getHeight(); // Altura original do frame
        int yVisual = y + height - alturaImg; // Alinha a base do pistão com a base da célula
        g.drawImage(img, x, yVisual, width, alturaImg, null);

    }

    public Rectangle getBounds() {
        BufferedImage img = sprites[frameAtual];
        int alturaImg = img.getHeight();
        int yVisual = y + height - alturaImg;
        return new Rectangle(x, yVisual, width, alturaImg);
    }
}