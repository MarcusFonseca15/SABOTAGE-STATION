package levelGroup;

import jogo_de_trap.Gravity;
import jogo_de_trap.Level;
import jogo_de_trap.Platform;
import jogo_de_trap.Pistao;
import jogo_de_trap.Player;
import jogo_de_trap.Objeto;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;

import jogo_de_trap.EspinhosP;

public class Level01 extends Level {

    private Player player;

    // Imagem de instruções
    private Image InstruIMG;
    private boolean showInstructs = true;
    private long imageTime;
    private float instructionAlpha = 1.0f;
    private static final int INSTRUCTION_DURATION = 4000;

    private static int[][] mapa = {
            { 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 111, 111, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 11, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 3, 3, 3, 3, 0, 3, 1, 1, 1, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 112, 4 },
            { 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4 },
            { 0, 0, 0, 3, 3, 3, 3, 3, 3, 0, 0, 0, 3, 0, 0, 4 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 0, 0, 4 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 4 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 4 },
            { 5, 0, 0, 11, 0, 0, 11, 0, 11, 0, 0, 0, 0, 0, 5, 4 },
            { 1, 2, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }
    };

    public Level01(Player player) {
        super(1);
        this.player = player;
        this.player.level = this;
        designTraps();

        InstruIMG = new ImageIcon(getClass().getResource("/assets/imgInstrucoes.png")).getImage();
        imageTime = System.currentTimeMillis();
        // drawInstructions();

        this.titulo = "01. Traps, TRAPS, E MAIS TRAPS!";
        this.setTitlePos(titleX, titleY - 15);

        this.setExitPos(710, 130);

    }

    @Override
    protected int[][] getMapa() {
        return mapa;
    }

    @Override
    protected void designTraps() {

        Pistao pt1 = (Pistao) mapaObjetos[10][14];
        pt1.forca = 1.7f;
        Pistao pt3 = (Pistao) mapaObjetos[10][0];
        pt3.forca = 3.0f;

        // gravidade padrão
        player.g.setGravity(1.0);
        player.g.setPulo(-15);

        EspinhosP spInv1 = (EspinhosP) mapaObjetos[1][0];
        EspinhosP spInv2 = (EspinhosP) mapaObjetos[1][1];
        EspinhosP spInv3 = (EspinhosP) mapaObjetos[4][14];
        EspinhosP spInv5 = (EspinhosP) mapaObjetos[2][5];

        // spInv1.setVisible(false);
        // spInv2.setVisible(false);
        spInv3.setVisible(false);
        // spInv5.setVisible(false);
    }

    public void updateInstructions() {
        if (showInstructs) {
            long elapsed = System.currentTimeMillis() - imageTime;
            if (elapsed >= INSTRUCTION_DURATION) {
                showInstructs = false;
            } else if (elapsed >= INSTRUCTION_DURATION - 1000) { // último segundo
                instructionAlpha = (INSTRUCTION_DURATION - elapsed) / 1000.0f;
            }
        }
    }

    public void drawInstructions(Graphics g) {
        if (showInstructs && InstruIMG != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, instructionAlpha));
            g2d.drawImage(InstruIMG, 0, 0, 800, 600, null);
            g2d.dispose();
        }
    }
}
