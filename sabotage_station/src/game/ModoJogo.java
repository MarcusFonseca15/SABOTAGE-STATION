package game;

/**
 * Enum representando os modos de dificuldade do jogo.
 * Ainda em desenvolvimento — integração com StartPanel via starpanelALT.txt (rascunhos/).
 */
public enum ModoJogo {

    FACIL("Modo Fácil: o jogo terá menos armadilhas e mais tempo para reagir. Ideal para iniciantes."),
    NORMAL("Modo Normal: a experiência padrão do jogo, com todas as armadilhas balanceadas."),
    DIFICIL("Modo Difícil: mais armadilhas, menos margem de erro. Só para os corajosos!");

    public final String descricao;

    ModoJogo(String descricao) {
        this.descricao = descricao;
    }
}
