package entities;

import java.util.ArrayList;
import java.util.List;

public class Cardapio {
	
	private List<Item> cardapio = new ArrayList<Item>();
	
	public Cardapio() {
		
	}
	
	public Cardapio(Item primeiroItem) {
		adicionaItem(primeiroItem);
	}
	
	boolean adicionaItem(Item item) {
		if(cardapio.isEmpty()) {
			cardapio.add(item);
			return true;
		}
		/* Aqui procuramos se j� existe algum item com o mesmo c�digo alfanum�rico no card�pio */
		else {
			Item aux = cardapio.stream().filter(x -> x.getcodigoAlfaNumerico().equalsIgnoreCase(item.getcodigoAlfaNumerico())).findAny().orElse(null);
			if(aux == null) {
				cardapio.add(item);
				return true;
			}
			return false;
		}
	}
	
	boolean removeItem(Item item) {
		if(cardapio.isEmpty()) {
			return false;
		}
		else {
			if(cardapio.removeIf(x -> x.equals(item)))
				return true;
			return false;
		}
	}
	
	boolean removeItem(String alfaNumerico) {
		if(cardapio.isEmpty())
			return false;
		else {
			if(cardapio.removeIf(x -> x.getcodigoAlfaNumerico().equalsIgnoreCase(alfaNumerico)))
				return true;
			return false;
		}
	}
	
	/* Aqui temos uma fun��o que busca o lanche no card�pio utilizando seu c�digo alfanum�rico */
	
	Item buscaLanche(String codigo) {
		return cardapio.stream().filter(x -> x.getcodigoAlfaNumerico().equals(codigo)).findAny().orElse(null);
	}
	
	Item buscaLanche(Item lanche) {
		return cardapio.stream().filter(x -> x.equals(lanche)).findAny().orElse(null);
	}
	
	/* A fun��o abaixo retorna os itens do card�pio em forma de String para serem impressos */
	
	public String exibirAvaliacaoDosLanches() {
		String s = "";
		for(Item lanche : cardapio)
			s += lanche.exibirAvaliacao();
		return s;
	}
	
	@Override
	public String toString() {
		String aux = "";
		for(Item lanche : cardapio)
			aux += lanche.toString() + String.format("%n");
		return aux;
	}

}
