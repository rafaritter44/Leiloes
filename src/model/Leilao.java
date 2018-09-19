package model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import servidor.Gerenciador;

public class Leilao {

	private Pessoa vendedor;
	private Produto produto;
	private Optional<Oferta> melhorOferta;
	private LocalDateTime horaInicial;
	private static final int TEMPO_LIMITE = 5;
	private static final double TAXA_DE_VENDA = 0.05;
	
	public Leilao(Pessoa vendedor, Produto produto) {
		this.vendedor = vendedor;
		this.produto = produto;
		produto.setEmLeilao(true);
		melhorOferta = Optional.empty();
		horaInicial = LocalDateTime.now();
	}
	
	private boolean tempoEsgotado() {
		return ChronoUnit.MINUTES.between(horaInicial, LocalDateTime.now()) > TEMPO_LIMITE;
	}
	
	public void vender() throws Exception {
		if(tempoEsgotado())
			throw new Exception("Tempo de leilão esgotado");
		if(!melhorOferta.isPresent())
			throw new Exception("Nenhuma oferta realizada até o momento");
		if(vendedor.removeProduto(produto)) {
			Oferta ofertaVencedora = melhorOferta.get();
			ofertaVencedora.getComprador().addProduto(produto);
			double valorDaVenda = ofertaVencedora.getValor();
			double pagamentoPraEmpresa = valorDaVenda * TAXA_DE_VENDA;
			vendedor.recebe(valorDaVenda - pagamentoPraEmpresa);
			Gerenciador.depositaTaxaDeVenda(pagamentoPraEmpresa);
		} else
			throw new Exception("ERRO - O vendedor não possui esse produto");
	}
	
	public Pessoa getVendedor() { return vendedor; }
	public Produto getProduto() { return produto; }
	public Optional<Oferta> getMelhorOferta() { return melhorOferta; }
	public LocalDateTime getHoraInicial() { return horaInicial; }
	
	public void setMelhorOferta(Oferta oferta) throws Exception {
		if(tempoEsgotado())
			throw new Exception("Tempo de leilão esgotado");
		melhorOferta = Optional.ofNullable(oferta);
	}
	
}
