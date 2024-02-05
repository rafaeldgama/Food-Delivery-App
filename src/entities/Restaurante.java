package entities;

import java.util.ArrayList;
import java.util.List;

import usuarios.Entregador;

public class Restaurante extends ObjetoAvaliavel {

	private String nome;
	private String CNPJ;
	private Double[] posicao = new Double[2];
			Cardapio cardapio = new Cardapio();
			List<Entregador> entregadores = new ArrayList<Entregador>();
	
	public Restaurante(String nome, String CNPJ, double x, double y) {
		this.nome = nome;
		this.CNPJ = CNPJ;
		posicao[0] = x;
		posicao[1] = y;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getCNPJ() {
		return CNPJ;
	}
	
	public Double[] getPosicao() {
		return posicao;
	}
	
	Entregador buscaEntregador(Entregador entregador) {
		return entregadores.stream().filter(x -> x.equals(entregador)).findAny().orElse(null);
	}
	
	/* O método a seguir aloca o primeiro entregador da lista para um pedido e depois
	 * adiciona-o ao final do lista.
	 */
	
	Entregador alocaEntregador() {
		Entregador e = entregadores.stream().filter(x -> x.getPedido() == null).findFirst().orElse(null);
		if(e != null) {
		entregadores.remove(e);
		entregadores.add(e);
			return e;
		}
		else {
			System.out.println("Todos os entregadores encontram-se ocupados. Não foi possível alocar o pedido.");
			return null;
		}
	}
	
	@Override
	protected String exibirAvaliacao() {
		if(avaliacoes.size() == 0)
			return "Ainda não existem avaliações para o restaurante " + nome + "." + String.format("%n");
		String s = "Existem " + avaliacoes.size() + " avaliações para o restaurante " + nome + ": " + String.format("%n");
		double soma = 0;
		for(Avaliacao nota : avaliacoes) {
			soma += nota.getNota();
			s += nota.toString() + String.format("%n");
		}
		s += "Avaliação geral do restaurante: " + String.format("%.1f%n", soma/avaliacoes.size());
		s += "-------------------------- Avaliação dos lanches --------------------------" + String.format("%n");
		s += cardapio.exibirAvaliacaoDosLanches() + String.format("%n");
		s += "-------------------------- Avaliação dos entregadores --------------------------" + String.format("%n");
		for (Entregador e : entregadores)
			s += e.exibirAvaliacao();
		return s;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Restaurante) {
			Restaurante r = (Restaurante) o;
			return (nome.equals(r.nome) && CNPJ.equals(r.CNPJ) && (posicao[0] == r.posicao[0]) && (posicao[1] == r.posicao[1]));
		}
		else
			return false;
	}
	
	@Override
	public String toString() {
		return "Restaurante "
				+ nome
				+ String.format("%n")
				+ "(" + "CNPJ: " + CNPJ + ")";
	}
	
}
