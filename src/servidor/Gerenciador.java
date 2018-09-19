package servidor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Leilao;
import model.Oferta;
import model.Pessoa;

public class Gerenciador {
	
	private List<Leilao> leiloes;
	private Map<Integer, Pessoa> pessoas;
	private static double receitaDaEmpresa;
	
	public Gerenciador() {
		leiloes = new ArrayList<>();
		pessoas = new HashMap<>();
		receitaDaEmpresa = 0;
	}
	
	public boolean ofertar(Leilao leilao, Oferta oferta) throws Exception {
		if(!leilao.getMelhorOferta().isPresent()) {
			leilao.setMelhorOferta(oferta);
			return true;
		}
		if(leilao.getMelhorOferta().get().getValor() < oferta.getValor()) {
			leilao.setMelhorOferta(oferta);
			return true;
		}
		return false;
	}
	
	public String vender(Leilao leilao) {
		try {
			leilao.vender();
			leiloes.remove(leilao);
			return "Vendido para " + leilao.getMelhorOferta().get().getComprador().getNome();
		} catch(Exception excecao) {
			return excecao.getMessage();
		}
	}
	
	public boolean encerraLeilao() {
		//TODO
		return true;
	}
	
	public void addLeilao(Leilao leilao) {
		leiloes.add(leilao);
	}
	
	public void addPessoa(Pessoa pessoa) {
		pessoas.put(pessoa.getId(), pessoa);
	}
	
	public static double getReceitaDaEmpresa() {
		return receitaDaEmpresa;
	}
	
	public static void depositaTaxaDeVenda(double valor) {
		receitaDaEmpresa += valor;
	}

}
