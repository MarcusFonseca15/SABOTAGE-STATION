package game;

import java.awt.Color;

public enum ModoJogo {
    TREINO("Modo Treino: 20 vidas. Ideal para aprender a sobreviver.", new Color(0x00, 0xFF, 0xFF)), // ciano
    NORMAL("Modo Normal: 10 vidas. Experiência padrão do jogo.", new Color(0x00, 0xFF, 0xFF)), // ciano
    DIFICIL("Modo Difícil: 5 vidas. Vai encarar?", new Color(0xFF, 0x30, 0x30)); // vermelho

    public final String descricao;

    public final Color cor;

    ModoJogo(String descricao, Color cor) {
        this.descricao = descricao;
        this.cor = cor;
    }
}
