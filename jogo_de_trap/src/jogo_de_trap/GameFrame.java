package jogo_de_trap;

import javax.swing.*;

public class GameFrame extends JFrame {

    Som musicBG;

    public GameFrame() {
        // this.add(new GamePanel());
        this.setTitle("Sabotage Station");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        StartPanel startPanel = new StartPanel(this);
        this.add(startPanel);

        this.pack();
        this.setSize(800, 637);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        tocarMusica();
    }

    private void tocarMusica() {
        musicBG = new Som();
        musicBG.tocar("jogo_de_trap/src/res/musica.wav", -10.0f); // volume em dB
    }
}
