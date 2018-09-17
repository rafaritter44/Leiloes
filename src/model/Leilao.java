package model;

import java.util.Optional;

public class Leilao {

	private Pessoa vendedor;
	private Produto produto;
	private Optional<Oferta> melhorOferta;
	
	public Leilao(Pessoa vendedor, Produto produto) {
		this.vendedor = vendedor;
		this.produto = produto;
		produto.setEmLeilao(true);
		melhorOferta = Optional.empty();
	}
	
	public boolean vender() {
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
	
	public void setMelhorOferta(Oferta oferta) {
		melhorOferta = Optional.ofNullable(oferta);
	}
	
}
