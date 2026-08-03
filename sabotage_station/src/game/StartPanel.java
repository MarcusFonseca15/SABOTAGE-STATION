package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StartPanel extends JPanel {
    private JFrame frame;
    
    // Variáveis de controle do Hover/Seleção
    private ModoJogo modoSelecionado = ModoJogo.NORMAL;
    private boolean[] modoHover = new boolean[2]; // 0=Normal, 1=Dificil
    private final ModoJogo[] valoresModo = {ModoJogo.NORMAL, ModoJogo.DIFICIL};
    private JPanel menuPanel;
    private JButton[] modoButtons;
    
    // Componente de texto para atualizar a descrição
    private JTextArea description;

    // Coordenadas para cálculo do desenho (baseado no seu layout)
    private final int MODES_X = 25;
    private final int MODES_Y = 166;
    private final int BTN_WIDTH = 74; // 224 dividido por 3
    private final int BTN_HEIGHT = 43;

    public StartPanel(JFrame frame) {
        this.frame = frame;
        this.setLayout(null);

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) iniciarJogo();
            }
        });

        // --- Painel Interno ---
        menuPanel = new JPanel();
        menuPanel.setLayout(null);
        menuPanel.setOpaque(false); // Importante para ver o background desenhado no pai
        menuPanel.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2));
        add(menuPanel);

        // centraliza o painel interno sempre que o tamanho do StartPanel mudar
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int pw = 280;
                int ph = 363;
                menuPanel.setBounds((getWidth() - pw) / 2, (getHeight() - ph) / 2 + 40, pw, ph);

                // reajusta posição dos botões de modo para centralizá-los dentro do menuPanel
                if (modoButtons != null) {
                    int gap = 10;
                    int n = modoButtons.length;
                    int totalW = (BTN_WIDTH * n) + (gap * (n - 1));
                    int startX = (menuPanel.getWidth() - totalW) / 2;
                    for (int i = 0; i < n; i++) {
                        modoButtons[i].setBounds(startX + i * (BTN_WIDTH + gap), MODES_Y, BTN_WIDTH, BTN_HEIGHT);
                    }
                    // description e demais componentes permanecem com as mesmas coordenadas relativas
                }
            }
        });

        Font buttonFont = FontManager.getVHSFont(18f);
        // Fonte reduzida em 30% para os botões de modo (Normal / Difícil)
        Font modeButtonFont = buttonFont.deriveFont(buttonFont.getSize2D() * 0.5f);

        JButton btnIniciar = new JButton("Iniciar"); // Placeholder visual
        btnIniciar.setFont(buttonFont);
        btnIniciar.setBounds(25, 33, 224, 48);
        btnIniciar.addActionListener(e -> iniciarJogo());
        btnIniciar.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btnIniciar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); }
            public void mouseExited(MouseEvent e) { btnIniciar.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR)); }
        });
        menuPanel.add(btnIniciar);

        JButton btnConfig = new JButton("Configurações"); // Placeholder visual
        btnConfig.setFont(buttonFont);
        btnConfig.setBounds(25, 102, 224, 43); // y, x, largura, altura
        btnConfig.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btnConfig.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); }
            public void mouseExited(MouseEvent e) { btnConfig.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR)); }
        });
        menuPanel.add(btnConfig);

        // --- Lógica dos 2 Botões de Modo (Normal / Difícil) ---
        String[] labels = {"Normal", "Difícil"};
        
        modoButtons = new JButton[2];
        for (int i = 0; i < 2; i++) {
            final int index = i;
            JButton btn = new JButton(labels[i]);
            btn.setFont(modeButtonFont);
            // Posicionamento inicial; será recalculado no primeiro resize
            btn.setBounds(MODES_X + (i * BTN_WIDTH), MODES_Y, BTN_WIDTH, BTN_HEIGHT);
            
            // Estilo invisível (desenharemos no paintComponent)
            btn.setOpaque(false);
            btn.setContentAreaFilled(false);
            btn.setBorderPainted(false);
            btn.setFocusPainted(false);
            btn.setForeground(Color.WHITE); // Cor do texto do botão

            btn.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    modoHover[index] = true;
                    repaint();
                }
                public void mouseExited(MouseEvent e) {
                    modoHover[index] = false;
                    repaint();
                }
                public void mouseClicked(MouseEvent e) {
                    modoSelecionado = valoresModo[index];
                    description.setText(modoSelecionado.descricao); // Atualiza texto
                    repaint();
                }
            });
            menuPanel.add(btn);
            modoButtons[i] = btn;
        }

        description = new JTextArea();
        description.setBounds(21, 236, 228, 89);
        description.setOpaque(false); // Transparente ou com cor de fundo
        description.setForeground(Color.WHITE);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setText(modoSelecionado.descricao); // Texto inicial
        description.setEditable(false);
        menuPanel.add(description);
    }

    private void iniciarJogo() {
        System.out.println("Iniciando jogo no modo: " + modoSelecionado);
        // Troca para GamePanel e passa o modo selecionado para ajustar vidas
        frame.getContentPane().removeAll();
        GamePanel gp = new GamePanel((GameFrame) frame, modoSelecionado);
        frame.add(gp);
        frame.revalidate();
        frame.repaint();
        // solicita foco no GamePanel para capturar teclas
        javax.swing.SwingUtilities.invokeLater(() -> gp.requestFocusInWindow());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 1. Background Image
        ImageIcon icon = new ImageIcon(getClass().getResource("/assets/telas_e_botoes/StartBG.jpg"));
        Image bg = icon.getImage();
        
        // Sua lógica de escala
        int imgWidth = bg.getWidth(null);
        int imgHeight = bg.getHeight(null);
        int panelWidth = this.getWidth();
        double scaleFactor = (double) panelWidth / imgWidth;
        int scaleAltura = (int) (imgHeight * scaleFactor) - 110;
        
        g.drawImage(bg, 0, 0, panelWidth, scaleAltura, null);

        Graphics2D g2d = (Graphics2D) g.create();

        // 2. Outline dos Botões de Modo (NOVA LÓGICA)
        // Usa a posição atual do menuPanel para desenhar os contornos relativos
        if (menuPanel != null) {
            int absoluteRefY = menuPanel.getY() + MODES_Y;
            int gap = 10;
            int n = valoresModo.length;
            int totalW = (BTN_WIDTH * n) + (gap * (n - 1));
            int startLocalX = menuPanel.getX() + (menuPanel.getWidth() - totalW) / 2;

            for (int i = 0; i < n; i++) {
                if (modoHover[i] || modoSelecionado == valoresModo[i]) {
                    Color c = (modoSelecionado == valoresModo[i]) ? Color.CYAN : Color.WHITE;
                    g2d.setColor(c);
                    g2d.setStroke(new BasicStroke(2));
                    g2d.drawRect(
                        startLocalX + i * (BTN_WIDTH + gap),
                        absoluteRefY,
                        BTN_WIDTH,
                        BTN_HEIGHT
                    );
                }
            }
        }
        g2d.dispose();
    }
}
