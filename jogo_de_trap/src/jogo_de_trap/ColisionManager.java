package jogo_de_trap;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import jogo_de_trap.objetos.Objeto;
import jogo_de_trap.objetos.Platform;
import jogo_de_trap.objetos.Player;

public class ColisionManager {

    // Leitura do mapa
    public static List<Objeto> extrairObjetos(Objeto[][] mapa) {
        List<Objeto> objetos = new ArrayList<>();
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[i].length; j++) {
                if (mapa[i][j] != null) {
                    objetos.add(mapa[i][j]);
                }
            }
        }
        return objetos;
    }

    public static void checarColisoes(Player player, Objeto[][] mapaObjetos, int direcaoGravidade) {
        if (direcaoGravidade != -1)
            return; // Só age se gravidade for -1

        List<Objeto> objetos = extrairObjetos(mapaObjetos);

        Rectangle topoDoPlayer = new Rectangle(player.getX(), player.getY(), player.getWidth(), player.getHeight() / 2);

        Rectangle baseDoPlayer = new Rectangle(player.getX(), player.getY() + player.getHeight() - 5, player.getWidth(),
                5);

        // Verificar colisões
        for (Objeto obj : objetos) {
            if (topoDoPlayer.intersects(obj.getBounds())) {
                // Ajusta a posição do player pra ficar embaixo da plataforma
                player.setY(obj.getY() + obj.getHeight());
                player.velY = 0;
                player.onGround = true;
                // System.out.println("Colidiu com teto (gravidade invertida)");
            }

            if (topoDoPlayer.intersects(obj.getBounds())) {
                player.setY(obj.getY() + obj.getHeight());
                player.velY = 0;
                player.onGround = true;
            }

            /*
             * if (baseDoPlayer.intersects(obj.getBounds())) {
             * player.setY(obj.getY() - player.getHeight());
             * player.velY = 0;
             * player.onGround = true;
             * }
             */

        }
    }
}
