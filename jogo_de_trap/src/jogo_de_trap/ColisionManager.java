package jogo_de_trap;

import jogo_de_trap.Platform;
import jogo_de_trap.Player;
import jogo_de_trap.Objeto;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class ColisionManager {

    // Método para extrair os objetos do mapa
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

    // Método que checa as colisões do Player com objetos no mapa
    public static void checarColisoes(Player player, Objeto[][] mapaObjetos, int direcaoGravidade) {
        if (direcaoGravidade != -1)
            return; // Só age se gravidade for -1 (gravidade invertida)

        // Extrair os objetos do mapa
        List<Objeto> objetos = extrairObjetos(mapaObjetos);

        // Criar um retângulo representando o topo do player (colisão com o teto)
        // Rectangle topoDoPlayer = new Rectangle(player.x, player.y, player.width, 5);
        Rectangle topoDoPlayer = new Rectangle(player.x, player.y, player.width, player.height / 2);

        Rectangle baseDoPlayer = new Rectangle(player.x, player.y + player.height - 5, player.width, 5);

        // Verificar colisões
        for (Objeto obj : objetos) {
            // Se o player colidir com uma plataforma
            if (topoDoPlayer.intersects(obj.getBounds())) {
                // Ajustar a posição do player para estar "abaixo" da plataforma
                player.y = obj.y + obj.height;
                player.velY = 0; // Zerando a velocidade vertical (parando o movimento)
                player.onGround = true; // Indica que o player está "no chão"
                // System.out.println("Colidiu com teto (gravidade invertida)");
                // Parar a verificação após a primeira colisão
            }

            if (topoDoPlayer.intersects(obj.getBounds())) {
                player.y = obj.y + obj.height;
                player.velY = 0;
                player.onGround = true;
            }

        }
    }
}
