package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import enums.StatusPedido;
import enums.TipoPedido;
import usuarios.Cliente;
import usuarios.Entregador;

public class Pedido {

	private Cliente cliente;
	private Entregador entregador;
	private Restaurante restaurante;
	private List<Item> pedido = new ArrayList<Item>();
	private Double precoTotal;
	private TipoPedido tipoDoPedido;
	private	StatusPedido status;
	private Scanner sc = new Scanner(System.in);
	
	public Pedido(Cliente usuario, Restaurante r, TipoPedido tipo) {
		cliente = usuario;
		restaurante = r;
		precoTotal = 0.0;
		tipoDoPedido = tipo;
		status = StatusPedido.NOVO;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	
	public Entregador getEntregador() {
		return entregador;
	}
	
	void setEntregador(Entregador e) {
		entregador = e;
	}
	
	public Restaurante getRestaurante() {
		return restaurante;
	}
	
	public Double getPrecoTotal() {
		return precoTotal;
	}
	
	public TipoPedido getTipoDoPedido() {
		return tipoDoPedido;
	}
	
	public StatusPedido getStatus() {
		return status;
	}
	
	public void setStatus(StatusPedido novoStatus) {
		status = novoStatus;
	}
	
	public void addItem(Item lanche) {
		Item i = restaurante.cardapio.buscaLanche(lanche);
		if(i != null)
			pedido.add(lanche);
		else
			System.out.println("Pedido não existente no cardápio do restaurante " + restaurante.getNome() + ".");
	}
	
	public void removeItem(Item lanche) {
		pedido.remove(lanche);
	}
	
	private Double calculaTaxaDeEntrega() {
		if (tipoDoPedido == TipoPedido.RETIRADA)
			return 0.0;
		else {
			double dX = cliente.getEndereco()[0] - restaurante.getPosicao()[0];
			double dY = cliente.getEndereco()[1] - restaurante.getPosicao()[1];
			return 0.5*Math.sqrt((dX*dX) + (dY*dY));
		}
	}
	
	public void calculaPrecoTotal() {
		if(status == StatusPedido.EM_PREPARACAO) {
			for(Item lanche : pedido) {
				/* Se o preço desconto do lanche for menor que o preço base */
				if(lanche.getPreco().compareTo(lanche.getPrecoDesconto()) > 0)
					precoTotal += lanche.getPrecoDesconto();
				else
					precoTotal += lanche.getPreco();
			}
			if(cliente.getQuantidadeDePedidosRealizados() == 0) {
				precoTotal *= 0.8; // desconto de 20% no caso da primeira compra
				cliente.realizaPedido();
			}
			/*Aqui verificamos se a quantidade de pedidos realizados é um múltiplo de 9,
			 * o que significa que o cliente agora está realizando sua próxima décima compra */
			else if(cliente.getQuantidadeDePedidosRealizados()%9 == 0) {
				if(precoTotal > 60)
					precoTotal -= 60;
				else
					precoTotal = 0.0;
				cliente.realizaPedido();
			}
			else {
				/* Se o preço total for maior que R$ 100, 10% de desconto */
				if(precoTotal > 100)
					precoTotal *= 0.9;
				cliente.realizaPedido();
			}
			precoTotal += calculaTaxaDeEntrega();
		}
	}
	
	void avaliarLanches() {
			char c;
			int nota;
			String comentario;
		for(Item lanche : pedido) {
			System.out.print("Insira uma nota de 0 a 10 para o lanche " + lanche.getNome() + ": ");
			nota = sc.nextInt();
			sc.nextLine();
			System.out.print("Deseja inserir um comentário? (s/n) ");
			c = sc.next().charAt(0);
			if(c == 's') {
				sc.nextLine();
				comentario = sc.nextLine();
				lanche.avaliar(nota, comentario);
			}
			else
				lanche.avaliar(nota);
		}
	}
	
	@Override
	public String toString() {
		String s = "";
		s = cliente.toString() + String.format("%n") + "Restaurante: " + restaurante.getNome() + String.format("%n");
		for (Item lanche : pedido)
			s += "- " + lanche.getcodigoAlfaNumerico() + "(" + lanche.getNome() + ")" + String.format("%n");
		s += "Valor total: R$ " + String.format("%.1f", precoTotal);
		return s;
	}
	
}
