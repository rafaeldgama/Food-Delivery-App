package entities;

import enums.TipoDesconto;

public class Item extends ObjetoAvaliavel {
	
	private String nome;
	private Double precoBase;
	private Double precoDesconto;
	private String codigoAlfaNumerico;
	
	public Item(String codigo, String nome, double preco) {
		this.nome = nome;
		precoBase = preco;
		precoDesconto = preco;
		codigoAlfaNumerico = codigo;
	}
	
	public String getNome() {
		return nome;
	}
	
	public Double getPreco() {
		return precoBase;
	}
	
	public Double getPrecoDesconto() {
		return precoDesconto;
	}
	
	public String getcodigoAlfaNumerico() {
		return codigoAlfaNumerico;
	}
	
	void removeDesconto() {
		precoDesconto = precoBase;
	}
	
	void aplicaDesconto(double valor, TipoDesconto desconto) {
		removeDesconto(); // remove desconto anterior, se houver
		if(desconto == TipoDesconto.PORCENTAGEM)
			precoDesconto *= (1-(valor/100));
		else
			precoDesconto -= valor;
	}
	
	@Override
	public String exibirAvaliacao() {
		if(avaliacoes.size() == 0)
			return "Ainda não existem avaliações para o lanche " + nome + "." + String.format("%n");
		String s = "Existem " + avaliacoes.size() + " avaliações para o lanche " + nome + ":" + String.format("%n");
		double soma = 0;
		for(Avaliacao nota : avaliacoes) {
			soma += nota.getNota();
			s += nota.toString();
		}
		s += String.format("%n") + "Avaliação geral do lanche " + nome + ":" + String.format("%.1f%n", soma/avaliacoes.size());
		return s;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Item) {
			Item outroItem = (Item) o;
			return codigoAlfaNumerico.equalsIgnoreCase(outroItem.codigoAlfaNumerico);
		}
		else
			return false;
	}

	public String toString() {
		if(precoBase.compareTo(precoDesconto) == 0)
			return "[" + codigoAlfaNumerico + "] " + nome + " R$ " + String.format("%.1f", precoBase)
				+ String.format("%n") + exibirAvaliacao();
		else
			return "[" + codigoAlfaNumerico + "] " + nome + " R$ " + String.format("%.1f", precoDesconto)
				+ " (PROMOÇÃO! Preço normal: R$ " + String.format("%.1f", precoBase) + ")"
				+ String.format("%n") + exibirAvaliacao();
	}
	
}
