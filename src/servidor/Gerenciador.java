package servidor;

import java.util.HashMap;
import java.util.Map;

import model.Leilao;
import model.Oferta;
import model.Pessoa;

public class Gerenciador {
	
	public Map<Integer, Leilao> leiloes;
	public Map<String, Pessoa> pessoas;
	private static double receitaDaEmpresa;
	
	public Gerenciador() {
		leiloes = new HashMap<>();
		pessoas = new HashMap<>();
		receitaDaEmpresa = 0;
	}
	
	public boolean ofertar(Leilao leilao, Oferta oferta) throws Exception {
		if(leilao.melhorOferta == null) {
			leilao.setMelhorOferta(oferta);
			return true;
		}
		if(leilao.getMelhorOferta().getValor() < oferta.getValor()) {
			leilao.setMelhorOferta(oferta);
			return true;
		}
		return false;
	}
	
	public String vender(Leilao leilao) {
		try {
			leilao.vender();
			leiloes.remove(leilao.getId());
			return "Vendido para " + leilao.getMelhorOferta().getComprador().getNome();
		} catch(Exception excecao) {
			return excecao.getMessage();
		}
	}
	
	public boolean encerraLeilao(int id) {
		if (leiloes.get(id) == null) {
			return false;
		}
		leiloes.remove(id);
		return true;
	}
	
	public void addLeilao(int id, Leilao leilao) {
		leiloes.put(id, leilao);
	}
	
	public void addPessoa(Pessoa pessoa) {
		pessoas.put(pessoa.getUsername(), pessoa);
	}
	
	public boolean usernameExiste(String username) {
		if (pessoas.get(username) != null) {
			return true;
		}
		return false;
	}
	
	public static double getReceitaDaEmpresa() {
		return receitaDaEmpresa;
	}
	
	public static void depositaTaxaDeVenda(double valor) {
		receitaDaEmpresa += valor;
	}

	@Override
	public String toString() {
		return "Gerenciador [leiloes=" + leiloes + ", pessoas=" + pessoas + "]";
	}

}
