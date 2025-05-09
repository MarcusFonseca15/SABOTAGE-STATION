package levelGroup;

import jogo_de_trap.Level;
import jogo_de_trap.Pistao;
import jogo_de_trap.Platform;
import jogo_de_trap.Player;
import jogo_de_trap.Pistao;

public class Level2 extends Level {

    private Player player;
	
    private static int[][] mapa = {

            { 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4 },
            { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 0, 0, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 0, 0 },
            { 1, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 0, 1, 1, 0, 0, 0, 11, 11, 11, 0, 0, 0, 0, 11, 1 },
            { 1, 9, 7, 8, 2, 0, 0, 1, 2, 3, 0, 0, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 0, 0, 0, 0, 0, 11, 11, 11, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 1, 1, 5, 5, 1, 1, 1, 1, 1, 6, 1, 1, 1, 5, 4 }

    };

    @Override
    protected int[][] getMapa() {
        return mapa;
    }

    public Level2(Player player) {
        super(2);

    }

    @Override
    protected void designTraps() {


        Pistao pistaoForte = (Pistao) mapaObjetos[11][14];
        pistaoForte.forca = 1.5f;

        System.out.println("pistaoForte.forca = " + pistaoForte.forca);

        Pistao pistCam = (Pistao) mapaObjetos[11][10];
        pistCam.forca = 1.5f;
        System.out.println("pistCam.forca = " + pistCam.forca);

    }

}
