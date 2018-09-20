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
	private Map<String, Pessoa> pessoas;
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
	
	public boolean encerraLeilao(int id) {
		for(int i=0; i<leiloes.size(); i++) {
			if(leiloes.get(i).getId() == id) {
				leiloes.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public void addLeilao(Leilao leilao) {
		leiloes.add(leilao);
	}
	
	public void addPessoa(Pessoa pessoa) {
		pessoas.put(pessoa.getUsername(), pessoa);
	}
	
	public static double getReceitaDaEmpresa() {
		return receitaDaEmpresa;
	}
	
	public static void depositaTaxaDeVenda(double valor) {
		receitaDaEmpresa += valor;
	}

}
