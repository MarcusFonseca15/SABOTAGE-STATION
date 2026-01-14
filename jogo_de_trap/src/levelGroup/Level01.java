package levelGroup;

import jogo_de_trap.Gravity;
import jogo_de_trap.Level;
import jogo_de_trap.objetos.EspinhosP;
import jogo_de_trap.objetos.Objeto;
import jogo_de_trap.objetos.Pistao;
import jogo_de_trap.objetos.Platform;
import jogo_de_trap.objetos.Player;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;

import jogo_de_trap.GamePanel;

public class Level01 extends Level {

    private Player player;
    private GamePanel gamePanel;

    // Imagem de instruções
    private Image InstruIMG;
    private boolean showInstructs = true;
    private long imageTime;
    private float instructionAlpha = 1.0f;
    private static final int INSTRUCTION_DURATION = 4000;
    private boolean skip = false;
    private int skipDUR = 500;

    private boolean showText = true;
    private long textPiscaTime = 0;
    private static final long TEXT_PISCA_DURATION = 500;

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

    public Level01(Player player, GamePanel gamePanel) {
        super(1);
        this.player = player;
        this.player.level = this;
        designTraps();

        InstruIMG = new ImageIcon(getClass().getResource("/assets/telas_e_botoes/imgInstrucoes.png")).getImage();
        imageTime = System.currentTimeMillis();
        // drawInstructions();

        this.titulo = formatarTitulo("Traps, TRAPS, E MAIS TRAPS!", gamePanel.getNumFase());
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
            long elapsed = System.currentTimeMillis() - textPiscaTime;
            if (elapsed >= TEXT_PISCA_DURATION) {
                showText = !showText;
                textPiscaTime = System.currentTimeMillis();
            }
        }
    }

    public void drawInstructions(Graphics g) {

        if (showInstructs && InstruIMG != null) {
            // Imagem
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, instructionAlpha));
            g2d.drawImage(InstruIMG, 0, 0, 800, 600, null);

            // Texto piscando
            if (showText) {
                g2d.setColor(Color.WHITE);
                try {
                    // Nota: O createFont cria a fonte com tamanho 1, o deriveFont define o tamanho
                    // real (24f)
                    java.io.InputStream is = getClass()
                            .getResourceAsStream("/assets/fontes/retro_computer_personal_use.ttf");
                    Font retroFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(22f);
                    g2d.setFont(retroFont);
                } catch (Exception e) {
                    e.printStackTrace();
                    g2d.setFont(new Font("Arial", Font.BOLD, 24));// usar Arial se der erro
                }
                String text = "Pressione qualquer teclar para continuar";
                int textWidth = g2d.getFontMetrics().stringWidth(text);
                g2d.drawString(text, (800 - textWidth) / 2, 550);
            }

            g2d.dispose();
        }
    }

    public void keyPressed(KeyEvent e) {

        if (showInstructs) {
            showInstructs = false;
            player.wantToJump = false;
            return;
        }
    }

}