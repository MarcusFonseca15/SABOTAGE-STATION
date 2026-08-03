package game;

/**
 * Enum representando os modos de dificuldade do jogo.
 * Ainda em desenvolvimento — integração com StartPanel via starpanelALT.txt (rascunhos/).
 */
public enum ModoJogo {
    NORMAL("Modo Normal: 10 vidas. Experiência padrão do jogo."),
    DIFICIL("Modo Difícil: 5 vidas. Vai encarar?");

    public final String descricao;

    ModoJogo(String descricao) {
        this.descricao = descricao;
    }
}
