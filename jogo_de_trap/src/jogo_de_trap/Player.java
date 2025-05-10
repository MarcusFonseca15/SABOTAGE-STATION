package jogo_de_trap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import jogo_de_trap.Gravity;

public class Player {
    int x, y;
    int width = 50, height = 50;

    // public int GRAVITY = 1;
    public Gravity g;

    int velX = 0;
    int velY = 0;
    boolean jumping = false;
    boolean onGround = true;
    boolean wantToJump = false;
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

        // SE GRAVIDADE INVERTIDA, DE CABEÇA PRA BAIXO
        if (this.g.valor == -1) {
            gh.drawImage(Lado, x, y + height, x + width, y, 0, 0, Lado.getWidth(null), Lado.getHeight(null), null);
        } else {
            gh.drawImage(Lado, x, y, width, height, null);
        }
    }

    public void update() {
        x += velX;

        if (!onGround) {
            velY += g.getGravity();
            // ColisionManager.checarColisoes(this, level.getMapaObjetos();
        }

        if (wantToJump) {
            if (onGround) {
                velY = -15;
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

        if (g.valor == 0 && y + height >= 600) {
            y = 600 - height;
            velY = 0;
            // ColisionManager.checarColisoes(this, level.getMapaObjetos();
            onGround = true;
        }

        // fiz isso aqui para atualizar o pulo
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
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            velX = 5;
            TrocaTroca = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            velX = -5;
            TrocaTroca = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            wantToJump = true;
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT) {
            velX = 0;
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

}
