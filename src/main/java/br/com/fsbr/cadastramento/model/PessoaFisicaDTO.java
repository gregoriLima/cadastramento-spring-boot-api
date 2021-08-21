package br.com.fsbr.cadastramento.model;

import br.com.fsbr.cadastramento.enumeration.Estados;

public class PessoaFisicaDTO {
	
	private Long id;
	private String nome;
	private String cpf;
	private Estados estado;
	private String cidade;
	
	public PessoaFisicaDTO(PessoaFisica pf) {
		this.id = pf.getId();
		this.nome = pf.getNome();
		this.cpf = pf.getCPF();
		this.cidade = pf.getCidade();
		this.estado = pf.getEstado();
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getCPF() {
		return cpf;
	}

	public Estados getEstado() {
		return estado;
	}

	public String getCidade() {
		return cidade;
	}

}
