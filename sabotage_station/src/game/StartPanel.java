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

    private static final Color MENU_BUTTON_BG = new Color(0x00, 0xA4, 0xFF, 77);
    private static final Color MENU_BUTTON_HOVER_BG = new Color(0x59, 0xE1, 0xFF);
    private static final Color MENU_BUTTON_BORDER = Color.CYAN;
    private static final Color MENU_BUTTON_FOREGROUND = Color.WHITE;
    
    // Componente de texto para atualizar a descrição
    private JTextArea description;

    private static class TranslucentButton extends JButton {
        public TranslucentButton(String text) {
            super(text);
            setOpaque(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setHorizontalAlignment(SwingConstants.CENTER);
            setVerticalAlignment(SwingConstants.CENTER);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(getBackground());
            g2d.fillRect(0, 0, getWidth(), getHeight());
            g2d.dispose();
            super.paintComponent(g);
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(MENU_BUTTON_BORDER);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
            g2d.dispose();
        }
    }

    private static class CenteredTextArea extends JTextArea {
        public CenteredTextArea() {
            setOpaque(false);
            setLineWrap(true);
            setWrapStyleWord(true);
            setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 4, 0, 0, Color.CYAN),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
            ));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(getBackground());
            g2d.fillRect(0, 0, getWidth(), getHeight());

            int lineHeight = getFontMetrics(getFont()).getHeight();
            int lineCount = getLineCount();
            int textHeight = lineHeight * lineCount;
            Insets insets = getInsets();
            int availableHeight = getHeight() - insets.top - insets.bottom;
            int yOffset = Math.max(0, (availableHeight - textHeight) / 2);
            g2d.translate(0, yOffset);
            super.paintComponent(g2d);
            g2d.dispose();
        }
    }

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

        JButton btnIniciar = new TranslucentButton("Iniciar");
        btnIniciar.setBounds(25, 33, 224, 48);
        btnIniciar.addActionListener(e -> iniciarJogo());
        configureMenuButton(btnIniciar, buttonFont);
        menuPanel.add(btnIniciar);

        JButton btnConfig = new TranslucentButton("Configurações");
        btnConfig.setBounds(25, 102, 224, 43); // y, x, largura, altura
        configureMenuButton(btnConfig, buttonFont);
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

        description = new CenteredTextArea();
        description.setBounds(21, 236, 228, 89);
        description.setBackground(new Color(0, 0, 0, 150));
        description.setForeground(Color.WHITE);
        description.setFont(FontManager.getVHSFont(14f));
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

    private void configureMenuButton(JButton btn, Font font) {
        btn.setFont(font);
        btn.setForeground(MENU_BUTTON_FOREGROUND);
        btn.setBackground(MENU_BUTTON_BG);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(MENU_BUTTON_HOVER_BG);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(MENU_BUTTON_BG);
            }
        });
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
