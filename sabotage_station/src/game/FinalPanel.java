package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FinalPanel extends JPanel {

    private static final String RECORD_FILE = "record.txt";
    private static final String DEFAULT_RECORD = "99:99:99"; // Sentinela para quando ainda não há recorde registrado

    private JFrame frame;
    private boolean win;
    private boolean isHover = false;
    private JButton actionButton;
    private String scoreTexto;
    private String recordTexto;
    private Image bgImage;

    FinalPanel(JFrame frame, boolean win, String scoreTexto) {
        this.frame = frame;
        this.win = win;
        this.scoreTexto = scoreTexto;
        this.setLayout(null);

        // 1. Carregar o recorde de vitória existente do arquivo
        this.recordTexto = carregarRecord();

        // 2. Salvar/Atualizar recorde APENAS SE FOR VITÓRIA
        if (this.win) {
            salvarRecordSeNecessario();
        }

        // 3. Pré-carregar imagem de fundo
        String backgroundPath = win ? "/assets/telas_e_botoes/VictoryBG.jpg" : "/assets/telas_e_botoes/GameOverBG.jpg";
        ImageIcon icon = new ImageIcon(getClass().getResource(backgroundPath));
        this.bgImage = icon.getImage();

        // 4. Configurar o botão da tela
        String buttonPath = win ? "/assets/telas_e_botoes/btnPlayAgain.png" : "/assets/telas_e_botoes/btnTryAgain.png";
        ImageIcon buttonIcon = new ImageIcon(getClass().getResource(buttonPath));
        Image scaledBtnImg = buttonIcon.getImage().getScaledInstance(180, 70, Image.SCALE_SMOOTH);
        
        this.actionButton = new JButton(new ImageIcon(scaledBtnImg));
        actionButton.setOpaque(false);
        actionButton.setContentAreaFilled(false);
        actionButton.setBorderPainted(false);
        actionButton.setFocusPainted(false);
        actionButton.setBounds(300, 500, 180, 70);

        setupButton(actionButton);
        setupHoverEffect();
    }

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
        return DEFAULT_RECORD;
    }

    private void salvarRecordSeNecessario() {
        try {
            Path path = Paths.get(RECORD_FILE);
            
            // Se não tinha recorde prévio ou o tempo de vitória atual for menor que o recorde antigo
            if (recordTexto.equals(DEFAULT_RECORD) || scoreTexto.compareTo(recordTexto) < 0) {
                Files.writeString(path, scoreTexto + System.lineSeparator(), StandardCharsets.UTF_8,
                        StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
                this.recordTexto = scoreTexto; // Atualiza a variável local para exibir o novo recorde na tela
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
    }

    private void setupButton(JButton actionButton) {
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
                gamePanel.currentLevel = 1;
                frame.add(gamePanel);
                frame.revalidate();
                frame.repaint();
                gamePanel.requestFocusInWindow();
            }
        });

        this.add(actionButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Renderizar fundo
        int imgWidth = bgImage.getWidth(null);
        int imgHeight = bgImage.getHeight(null);
        int panelWidth = this.getWidth();

        if (imgWidth > 0) {
            double scaleFactor = (double) panelWidth / imgWidth;
            int scaleAltura = (int) (imgHeight * scaleFactor) - 110;
            g.drawImage(bgImage, 0, 0, panelWidth, scaleAltura, null);
        }

        // Renderizar texto informativo
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 22));
        
        String exibiRecord = recordTexto.equals(DEFAULT_RECORD) ? "--:--:--" : recordTexto;
        String textoScore;

        if (win) {
            textoScore = "Seu Tempo: " + scoreTexto + "  |  Recorde: " + exibiRecord;
        } else {
            textoScore = "Você durou: " + scoreTexto + "  |  Recorde: " + exibiRecord;
        }
        
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(textoScore);
        int textX = (getWidth() - textWidth) / 2;
        int textY = 30;
        g2d.drawString(textoScore, textX, textY);
        g2d.dispose();

        // Destaque (Outline) no botão ao passar o mouse
        if (isHover) {
            Graphics2D g2dButton = (Graphics2D) g.create();
            g2dButton.setColor(Color.WHITE);
            g2dButton.setStroke(new BasicStroke(3));
            int m = 1;
            g2dButton.drawRect(300 - m, 500 - m, 180 + (2 * m), 70 + (2 * m));
            g2dButton.dispose();
        }
    }
}