package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import servidor.Gerenciador;

public class Leilao implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private Pessoa vendedor;
	private Produto produto;
	public Oferta melhorOferta;
	private LocalDateTime horaInicial;
	public String status;
	private static final int TEMPO_LIMITE = 5;
	private static final double TAXA_DE_VENDA = 0.05;
	private static int contador = 1;
	
	public Leilao(Pessoa vendedor, Produto produto) {
		status = "Ativo";
		this.id = contador;
		contador++;
		this.vendedor = vendedor;
		this.produto = produto;
		produto.setEmLeilao(true);
		produto.idLeilao = id;
		melhorOferta = null;
		horaInicial = LocalDateTime.now();
	}
	
	public long tempoRestante() {
		return ChronoUnit.MINUTES.between(horaInicial, LocalDateTime.now());
	}
	
	public boolean tempoEsgotado() {
		 return tempoRestante() > TEMPO_LIMITE;
	}
	
	public void vender() throws Exception {
		if(tempoEsgotado())
			throw new Exception("Tempo de leil�o esgotado");
		if(melhorOferta == null)
			throw new Exception("Nenhuma oferta realizada at� o momento");
		if(vendedor.removeProduto(produto)) {
			Oferta ofertaVencedora = melhorOferta;
			ofertaVencedora.getComprador().addProduto(produto);
			double valorDaVenda = ofertaVencedora.getValor();
			double pagamentoPraEmpresa = valorDaVenda * TAXA_DE_VENDA;
			vendedor.recebe(valorDaVenda - pagamentoPraEmpresa);
			Gerenciador.depositaTaxaDeVenda(pagamentoPraEmpresa);
		} else
			throw new Exception("ERRO - O vendedor n�o possui esse produto");
	}
	
	public int getId() { return id; }
	public Pessoa getVendedor() { return vendedor; }
	public Produto getProduto() { return produto; }
	public Oferta getMelhorOferta() { return melhorOferta; }
	public LocalDateTime getHoraInicial() { return horaInicial; }
	
	public void setMelhorOferta(Oferta oferta) throws Exception {
		if(tempoEsgotado())
			throw new Exception("Tempo de leil�o esgotado");
		melhorOferta = oferta;
	}

	@Override
	public String toString() {
		return "Leilao [id=" + id + ", vendedor=" + vendedor + ", produto=" + produto + ", melhorOferta=" + melhorOferta
				+ ", horaInicial=" + horaInicial + "]";
	}
	
}
