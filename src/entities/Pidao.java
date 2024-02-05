package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import enums.StatusPedido;
import enums.TipoDesconto;
import enums.TipoPedido;
import usuarios.Cliente;
import usuarios.Entregador;

public class Pidao {
	
	private List<Restaurante> restaurantes = new ArrayList<Restaurante>();
	private List<Pedido> pedidos = new ArrayList<Pedido>();
	private List<Cliente> cadastroClientes = new ArrayList<Cliente>();
	Scanner sc = new Scanner(System.in);
	
	public Pidao() {
		
	}
	
	private Cliente buscaCliente(Cliente cliente) {
		return cadastroClientes.stream().filter(x -> x.equals(cliente)).findAny().orElse(null);
	}
	
	private Restaurante buscaRestaurante(Restaurante r) {
		return restaurantes.stream().filter(x -> x.equals(r)).findAny().orElse(null);
	}
	
	/* Os métodos abaixo são responsáveis por cadastrar restaurantes,
	 * clientes e entregadores no Pidão, tomando o cuidado para que cada
	 * objeto seja único.
	 */
	
	public void cadastrarRestaurante(Restaurante r) {
		if(restaurantes.isEmpty()) {
			restaurantes.add(r);
			System.out.println("Restaurante cadastrado com sucesso!");
			return;
		}
		else {
			Restaurante aux = buscaRestaurante(r);
			if(aux == null) {
				restaurantes.add(r);
				System.out.println("Restaurante cadastrado com sucesso!");
				return;
			}
			System.out.println("Restaurante já cadastrado. Não foi possível realizar o cadastro.");
		}
	}
	
	public void cadastrarCliente(Cliente cliente) {
		if(cadastroClientes.isEmpty()) {
			cadastroClientes.add(cliente);
			System.out.println("Usuário cadastrado com sucesso!");
			return;
		}
		else {
			Cliente aux = buscaCliente(cliente);
			if(aux == null) {
				cadastroClientes.add(cliente);
				System.out.println("Usuário cadastrado com sucesso!");
				return;
			}
			System.out.println("CPF já existente. Não foi possível realizar o cadastro.");
		}
	}
	
	/* O método abaixo cadastra o entregador "e" no restaurante "r" */
	
	public void cadastrarEntregador(Entregador e, Restaurante r) {
		if(r.entregadores.isEmpty()) {
			r.entregadores.add(e);
			System.out.println("Entregador cadastrado com sucesso!");
			return;
		}
		else {
			Entregador aux = r.buscaEntregador(e);
			if(aux == null) {
				r.entregadores.add(e);
				System.out.println("Entregador cadastrado com sucesso!");
				return;
			}
			System.out.println("Entregador já cadastrado. Não foi possível realizar o cadastro.");
		}
	}
	
	public void adicionarAoCardapio(Item item, Restaurante r) {
		boolean sucesso = r.cardapio.adicionaItem(item);
		if(sucesso)
			System.out.println("Lanche adicionado ao cardápio do restaurante " + r.getNome() + " com sucesso!");
		else
			System.out.println("Não foi possível adicionar o lanche ao cardápio. Código alfanumérico já existente.");
	}
	
	public void removerDoCardapio(Item item, Restaurante r) {
		boolean sucesso = r.cardapio.removeItem(item);
		if(sucesso)
			System.out.println("Lanche removido do cardápio do restaurante " + r.getNome() + " com sucesso!");
		else
			System.out.println("Lanche informado não encontra-se no cardápio. Não foi possível realizar a remoção.");
	}
	
	public void imprimirCardapioDosRestaurantes() {
		System.out.println();
		System.out.println("===================== Cardápios dos restaurantes =====================");
		for(Restaurante r : restaurantes) {
			System.out.println(r.toString());
			System.out.println();
			System.out.println("Cardápio de hoje:");
			System.out.println(r.cardapio.toString());
		}
	}
	
	public void aplicarDesconto(Restaurante r, String codigo, double valor, TipoDesconto desconto) {
		Item lanche = r.cardapio.buscaLanche(codigo);
		if(lanche == null) 
			System.out.println("Não foi possível aplicar o desconto. Lanche inexistente no cardápio.");
		else {
			lanche.aplicaDesconto(valor, desconto);
			System.out.println("Desconto aplicado com sucesso!");
		}
	}
	
	public void removerDesconto(Restaurante r, String codigo) {
		Item lanche = r.cardapio.buscaLanche(codigo);
		if(lanche == null) 
			System.out.println("Não foi possível remover o desconto. Lanche inexistente no cardápio.");
		else {
			lanche.removeDesconto();
			System.out.println("Desconto removido com sucesso.");
		}
	}
	
	public void fazerPedido(Pedido p) {
		p.getCliente().setPedidoAtual(p);
		p.setStatus(StatusPedido.EM_PREPARACAO);
		p.calculaPrecoTotal();
		pedidos.add(p);
		if(p.getTipoDoPedido() == TipoPedido.ENTREGA) {
			p.setEntregador(p.getRestaurante().alocaEntregador());
			p.getEntregador().setPedido(p);
		}
	}
	
	public void cancelarPedido(Pedido p) {
		if(p.getStatus() == StatusPedido.NOVO || p.getStatus() == StatusPedido.EM_PREPARACAO) {
			boolean sucesso = pedidos.remove(p);
			if(sucesso) {
				p.getEntregador().setPedido(null);
				p.getCliente().setPedidoAtual(null);
				System.out.println("Pedido cancelado com sucesso.");
			}
			else
				System.out.println("Pedido não existente. Não foi possível realizar o cancelamento.");
		}
		else
			System.out.println("Não é possível realizar o cancelamento após o pedido ter saído para entrega.");
	}
	
	public void imprimirResumoPedidos() {
		if(pedidos.isEmpty())
			System.out.println("Nenhum pedido existente no momento.");
		else {
			System.out.println("Existem " + pedidos.size() + " pedidos:");
			System.out.println("============================================");
			for(Pedido pedido : pedidos) {
				System.out.println(pedido.toString());
				System.out.println("============================================");
			}
		}
	}
	
	public void entregaPedido(Pedido p) {
		if(p.getStatus() == StatusPedido.PRONTO)
			p.setStatus(StatusPedido.SAIU_PARA_ENTREGA);
		else
			System.out.println("Pedido ainda não está pronto!");
	}

	private void avaliarPedido(Pedido p) {
		char c;
		int nota;
		String comentario;
		System.out.print("Deseja avaliar o restaurante? (s/n) ");
		c = sc.next().charAt(0);
		if(c == 's') {
			System.out.print("Insira uma nota de 0 a 10 para o restaurante " + p.getRestaurante().getNome() + ": ");
			nota = sc.nextInt();
			sc.nextLine();
			System.out.print("Deseja inserir um comentário? (s/n) ");
			c = sc.next().charAt(0);
			if(c == 's') {
				sc.nextLine();
				comentario = sc.nextLine();
				p.getRestaurante().avaliar(nota, comentario);
			}
			else
				p.getRestaurante().avaliar(nota);
		}
		System.out.print("Deseja avaliar os lanches? (s/n) ");
		c = sc.next().charAt(0);
		if(c == 's')
			p.avaliarLanches();
		if(p.getTipoDoPedido() == TipoPedido.ENTREGA) {
			System.out.print("Deseja avaliar o entregador? (s/n) ");
			c = sc.next().charAt(0);
			if(c == 's') {
				System.out.print("Insira uma nota de 0 a 10 para o entregador " + p.getEntregador().getNome() + ": ");
				nota = sc.nextInt();
				System.out.print("Deseja inserir um comentário? (s/n) ");
				sc.nextLine();
				c = sc.next().charAt(0);
				if(c == 's') {
					sc.nextLine();
					comentario = sc.nextLine();
					p.getEntregador().avaliar(nota, comentario);
				}
				else
					p.getEntregador().avaliar(nota);
			}
		}
		System.out.println();
	}
	
	public void finalizaEntrega(Pedido p) {
		p.setStatus(StatusPedido.ENTREGUE);
		pedidos.remove(p);
		avaliarPedido(p);
		p.setEntregador(null);
	}
	
	public void exibirAvaliacoes() {
		System.out.println("==================================== Avaliações ====================================");
		for(Restaurante r : restaurantes) {
			System.out.println("-------------------------- Restaurante " + r.getNome() + "--------------------------");
			System.out.println(r.exibirAvaliacao());
			System.out.println("======================================================");
		}
	}
	
}