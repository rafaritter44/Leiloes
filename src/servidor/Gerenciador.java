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
	
	public Gerenciador() {
		leiloes = new ArrayList<>();
		pessoas = new HashMap<>();
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
	
	public boolean vender(Leilao leilao) throws Exception {
		if(leilao.vender()) {
			leiloes.remove(leilao);
			return true;
		}
		return false;
	}
	
	public void addLeilao(Leilao leilao) {
		leiloes.add(leilao);
	}
	
	public void addPessoa(Pessoa pessoa) {
		pessoas.put(pessoa.getId(), pessoa);
	}

}
