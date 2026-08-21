package levelGroup;

import game.GamePanel;
import game.Level;
import game.objetos.Pistao;
import game.objetos.Platform;
import game.objetos.Player;

public class Level02 extends Level {
    private Player player;
    private GamePanel gamePanel;

    private static int[][] mapa = {
            { 34, 34, 34, 34, 34, 34, 34, 34, 34, 34, 34, 34, 34, 34, 34, 34 },
            { 0, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 4 },
            { 0, 3, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 6, 0, 4 },
            { 0, 0, 3, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 3, 0, 0, 6, 4 },
            { 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 3, 0, 10 },
            { 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 9, 0, 10 },
            { 0, 0, 0, 0, 2, 0, 0, 0, 3, 3, 3, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 8, 1, 1, 1, 8, 8, 8, 8, 8, 8, 8, 8, 3, 8, 8, 1 }
    };

    @Override
    protected int[][] getMapa() {
        return mapa;
    }

    public Level02(Player player, GamePanel gamePanel) {
        super(3);
        this.player = player;
        designTraps();
        this.titulo = formatarTitulo("Ah, você tava aí, safado?", gamePanel.getNumFase());
        this.setTitlePos(titleX, titleY - 5);
    }

    @Override
    protected void designTraps() {

        Pistao pt2 = (Pistao) mapaObjetos[6][14];
        pt2.forca = 0.9f;
    }

}
