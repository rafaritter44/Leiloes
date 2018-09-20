package model;

import java.io.Serializable;

public class Mensagem implements Serializable {

	private static final long serialVersionUID = 1L;
	public String operacao;
	public String username;
	public String args;
	
	public Mensagem(String operacao, String username, String args) {
		this.operacao = operacao;
		this.username = username;
		this.args = args;
	}

	@Override
	public String toString() {
		return "Mensagem [operacao=" + operacao + ", username=" + username + ", args=" + args + "]";
	}
	
}
