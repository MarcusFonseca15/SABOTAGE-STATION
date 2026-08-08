package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StartPanel extends JPanel {
    private GameFrame frame;
    
    // Variáveis de controle do Hover/Seleção
    private ModoJogo modoSelecionado = ModoJogo.NORMAL;
    private final ModoJogo[] valoresModo = {ModoJogo.NORMAL, ModoJogo.DIFICIL};
    private JPanel menuPanel;
    private JButton[] modoButtons;

    private Image bgImage;

    private static final Color MENU_BUTTON_BG = new Color(0x00, 0xA4, 0xFF, 77);
    private static final Color MENU_BUTTON_HOVER_BG = new Color(0x59, 0xE1, 0xFF);
    private static final Color MENU_BUTTON_BORDER = Color.CYAN;
    private static final Color MENU_BUTTON_FOREGROUND = Color.WHITE;
    
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

    // Classe própria para o Botão de Modo.
    private class ModeButton extends JButton {
        private final ModoJogo modo;
        private boolean hovered = false;

        public ModeButton(String text, ModoJogo modo) {
            super(text);
            this.modo = modo;
            
            // Estilo invisível
            setOpaque(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setForeground(MENU_BUTTON_FOREGROUND);

            addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    hovered = true;
                    repaint();
                }
                public void mouseExited(MouseEvent e) {
                    hovered = false;
                    repaint();
                }
                public void mouseClicked(MouseEvent e) {
                    modoSelecionado = modo;
                    description.setText(modoSelecionado.descricao); // Atualiza texto
                    for (JButton btn : modoButtons) {
                        btn.repaint();
                    }
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); 
            
            if (hovered || modoSelecionado == modo) {
                Graphics2D g2d = (Graphics2D) g.create();
                Color c = (modoSelecionado == modo) ? Color.CYAN : Color.WHITE;
                g2d.setColor(c);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
                g2d.dispose();
            }
        }
    }

    private final int BTN_WIDTH = 74; 
    private final int BTN_HEIGHT = 43;

    public StartPanel(GameFrame frame) { 
        this.frame = frame;
        
        ImageIcon icon = new ImageIcon(getClass().getResource("/assets/telas_e_botoes/StartBG.jpg"));
        this.bgImage = icon.getImage();

        this.setLayout(new GridBagLayout()); 

        InputMap im = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = this.getActionMap();
        im.put(KeyStroke.getKeyStroke("SPACE"), "iniciarJogo");
        am.put("iniciarJogo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarJogo();
            }
        });

        menuPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                // Desenha manualmente a cor de fundo translúcida
                g2d.setColor(getBackground());
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        
        menuPanel.setOpaque(false); 
        
        menuPanel.setLayout(new GridBagLayout()); 
        menuPanel.setBackground(new Color(0, 0, 0, 70));
        menuPanel.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2));
        menuPanel.setPreferredSize(new Dimension(280, 363));
        
        // Regras (Constraints) para alinhar tudo
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        Font buttonFont = FontManager.getVHSFont(18f);
        Font modeButtonFont = buttonFont.deriveFont(buttonFont.getSize2D() * 0.5f);

        JButton btnIniciar = new TranslucentButton("Iniciar");
        btnIniciar.setPreferredSize(new Dimension(224, 48)); 
        btnIniciar.addActionListener(e -> iniciarJogo());
        configureMenuButton(btnIniciar, buttonFont);
        menuPanel.add(btnIniciar, gbc);

        gbc.gridy++;
        JButton btnConfig = new TranslucentButton("Configurações");
        btnConfig.setPreferredSize(new Dimension(224, 43)); 
        configureMenuButton(btnConfig, buttonFont);
        menuPanel.add(btnConfig, gbc);

        // Lógica dos Botões de Modo
        gbc.gridy++;
        JPanel modosContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        modosContainer.setOpaque(false);
        
        String[] labels = {"Normal", "Difícil"};
        modoButtons = new JButton[valoresModo.length];
        
        for (int i = 0; i < valoresModo.length; i++) {
            ModeButton btn = new ModeButton(labels[i], valoresModo[i]);
            btn.setFont(modeButtonFont);
            btn.setPreferredSize(new Dimension(BTN_WIDTH, BTN_HEIGHT));
            
            modosContainer.add(btn);
            modoButtons[i] = btn;
        }
        menuPanel.add(modosContainer, gbc);

        gbc.gridy++;
        description = new CenteredTextArea();
        description.setPreferredSize(new Dimension(228, 89));
        description.setBackground(new Color(0, 0, 0, 150));
        description.setForeground(Color.WHITE);
        description.setFont(FontManager.getVHSFont(14f));
        description.setText(modoSelecionado.descricao); 
        description.setEditable(false);
        menuPanel.add(description, gbc);

        GridBagConstraints mainGbc = new GridBagConstraints();
        mainGbc.insets = new Insets(40, 0, 0, 0); 
        this.add(menuPanel, mainGbc);
    }

    private void iniciarJogo() {
        System.out.println("Iniciando jogo no modo: " + modoSelecionado);
        
        GamePanel gp = new GamePanel(frame, modoSelecionado);
        
        if (frame.getContentPane().getLayout() instanceof CardLayout) {
            frame.getContentPane().add(gp, "GamePanel");
            CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
            cl.show(frame.getContentPane(), "GamePanel");
        } else {
            frame.getContentPane().removeAll();
            frame.getContentPane().add(gp);
            frame.revalidate();
            frame.repaint();
        }
        
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

        if (bgImage != null) {
            int imgWidth = bgImage.getWidth(null);
            int imgHeight = bgImage.getHeight(null);
            int panelWidth = this.getWidth();
            double scaleFactor = (double) panelWidth / imgWidth;
            int scaleAltura = (int) (imgHeight * scaleFactor) - 110;
            
            g.drawImage(bgImage, 0, 0, panelWidth, scaleAltura, null);
        }
    }
}