package model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public class Leilao {

	private Pessoa vendedor;
	private Produto produto;
	private Optional<Oferta> melhorOferta;
	private LocalDateTime horaInicial;
	private static final int TEMPO_LIMITE = 5;
	
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
	
	public boolean vender() throws Exception {
		if(tempoEsgotado())
			throw new Exception("Tempo de leilão esgotado");
		if(!melhorOferta.isPresent())
			return false;
		if(vendedor.removeProduto(produto)) {
			melhorOferta.get().getComprador().addProduto(produto);
			return true;
		}
		return false;
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
