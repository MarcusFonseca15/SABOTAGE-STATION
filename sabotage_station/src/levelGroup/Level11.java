package levelGroup;

import game.GamePanel;
import game.Gravity;
import game.Level;
import game.objetos.Platform;
import game.objetos.Player;

import java.awt.event.KeyEvent;

public class Level11 extends Level {

    private Player player;
    private GamePanel gamePanel;

    // Chão completo (linha 11) e teto completo (linha 0) de plataformas
    private static int[][] mapa = {
        { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, // linha 0: teto
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, // linha 11: chão
    };

    @Override
    protected int[][] getMapa() {
        return mapa;
    }

    public Level11(Player player, GamePanel gamePanel) {
        super(11);
        this.player = player;
        this.gamePanel = gamePanel;

        designTraps();

        this.titulo = formatarTitulo("Teste de Gravidade", gamePanel.getNumFase());
    }

    @Override
    protected void designTraps() {
        // Sem armadilhas — nível de teste puro de gravidade.
        // As teclas I / L / S / N mudam a gravidade via keyPressed().
    }

    /**
     * Chamado pelo GamePanel sempre que uma tecla é pressionada neste nível.
     *
     * I → Gravity.INVERTIDA  (direcao=-1, forca=1.0)
     * L → Gravity.LUNAR      (direcao=+1, forca=0.3)
     * S → Gravity.SOLAR      (direcao=+1, forca=4.0)
     * N → Gravity.NORMAL     (direcao=+1, forca=1.0)
     */
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_I -> {
                player.g.setGravity(-1, 1.0, 15.0);
                System.out.println("[Level11] Gravidade: INVERTIDA");
            }
            case KeyEvent.VK_L -> {
                player.g.setGravity(1, 0.3, 15.0);
                System.out.println("[Level11] Gravidade: LUNAR");
            }
            case KeyEvent.VK_S -> {
                player.g.setGravity(1, 4.0, 15.0);
                System.out.println("[Level11] Gravidade: SOLAR");
            }
            case KeyEvent.VK_N -> {
                player.g.setGravity(1, 1.0, 15.0);
                System.out.println("[Level11] Gravidade: NORMAL");
            }
        }
    }
}
