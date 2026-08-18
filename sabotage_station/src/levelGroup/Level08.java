package levelGroup;

import game.GamePanel;
import game.Level;
import game.objetos.Pistao;
import game.objetos.Platform;
import game.objetos.Player;

public class Level08 extends Level {
    private Player player;
    private GamePanel gamePanel;

    private static int[][] mapa = {
    { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
    { 14, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 13 },
    { 11, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10 },
    { 11, 0, 0, 0, 6, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    { 11, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 6, 0, 0 },
    { 11, 0, 0, 10, 3, 3, 3, 11, 0, 0, 6, 0, 0, 0, 0, 0 },
    { 11, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 3, 0, 0, 0, 10 },
    { 0, 0, 0, 4, 0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 3, 0 },
    { 0, 0, 0, 0, 0, 0, 0, 0, 10, 3, 3, 3, 0, 3, 0, 10 },
    { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    { 0, 0, 0, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    { 6, 1, 1, 1, 6, 8, 1, 8, 1, 8, 1, 8, 1, 8, 6, 6 }
};

    @Override
    protected int[][] getMapa() {
        return mapa;
    }

    public Level08(Player player, GamePanel gamePanel) {
        super(3);
        this.player = player;
        designTraps();
        this.titulo = formatarTitulo("De olho no pixel!", gamePanel.getNumFase());
        this.setTitlePos(titleX, titleY - 10);

        setShowExit(false);
    }

    @Override
    protected void designTraps() {
        Pistao pt1 = (Pistao) mapaObjetos[11][0];
        pt1.forca = 1.45f;

        Pistao pt3 = (Pistao) mapaObjetos[11][14];
        pt3.forca = 3f;

        Pistao pt4 = (Pistao) mapaObjetos[11][15];
        pt4.forca = 3f;

        Pistao pt5 = (Pistao) mapaObjetos[3][6];
        pt5.forca = 0.00f;

        Pistao pt6 = (Pistao) mapaObjetos[11][4];
        pt6.forca = 1f;
    }

}
