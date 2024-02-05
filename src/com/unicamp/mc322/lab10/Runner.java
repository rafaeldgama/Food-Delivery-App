package com.unicamp.mc322.lab10;

import java.util.Locale;

import entities.Item;
import entities.Pedido;
import entities.Pidao;
import entities.Restaurante;
import enums.TipoDesconto;
import enums.TipoPedido;
import usuarios.Cliente;
import usuarios.Entregador;

public class Runner {

	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);
		Pidao pidaoApp = new Pidao();
		
		Cliente cliente1 = new Cliente("Marcos Paulo", "123.789.643-11", 1, 2);
		Cliente cliente2 = new Cliente("Pereira", "123.789.643-12", 8, 4);
		
		pidaoApp.cadastrarCliente(cliente1);
		pidaoApp.cadastrarCliente(cliente2);
		
		Restaurante Marambar = new Restaurante("Marambar", "123.456.789-01", 0, 0);
		Restaurante StarClean = new Restaurante("Star Clean", "123.456.789-02", 1, 1);
		
		pidaoApp.cadastrarRestaurante(Marambar);
		pidaoApp.cadastrarRestaurante(StarClean);
		
		Entregador entregador1 = new Entregador("José", "123.789.643-13");
		Entregador entregador2 = new Entregador("Manuel", "123.789.643-14");
		Entregador entregador3 = new Entregador("Silveira", "123.789.643-15");
		Entregador entregador4 = new Entregador("Matheus", "123.789.643-16");
		
		pidaoApp.cadastrarEntregador(entregador1, Marambar);
		pidaoApp.cadastrarEntregador(entregador2, Marambar);
		pidaoApp.cadastrarEntregador(entregador3, StarClean);
		pidaoApp.cadastrarEntregador(entregador4, StarClean);
		
		Item cuscuz = new Item("CCZ00", "Cuscuz com ovo", 10.00);
		Item macaxeira = new Item("MXCOS", "Macaxeira com costela no bafo", 15.00);
		Item coxinhaFrango = new Item("CXFRA", "Coxinha de Frango", 8.00);
		Item EsfihaDeCarne = new Item("ESFCA", "Esfiha de Carne", 6.00);
		Item xTudo = new Item ("XHAMB", "X-tudo", 15.00);
		
		pidaoApp.adicionarAoCardapio(cuscuz, Marambar);
		pidaoApp.adicionarAoCardapio(macaxeira, Marambar);
		pidaoApp.adicionarAoCardapio(coxinhaFrango, Marambar);
		pidaoApp.adicionarAoCardapio(EsfihaDeCarne, StarClean);
		pidaoApp.adicionarAoCardapio(xTudo, StarClean);
		
		pidaoApp.aplicarDesconto(Marambar, "CCZ00", 10, TipoDesconto.PORCENTAGEM);
		pidaoApp.aplicarDesconto(StarClean, "ESFCA", 2, TipoDesconto.VALOR_FIXO);
		
		pidaoApp.imprimirCardapioDosRestaurantes();
		
		Pedido p1 = new Pedido(cliente1, Marambar, TipoPedido.ENTREGA);
		p1.addItem(cuscuz);
		p1.addItem(macaxeira);
		pidaoApp.fazerPedido(p1);
		
		Pedido p2 = new Pedido(cliente2, Marambar, TipoPedido.ENTREGA);
		p2.addItem(coxinhaFrango);
		p2.addItem(cuscuz);
		pidaoApp.fazerPedido(p2);
		
		Pedido p3 = new Pedido(cliente2, StarClean, TipoPedido.ENTREGA);
		p3.addItem(EsfihaDeCarne);
		p3.addItem(xTudo);
		pidaoApp.fazerPedido(p3);
		
		pidaoApp.imprimirResumoPedidos();
		
		/* Execute esta parte abaixo se quiser testar o sistema de avaliações 
		
		p1.getEntregador().realizaEntrega(pidaoApp);
		p2.getEntregador().realizaEntrega(pidaoApp);
		p3.getEntregador().realizaEntrega(pidaoApp);

		pidaoApp.exibirAvaliacoes();
		
		*/
		
	}

}
