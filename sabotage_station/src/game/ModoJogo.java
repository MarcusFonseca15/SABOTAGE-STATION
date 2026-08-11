package game;

public enum ModoJogo {
    FACIL("Modo Fácil: 20 vidas. Para os betinhas."),
    NORMAL("Modo Normal: 10 vidas. Experiência padrão do jogo."),
    DIFICIL("Modo Difícil: 5 vidas. Vai encarar?");

    public final String descricao;

    ModoJogo(String descricao) {
        this.descricao = descricao;
    }
}
