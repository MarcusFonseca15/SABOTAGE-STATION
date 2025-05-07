package jogo_de_trap;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class ColisionManager {

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

    public static void checarColisoes(Player player, Objeto[][] mapaObjetos) {
        Rectangle jogador = new Rectangle(player.x, player.y, player.width, player.height);

        for (Objeto obj : extrairObjetos(mapaObjetos)) {
            if (jogador.intersects(obj.getBounds())) {
                Rectangle objRect = obj.getBounds();

                // Colisão por baixo (player caindo)
                if (player.velY > 0 && jogador.y + jogador.height <= objRect.y + player.velY) {
                    player.y = objRect.y - player.height;
                    player.velY = 0;
                    player.onGround = true;
                }

                // Colisão por cima (gravidade invertida)
                else if (player.velY < 0 && jogador.y >= objRect.y + objRect.height - player.velY) {
                    player.y = objRect.y + objRect.height;
                    player.velY = 0;
                    player.onGround = true;
                }

                // Colisão lateral esquerda
                else if (player.velX > 0 && jogador.x + jogador.width <= objRect.x + player.velX) {
                    player.x = objRect.x - player.width;
                }

                // Colisão lateral direita
                else if (player.velX < 0 && jogador.x >= objRect.x + objRect.width - player.velX) {
                    player.x = objRect.x + objRect.width;
                }
            }
        }
    }
}
