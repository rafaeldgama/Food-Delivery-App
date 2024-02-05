package entities;

import java.util.ArrayList;
import java.util.List;

public abstract class ObjetoAvaliavel {
	
	protected List<Avaliacao> avaliacoes = new ArrayList<Avaliacao>();
	
	protected void avaliar(int nota) {
		avaliacoes.add(new Avaliacao(nota));
	}
	
	protected void avaliar(int nota, String comentario) {
		avaliacoes.add(new Avaliacao(nota, comentario));
	}

	protected abstract String exibirAvaliacao();
	
}
