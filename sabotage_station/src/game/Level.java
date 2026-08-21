package game;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import game.objetos.Espinhos;
import game.objetos.EspinhosP;
import game.objetos.FakeEspinho;
import game.objetos.Graviton;
import game.objetos.Laser;

import game.objetos.Objeto;
import game.objetos.Pistao;
import game.objetos.Platform;
import game.objetos.Player;

public abstract class Level {
    private static final int TILE_SIZE = 50;
    private static final int LIN = 600 / TILE_SIZE; // 12
    private static final int COLS = 800 / TILE_SIZE; // 16
    protected Objeto[][] mapaObjetos = new Objeto[LIN][COLS];

    ArrayList<Platform> platforms = new ArrayList<>();
    ArrayList<Laser> lasers = new ArrayList<>();
    ArrayList<Pistao> pistoes = new ArrayList<>();
    ArrayList<Espinhos> espinhos = new ArrayList<>();
    ArrayList<EspinhosP> espinhosP = new ArrayList<>();
    ArrayList<FakeEspinho> fakeEspinho = new ArrayList<>();
    ArrayList<Graviton> gravitons = new ArrayList<>();

    ////////////// TITULO
    protected String titulo = "";
    protected Color cortitle = Color.WHITE;
    protected int sizeTitle = 27;
    protected int titleX = 60;
    protected int titleY = 43;

    /////////////// IMAGEM DE EXIT
    protected Image exitImage;
    protected int exitX = 670;
    protected int exitY = 100;
    protected int exitWidth = 60;
    protected int exitHeight = 40;
    protected boolean showExit = true;

    protected String formatarTitulo(String titulo, int numFase) {
        return String.format("%02d. %s", numFase, titulo);
    }

    private boolean ativo = true;

    public Level(int number) {
        carregarMapa(getMapa());
        exitImage = new ImageIcon(getClass().getResource("/assets/telas_e_botoes/Exit.png")).getImage();
    }

    private void carregarMapa(int[][] mapa) {
        for (int row = 0; row < LIN; row++) {
            for (int col = 0; col < COLS; col++) {
                int valor = mapa[row][col];
                int x = col * TILE_SIZE;
                int y = row * TILE_SIZE;

                Objeto obj = criarObjetoPorCodigo(valor, x, y);
                if (obj != null) {
                    mapaObjetos[row][col] = obj;

                    if (obj instanceof Platform) {
                        platforms.add((Platform) obj);
                    } else if (obj instanceof Laser) {
                        lasers.add((Laser) obj);
                    } else if (obj instanceof Pistao) {
                        pistoes.add((Pistao) obj);
                    } else if (obj instanceof Espinhos) {
                        espinhos.add((Espinhos) obj);
                    } else if (obj instanceof EspinhosP) {
                        espinhosP.add((EspinhosP) obj);
                    } else if (obj instanceof FakeEspinho) {
                        fakeEspinho.add((FakeEspinho) obj);
                    } else if (obj instanceof Graviton) {
                        gravitons.add((Graviton) obj);
                    }
                }

            }
        }
    }

    public void draw(Graphics g) {
        for (Graviton graviton : gravitons)
            graviton.draw(g);

        for (Platform p : platforms) {
            p.updateGlow();
            p.draw(g);
        }

        for (Laser t : lasers)
            t.draw(g);

        for (Pistao pistao : pistoes)
            pistao.draw(g);

        for (Espinhos espinho : espinhos)
            espinho.draw(g);

        for (EspinhosP espinhosP : espinhosP)
            espinhosP.draw(g);

        for (FakeEspinho fakeEspinho : fakeEspinho)
            fakeEspinho.draw(g);
    }

    public boolean checkLaserCollision(Player player) {
        for (Laser l : lasers) {
            if (l.checkCollision(player))
                return true;
        }

        return false;
    }

    public List<Platform> getPlatforms() {
        return platforms;
    }

    public List<Pistao> getPistoes() {
        return pistoes;
    }

    public boolean checkEspinhosCollision(Player player) {
        for (Espinhos l : espinhos) {
            if (l.checkCollision(player))
                return true;
        }
        return false;
    }

    public boolean checkEspinhosPCollision(Player player) {

        for (EspinhosP l : espinhosP) {
            if (l.checkCollision(player)) {

                if (!l.isVisible()) {
                    l.setVisible(true);

                    // Mostra o espinho quando encostar nele
                    new Thread(() -> {
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        l.setVisible(false);
                    }).start();
                }

                return true;
            }
        }
        return false;
    }

    protected Objeto criarObjetoPorCodigo(int tipo, int x, int y) {

        switch (tipo) {
            case 1, 2, 3, 4: // plataformas metalTile 1–4
                return new Platform(x, y, TILE_SIZE, TILE_SIZE, tipo);
            case 34: // plataforma metalTile5 (invertida)
                return new Platform(x, y, TILE_SIZE, TILE_SIZE, 5);
            case 5: // pistao normal
                return new Pistao(x, y, TILE_SIZE, TILE_SIZE, 1, false);
            case 6: // pistao camuflado
                return new Pistao(x, y, TILE_SIZE, TILE_SIZE, 1, true);
            case 12, 15: // feixe de laser (tipo 1 e tipo 2)
                return new Laser(x, y + 15, TILE_SIZE, 20, tipo);
            case 19: // feixe de laser vertical
                return new Laser(x + 1, y + 15, TILE_SIZE, 60, tipo);
            case 13, 14, 16, 17: // base do laser (dir/esq tipo 1 e tipo 2)
                return new Laser(x, y, TILE_SIZE, TILE_SIZE, tipo);
            case 18, 20: // base do laser (topo/baixo)
                return new Laser(x, y, TILE_SIZE, 75, tipo);
            case 7: // espinhos (animado)
                return new Espinhos(x, y + 30, TILE_SIZE, TILE_SIZE);
            case 8: // espinhosP piso
                return new EspinhosP(x, y + 30, TILE_SIZE, TILE_SIZE, tipo);
            case 9: // espinhosP topo
                return new EspinhosP(x, y - 10, TILE_SIZE, TILE_SIZE, tipo);
            case 10, 11: // espinhosP direita/esquerda
                return new EspinhosP(x, y, TILE_SIZE, TILE_SIZE, tipo);
            case 23, 25, 26: // fakeEspinho (piso, direita, esquerda)
                return new FakeEspinho(x, y + 30, TILE_SIZE, TILE_SIZE, tipo);
            case 24: // fakeEspinho topo
                return new FakeEspinho(x, y - 10, TILE_SIZE, TILE_SIZE, tipo);
            case Graviton.TIPO_NORMAL: // 30 – graviton normal
            case Graviton.TIPO_LUNAR: // 31 – graviton lunar
            case Graviton.TIPO_SOLAR: // 32 – graviton solar
            case Graviton.TIPO_INVERTIDA: // 33 – graviton invertida
                return new Graviton(x, y, TILE_SIZE, TILE_SIZE, tipo);
            default:
                return null;
        }

    }

    public void pararThread() {
        ativo = false;
    }

    public void updatePistaos(Player p) {
        for (Pistao pistao : pistoes) {
            pistao.update(p);
        }
    }

    public void updateEspinhos() {
        for (Espinhos espinho : espinhos) {
            espinho.update();
        }
    }

    public void updateLasers() {
        Laser.updateFrameGlobal();
        for (Laser laser : lasers) {
            laser.update();
        }
    }

    public void updateGravitons(java.awt.Rectangle playerBounds, game.Gravity playerGravity) {
        for (Graviton grav : gravitons) {
            grav.checkAndActivate(playerBounds, playerGravity, gravitons);
        }
    }

    public void resetGravitons() {
        for (Graviton grav : gravitons) {
            grav.desativar();
        }
    }

    public List<Graviton> getGravitons() {
        return gravitons;
    }

    protected abstract int[][] getMapa();

    protected abstract void designTraps();

    public Objeto[][] getMapaObjetos() {
        return mapaObjetos;
    }

    // TITULO
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setCorTitle(Color cor) {
        this.cortitle = cor;
    }

    public void setSizeTitle(int tamanho) {
        this.sizeTitle = tamanho;
    }

    public void setTitlePos(int x, int y) {
        this.titleX = x;
        this.titleY = y;
    }

    public Color getCorTitle() {
        return cortitle;
    }

    public int getSizeTitle() {
        return sizeTitle;
    }

    public int getTitleX() {
        return titleX;
    }

    public int getTitleY() {
        return titleY;
    }

    // SETTERS PARA EXIT
    public void setExitPos(int x, int y) {
        this.exitX = x;
        this.exitY = y;
    }

    public void setExitSize(int width, int height) {
        this.exitWidth = width;
        this.exitHeight = height;
    }

    public void setShowExit(boolean show) {
        this.showExit = show;
    }

    // Getters para EXIT
    public int getExitX() {
        return exitX;
    }

    public int getExitY() {
        return exitY;
    }

    public int getExitWidth() {
        return exitWidth;
    }

    public int getExitHeight() {
        return exitHeight;
    }

    public boolean isShowExit() {
        return showExit;
    }

    public Image getExitImage() {
        return exitImage;
    }

}
