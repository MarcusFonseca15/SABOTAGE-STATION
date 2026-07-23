package game;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FinalPanel extends JPanel {

    private static final String RECORD_FILE = "record.txt";

    private JFrame frame;
    boolean win;
    boolean isHover = false;
    JButton actionButton;
    private String scoreTexto;
    private String recordTexto;

    FinalPanel(JFrame frame, boolean win, String scoreTexto) {
        this.frame = frame;
        this.win = win;
        this.scoreTexto = scoreTexto;
        this.recordTexto = carregarRecord();
        this.setLayout(null);

        // background
        String backgroundPath = win ? "/assets/telas_e_botoes/VictoryBG.jpg" : "/assets/telas_e_botoes/GameOverBG.jpg";
        ImageIcon icon = new ImageIcon(getClass().getResource(backgroundPath));
        Image bg = icon.getImage();

        // botão
        ImageIcon resizedIcon = new ImageIcon(
                new ImageIcon(getClass().getResource(
                        win ? "/assets/telas_e_botoes/btnPlayAgain.png" : "/assets/telas_e_botoes/btnTryAgain.png"))
                        .getImage().getScaledInstance(180, 70, Image.SCALE_SMOOTH));
        this.actionButton = new JButton(resizedIcon);
        actionButton.setOpaque(false);
        actionButton.setContentAreaFilled(false);
        actionButton.setBorderPainted(false);
        actionButton.setFocusPainted(false);
        actionButton.setBounds(300, 500, 180, 70);

        setupButton(actionButton);
        setupHoverEffect();
    } // <-- fim do construtor

    private String carregarRecord() {
        try {
            Path path = Paths.get(RECORD_FILE);
            if (Files.exists(path)) {
                String conteudo = Files.readString(path, StandardCharsets.UTF_8).trim();
                if (!conteudo.isEmpty()) {
                    return conteudo;
                }
            }
        } catch (IOException e) {
            System.out.println("Não foi possível ler o record: " + e.getMessage());
        }
        return "00:00:00";
    }

    private void salvarRecordSeNecessario() {
        try {
            Path path = Paths.get(RECORD_FILE);
            String recordAtual = carregarRecord();
            if (scoreTexto.compareTo(recordAtual) < 0) {
                Files.writeString(path, scoreTexto + System.lineSeparator(), StandardCharsets.UTF_8,
                        StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
                recordTexto = scoreTexto;
            }
        } catch (IOException e) {
            System.out.println("Não foi possível salvar o record: " + e.getMessage());
        }
    }

    private void setupHoverEffect() {

        actionButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                actionButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                isHover = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                actionButton.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                isHover = false;
                repaint();
            }
        });
    } // setupHoverEffect

    private void setupButton(JButton actionButton) {
        // x, y, largura, altura

        actionButton.addActionListener(e -> {
            if (win) {
                frame.getContentPane().removeAll();
                StartPanel startPanel = new StartPanel(frame);
                frame.add(startPanel);
                frame.revalidate();
                frame.repaint();
            } else {
                frame.getContentPane().removeAll();
                GamePanel gamePanel = new GamePanel((GameFrame) frame);
                // Forçar início na fase 1
                gamePanel.currentLevel = 1;
                frame.add(gamePanel);
                frame.revalidate();
                frame.repaint();
                gamePanel.requestFocusInWindow();
            }
        });

        this.add(actionButton);
    } // setupbutton

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // background
        String backgroundPath = win ? "/assets/telas_e_botoes/VictoryBG.jpg" : "/assets/telas_e_botoes/GameOverBG.jpg";
        ImageIcon icon = new ImageIcon(getClass().getResource(backgroundPath));
        Image bg = icon.getImage();

        int imgWidth = bg.getWidth(null);
        int imgHeight = bg.getHeight(null);

        int panelWidth = this.getWidth();

        // fixar o topo da img no topo do painel
        double scaleFactor = (double) panelWidth / imgWidth;
        int scaleAltura = (int) (imgHeight * scaleFactor) - 110;

        g.drawImage(bg, 0, 0, panelWidth, scaleAltura, null);

        salvarRecordSeNecessario();

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        String textoScore = "Seu Score foi: " + scoreTexto + ". Record: " + recordTexto;
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(textoScore);
        int textX = (getWidth() - textWidth) / 2;
        int textY = 120;
        g2d.drawString(textoScore, textX, textY);
        g2d.dispose();

        // Outline de botão
        if (isHover) {
            Graphics2D g2dButton = (Graphics2D) g.create();
            g2dButton.setColor(Color.WHITE);
            g2dButton.setStroke(new BasicStroke(3));
            // botão é (300, 500, 180, 70);
            int m = 1; // margem
            g2dButton.drawRect(300 - m, 500 - m, 180 + (2 * m), 70 + (2 * m)); // 4px de margem
            g2dButton.dispose();
        }
    } // fim paint component

}
