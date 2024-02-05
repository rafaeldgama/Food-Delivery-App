package usuarios;

import entities.Avaliacao;
import entities.ObjetoAvaliavel;
import entities.Pedido;
import entities.Pidao;

public class Entregador extends ObjetoAvaliavel {

	private String nome;
	private String CPF;
	private Pedido pedido;
	
	public Entregador(String nome, String CPF){
		this.nome = nome;
		this.CPF = CPF;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getCPF() {
		return CPF;
	}
	
	public Pedido getPedido() {
		return pedido;
	}
	
	public void setPedido(Pedido p) {
		pedido = p;
	}
	
	public void realizaEntrega(Pidao pidaoApp) {
		pidaoApp.finalizaEntrega(pedido);
		pedido = null;
	}
	
	@Override
	public String exibirAvaliacao() {
		if(avaliacoes.size() == 0)
			return "Ainda não existem avaliações para o entregador " + nome + "." + String.format("%n");
		String s = "Existem " + avaliacoes.size() + " avaliações para o entregador " + nome + ": " + String.format("%n");
		double soma = 0;
		for(Avaliacao nota : avaliacoes) {
			soma += nota.getNota();
			s += nota.toString();
		}
		s += String.format("%n") + "Avaliação geral do entregador " + nome + ": " + String.format("%.1f%n", soma/avaliacoes.size());
		return s;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Entregador) {
			Entregador outroEntregador = (Entregador) o;
			return CPF.equals(outroEntregador.CPF);
		}
		else
			return false;
	}

	@Override
	public String toString() {
		return "Entregador: " + nome + " (" + CPF + ")" + String.format("%n") + exibirAvaliacao() + String.format("%n");
	}
	
}
