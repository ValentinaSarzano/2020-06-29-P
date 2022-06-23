package it.polito.tdp.PremierLeague.model;

public class Mese {
	private int numero;
	private String nome;
	
	public Mese(int numero, String nome) {
		super();
		this.numero = numero;
		this.nome = nome;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return nome;
	}
	

}
