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
            { 4, 34, 34, 34, 34, 34, 34, 34, 34, 34, 34, 34, 34, 34, 34, 34 },
            { 14, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 13 },
            { 11, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10 },
            { 11, 0, 0, 0, 2, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
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
    }

}
