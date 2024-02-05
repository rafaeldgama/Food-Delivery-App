package usuarios;

import entities.Pedido;

public class Cliente {
	
	private String nome;
	private String CPF;
	private Pedido pedidoAtual;
	private Double[] endereco = new Double[2];
	private Integer quantidadeDePedidosRealizados;
	
	public Cliente(String nome, String CPF, double x, double y) {
		this.nome = nome;
		this.CPF = CPF;
		endereco[0] = x;
		endereco[1] = y;
		quantidadeDePedidosRealizados = 0;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getCPF() {
		return CPF;
	}
	
	public Pedido getPedidoAtual() {
		return pedidoAtual;
	}
	
	public void setPedidoAtual(Pedido p) {
		pedidoAtual = p;
	}
	
	public Double[] getEndereco() {
		return endereco;
	}
	
	public void setEndereco(double x, double y) {
		endereco[0] = x;
		endereco[1] = y;
	}
	
	public Integer getQuantidadeDePedidosRealizados() {
		return quantidadeDePedidosRealizados;
	}
	

	public void realizaPedido() {
		quantidadeDePedidosRealizados++;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Cliente) {
			Cliente outroCliente = (Cliente) o;
			return CPF.equals(outroCliente.CPF);
		}
		else
			return false;
	}

	@Override
	public String toString() {
		return "Cliente: " + nome + " (" + CPF + ")";
	}

}
