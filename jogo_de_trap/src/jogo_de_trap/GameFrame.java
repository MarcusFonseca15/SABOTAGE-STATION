package jogo_de_trap;

import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.net.URL;
import java.util.Random;

import javax.swing.*;

//Aqui onde tudo inicia
public class GameFrame extends JFrame {

    Som musicBG;

    private Point posicaoOriginal;
    private boolean vibrando = false;

    public GameFrame() {
        this.setTitle("Sabotage Station");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        StartPanel startPanel = new StartPanel(this);
        this.add(startPanel);

        this.pack();
        this.setSize(800, 637);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        // Icone na barra de título
        URL path = this.getClass().getResource("/assets/icone_Janela.png");
        Image icone = Toolkit.getDefaultToolkit().getImage(path);
        this.setIconImage(icone);

        tocarMusica();
    }

    private void tocarMusica() {
//
        musicBG = new Som();
        musicBG.tocar("/assets/musica/musica.wav", -1.0f); // volume em dB

    }

    public void vibrarTela(int duracao, int intensidade) {
        if (vibrando)
            return; // Evita vibrar sem parar

        vibrando = true;
        posicaoOriginal = this.getLocation();

        new Thread(() -> {
            try {
                long inicio = System.currentTimeMillis();
                Random random = new Random();

                while (System.currentTimeMillis() - inicio < duracao) {
                    int offsetX = random.nextInt(intensidade * 2) - intensidade;
                    int offsetY = random.nextInt(intensidade * 2) - intensidade;

                    this.setLocation(posicaoOriginal.x + offsetX, posicaoOriginal.y + offsetY);
                    Thread.sleep(50); // 20 FPS vibraçao
                }

                // volta pro posicao original
                this.setLocation(posicaoOriginal);
                vibrando = false;
            } catch (InterruptedException e) {
                this.setLocation(posicaoOriginal);
                vibrando = false;
            }
        }).start();
    }

    public static void main(String[] args) {
        new GameFrame();
    }
}
