package game.objetos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.Rectangle;

import game.Gravity;

public class Player {
    int x, y;
    int width = 50, height = 50;

    public Gravity g;

    public int velX = 0;
    public int velY = 0;
    public boolean jumping = false;
    public boolean onGround = true;
    public boolean wantToJump = false;
    boolean TrocaTroca = true;

    Image PersoPulandoD;
    Image PersoPulandoE;
    Image[] PersoCorrendoD;
    Image[] PersoCorrendoE;
    Image[] PersoParadoD;
    Image[] PersoParadoE;

    int ControleDoIndex = 0;
    int IntervaloDoFrame = 5;
    int ContDoFrame = 0;

    int PControleDoIndex = 0;
    int PIntervaloDoFrame = 5;
    int PContDoFrame = 0;

    public Player(int x, int y, Gravity g) {
        this.x = x;
        this.y = y;
        this.g = g;

        PersoParadoD = new Image[] {
                new ImageIcon(getClass().getResource("/assets/playerSprites/playerSTILL1.png")).getImage(),
                new ImageIcon(getClass().getResource("/assets/playerSprites/playerSTILL2.png")).getImage(),
                new ImageIcon(getClass().getResource("/assets/playerSprites/playerSTILL3.png")).getImage(),
                new ImageIcon(getClass().getResource("/assets/playerSprites/playerSTILL4.png")).getImage()
        };

        PersoParadoE = new Image[] {
                new ImageIcon(getClass().getResource("/assets/playerSprites/playerSTILL1-left.png")).getImage(),
                new ImageIcon(getClass().getResource("/assets/playerSprites/playerSTILL2-left.png")).getImage(),
                new ImageIcon(getClass().getResource("/assets/playerSprites/playerSTILL3-left.png")).getImage(),
                new ImageIcon(getClass().getResource("/assets/playerSprites/playerSTILL4-left.png")).getImage()
        };

        PersoCorrendoD = new Image[] {
                new ImageIcon(getClass().getResource("/assets/playerSprites/playerRUN1.png")).getImage(),
                new ImageIcon(getClass().getResource("/assets/playerSprites/playerRUN2.png")).getImage(),
                new ImageIcon(getClass().getResource("/assets/playerSprites/playerRUN3.png")).getImage(),
                new ImageIcon(getClass().getResource("/assets/playerSprites/playerRUN4.png")).getImage()
        };

        PersoCorrendoE = new Image[] {
                new ImageIcon(getClass().getResource("/assets/playerSprites/playerRUN1-left.png")).getImage(),
                new ImageIcon(getClass().getResource("/assets/playerSprites/playerRUN2-left.png")).getImage(),
                new ImageIcon(getClass().getResource("/assets/playerSprites/playerRUN3-left.png")).getImage(),
                new ImageIcon(getClass().getResource("/assets/playerSprites/playerRUN4-left.png")).getImage()
        };

        PersoPulandoD = new ImageIcon(getClass().getResource("/assets/playerSprites/playerJUMP.png")).getImage();
        PersoPulandoE = new ImageIcon(getClass().getResource("/assets/playerSprites/playerJUMP-left.png")).getImage();
    }

    public void draw(Graphics gh) {

        Image Lado;

        if (jumping) {
            Lado = TrocaTroca ? PersoPulandoD : PersoPulandoE;
        } else if (velX != 0) {
            Lado = TrocaTroca ? PersoCorrendoD[ControleDoIndex] : PersoCorrendoE[ControleDoIndex];
        } else {
            Lado = TrocaTroca ? PersoParadoD[PControleDoIndex] : PersoParadoE[PControleDoIndex];
        }

        // SE GRAVIDADE INVERTIDA, RENDERIZA DE CABEÇA PARA BAIXO
        if (this.g.getDirecao() == -1) {
            gh.drawImage(Lado, x, y + height, x + width, y, 0, 0, Lado.getWidth(null), Lado.getHeight(null), null);
        } else {
            gh.drawImage(Lado, x, y, width, height, null);
        }
    }

    public void update() {
        x += velX;

        if (!onGround) {
            velY += g.getDirecao() * g.getForca();
        }

        if (wantToJump) {
            if (onGround) {
                // Impulso na direção OPOSTA da gravidade vigente
                velY = (int) (-g.getDirecao() * g.getForcaPulo());
                jumping = true;
                onGround = false;
                wantToJump = false; // só reseta aqui
            }
        }

        y += velY;

        // Limitar na tela
        if (x < 0)
            x = 0;
        if (x + width > 800)
            x = 800 - width;

        if (g.getDirection() == 1 && y + height >= 600) {
            y = 600 - height;
            velY = 0;
        } else if (g.getDirection() == -1 && y <= 0) {
            y = 0;
            velY = 0;
        }

        jumping = !onGround;

        if (velX != 0) {
            ContDoFrame++;
            if (ContDoFrame >= IntervaloDoFrame) {
                ControleDoIndex = (ControleDoIndex + 1) % PersoCorrendoD.length;
                ContDoFrame = 0;
            }
        } else {
            PContDoFrame++;
            if (PContDoFrame >= PIntervaloDoFrame) {
                PControleDoIndex = (PControleDoIndex + 1) % PersoParadoD.length;
                PContDoFrame = 0;
            }
        }

    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
            velX = 5;
            TrocaTroca = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
            velX = -5;
            TrocaTroca = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_SPACE
                || e.getKeyCode() == KeyEvent.VK_W) {
            wantToJump = true;
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT
                || e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_A) {
            velX = 0;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_UP
                || e.getKeyCode() == KeyEvent.VK_W) {
            wantToJump = false;
        }

    }

    public void reset() {
        this.x = 70;
        this.y = 500;
        this.velX = 0;
        this.velY = 0;
        this.jumping = false;
        this.onGround = true;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public int getY() {
        return y;
    }

    public int setY(int y) {
        this.y = y;
        return this.y;
    }

    public int getX() {
        return x;
    }

    public int setX(int x) {
        this.x = x;
        return this.x;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
