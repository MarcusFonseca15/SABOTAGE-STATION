package game;

import java.awt.Rectangle;
import java.util.List;

import game.objetos.Pistao;
import game.objetos.Platform;
import game.objetos.Player;

public class ColisionManager {

    /**
     * @param player
     * @param platforms        lista de Platform do nível atual
     * @param pistoes          lista de Pistao do nível atual
     * @param direcaoGravidade 1 = normal, -1 = invertida (cai para cima)
     */
    public static void resolverColisoes(
            Player player,
            List<Platform> platforms,
            List<Pistao> pistoes,
            int direcaoGravidade) {

        player.onGround = false;

        for (Platform p : platforms) {
            resolverBlocoColisao(player, p.getBounds(), direcaoGravidade);
        }

        for (Pistao pistao : pistoes) {
            resolverPistaoColisao(player, pistao, direcaoGravidade);
        }
    }

    // Platform ───────────────────────────────────────────────────────────────

    private static void resolverBlocoColisao(Player player, Rectangle bloco, int dir) {
        Rectangle playerBounds = new Rectangle(
                player.getX(), player.getY(), player.getWidth(), player.getHeight());

        if (!playerBounds.intersects(bloco))
            return;

        if (dir == 1) {
            // Gravidade normal, pouso no TOPO do bloco
            if (player.getY() + player.getHeight() - player.velY <= bloco.y) {
                player.setY(bloco.y - player.getHeight());
                player.velY = 0;
                player.jumping = false;
                player.onGround = true;
                return;
            }
            // Cabeça na BASE do bloco
            if (player.getY() - player.velY >= bloco.y + bloco.height) {
                player.setY(bloco.y + bloco.height);
                player.velY = 0;
                return;
            }
        } else { // dir == -1
            // Gravidade invertida, pouso na BASE do bloco
            if (player.getY() - player.velY >= bloco.y + bloco.height) {
                player.setY(bloco.y + bloco.height);
                player.velY = 0;
                player.jumping = false;
                player.onGround = true;
                return;
            }
            // Cabeça no TOPO do bloco (player veio de baixo)
            if (player.getY() + player.getHeight() - player.velY <= bloco.y) {
                player.setY(bloco.y - player.getHeight());
                player.velY = 0;
                return;
            }
        }

        resolverColisaoHorizontal(player, bloco);
    }

    // Pistão──────────────────────────────────────────────────────────────────

    /**
     * Dividido em duas hitboxes:
     * - getBaseBounds(): base fixa de 30 px (parte azul). guard externo + colisão
     * vertical
     * - getBounds(): hitbox dinâmica com altura real do sprite atual. colisão
     * lateral quando estendido
     *
     * Guard externo = getBaseBounds().
     * Se o player não toca a base, NENHUM bloqueio ocorre — o player consegue
     * pular livremente do lado do pistão e pousar no topo quando ultrapassar
     *
     * Ramificação horizontal (só alcançada se vertical não resolveu):
     * 1. player intersecta getBounds() → empurra contra hitbox dinâmica (pistão
     * estendido)
     * 2. caso contrário → empurra contra getBaseBounds() (base fixa)
     */
    private static void resolverPistaoColisao(Player player, Pistao pistao, int dir) {
        Rectangle playerBounds = new Rectangle(
                player.getX(), player.getY(), player.getWidth(), player.getHeight());

        Rectangle baseBounds = pistao.getBaseBounds(); // 30 px fixos na base do tile
        Rectangle extendedBounds = pistao.getBounds(); // altura real do sprite atual

        if (!playerBounds.intersects(baseBounds))
            return;

        // ── colisão vertical contra a base ──────────────────────────────────
        if (dir == 1) {
            // Pouso por CIMA da base (gravidade normal)
            if (player.getY() + player.getHeight() - player.velY <= baseBounds.y) {
                player.setY(baseBounds.y - player.getHeight());
                player.velY = 0;
                player.jumping = false;
                player.onGround = true;
                return;
            }
            // Bate por BAIXO da base
            if (player.getY() - player.velY >= baseBounds.y + baseBounds.height) {
                player.setY(baseBounds.y + baseBounds.height);
                player.velY = 0;
                return;
            }
        } else { // dir == -1 (gravidade invertida)
            // Pouso na BASE do bloco (player "cai" para cima)
            if (player.getY() - player.velY >= baseBounds.y + baseBounds.height) {
                player.setY(baseBounds.y + baseBounds.height);
                player.velY = 0;
                player.jumping = false;
                player.onGround = true;
                return;
            }
            // Bate no TOPO da base (player veio de baixo)
            if (player.getY() + player.getHeight() - player.velY <= baseBounds.y) {
                player.setY(baseBounds.y - player.getHeight());
                player.velY = 0;
                return;
            }
        }

        // ── colisão horizontal (vertical não resolveu) ───────────────────────
        // Prioridade: hitbox dinâmica (estendida) > hitbox da base
        if (playerBounds.intersects(extendedBounds)) {
            resolverColisaoHorizontal(player, extendedBounds);
        } else {
            resolverColisaoHorizontal(player, baseBounds);
        }
    }

    // ─────────────────────────────────────────────────────────────────────────

    // Empurra o player para fora de um bloco na direção horizontal.
    private static void resolverColisaoHorizontal(Player player, Rectangle bloco) {
        if (player.getX() + player.getWidth() - player.velX <= bloco.x) {
            player.setX(bloco.x - player.getWidth());
        } else if (player.getX() - player.velX >= bloco.x + bloco.width) {
            player.setX(bloco.x + bloco.width);
        }
    }
}
