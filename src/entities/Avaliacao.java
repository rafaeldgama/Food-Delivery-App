package entities;

public class Avaliacao {
	
	private Integer nota;
	private String comentario;
	
	public Avaliacao(int nota) {
		this.nota = nota;
		comentario = "";
	}
	
	public Avaliacao(int nota, String comentario) {
		this.nota = nota;
		this.comentario = comentario;
	}
	
	public Integer getNota() {
		return nota;
	}
	
	public String getComentario() {
		return comentario;
	}
	
	@Override
	public String toString() {
		return String.format("Nota : %d%n", nota) + comentario + String.format("%n");
	}

}
