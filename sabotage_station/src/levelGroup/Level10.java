package levelGroup;

import game.GamePanel;
import game.Level;
import game.objetos.EspinhosP;
import game.objetos.Platform;
import game.objetos.Player;

public class Level10 extends Level {

    Player player;
    private GamePanel gamePanel;

    private static int[][] mapa = {
    { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    { 0, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4 },
    { 0, 0, 0, 0, 0, 0, 0, 0, 8, 0, 0, 0, 0, 0, 8, 4 },
    { 4, 4, 4, 4, 0, 4, 4, 4, 4, 4, 0, 0, 4, 4, 4, 4 },
    { 0, 0, 0, 0, 0, 0, 8, 4, 4, 0, 0, 0, 0, 0, 10, 4 },
    { 0, 4, 4, 4, 4, 4, 4, 4, 0, 0, 4, 4, 0, 0, 4, 4 },
    { 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 4, 0, 0, 0, 0, 4 },
    { 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 0, 0, 4, 4, 0, 4 },
    { 9, 4, 8, 0, 0, 0, 0, 0, 0, 0, 0, 4, 4, 4, 8, 4 },
    { 0, 4, 4, 4, 4, 0, 0, 4, 4, 4, 4, 4, 9, 9, 4, 4 },
    { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 0 },
    { 6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }
};

    @Override
    protected int[][] getMapa() {
        return mapa;
    }

    public Level10(Player player, GamePanel gamePanel) {
        super(3);
        this.player = player;
        designTraps();
        this.titulo = formatarTitulo("Simples assim :)", gamePanel.getNumFase());

        this.setExitPos(650, 350);
        this.setExitSize(83, 48);
    }

    @Override
    protected void designTraps() {

        for (int linha = 0; linha < mapaObjetos.length - 1; linha++) {
            for (int coluna = 0; coluna < mapaObjetos[linha].length; coluna++) {
                if (mapaObjetos[linha][coluna] instanceof Platform) {
                    ((Platform) mapaObjetos[linha][coluna]).setVisible(false);
                }
            }
        }

        EspinhosP e1 = (EspinhosP) mapaObjetos[2][8];
        e1.setVisible(false);

        EspinhosP e2 = (EspinhosP) mapaObjetos[2][14];
        e2.setVisible(false);

        EspinhosP e3 = (EspinhosP) mapaObjetos[4][6];
        e3.setVisible(false);

        EspinhosP e4 = (EspinhosP) mapaObjetos[4][14];
        e4.setVisible(false);

        EspinhosP e5 = (EspinhosP) mapaObjetos[8][0];
        e5.setVisible(false);

        EspinhosP e6 = (EspinhosP) mapaObjetos[8][2];
        e6.setVisible(false);

        EspinhosP e7 = (EspinhosP) mapaObjetos[8][14];
        e7.setVisible(false);

        EspinhosP e8 = (EspinhosP) mapaObjetos[9][12];
        e8.setVisible(false);

        EspinhosP e9 = (EspinhosP) mapaObjetos[9][13];
        e9.setVisible(false);

        EspinhosP e10 = (EspinhosP) mapaObjetos[10][14];
        e10.setVisible(false);
    }

}
