package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

import servidor.Gerenciador;

public class Leilao implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private Pessoa vendedor;
	private Produto produto;
	public Oferta melhorOferta;
	private LocalDateTime horaInicial;
	public String status;
	public LocalDateTime horaFinal;
	private static final int TEMPO_LIMITE = 5;
	private static final double TAXA_DE_VENDA = 0.05;
	public Random r;
	
	public Leilao(Pessoa vendedor, Produto produto) {
		status = "Ativo";
		this.id = r.nextInt(Integer.MAX_VALUE);
		
		this.vendedor = vendedor;
		this.produto = produto;
		produto.setEmLeilao(true);
		produto.idLeilao = id;
		melhorOferta = null;
		horaInicial = LocalDateTime.now();
		horaFinal = horaInicial.plusMinutes(TEMPO_LIMITE); 
	}
	
	public long tempoRestante() {
		
		return ChronoUnit.MINUTES.between(LocalDateTime.now(), horaFinal);
	}
	
	public boolean tempoEsgotado() {
		 return tempoRestante() < 0;
	}
	
	public void vender() throws Exception {
		if(tempoEsgotado())
			throw new Exception("Tempo de leilao esgotado");
		if(melhorOferta == null)
			throw new Exception("Nenhuma oferta realizada ate o momento");
		if(vendedor.removeProduto(produto)) {
			Oferta ofertaVencedora = melhorOferta;
			ofertaVencedora.getComprador().addProduto(produto);
			double valorDaVenda = ofertaVencedora.getValor();
			double pagamentoPraEmpresa = valorDaVenda * TAXA_DE_VENDA;
			vendedor.recebe(valorDaVenda - pagamentoPraEmpresa);
			Gerenciador.depositaTaxaDeVenda(pagamentoPraEmpresa);
		} else
			throw new Exception("ERRO - O vendedor nao possui esse produto");
	}
	
	public int getId() { return id; }
	public Pessoa getVendedor() { return vendedor; }
	public Produto getProduto() { return produto; }
	public Oferta getMelhorOferta() { return melhorOferta; }
	public LocalDateTime getHoraInicial() { return horaInicial; }
	
	public void setMelhorOferta(Oferta oferta) throws Exception {
		if(tempoEsgotado())
			throw new Exception("Tempo de leilao esgotado");
		melhorOferta = oferta;
	}

	@Override
	public String toString() {
		return "Leilao [id=" + id + ", vendedor=" + vendedor + ", produto=" + produto + ", melhorOferta=" + melhorOferta
				+ ", horaInicial=" + horaInicial + "]";
	}
	
}
