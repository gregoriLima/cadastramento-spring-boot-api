package br.com.fsbr.cadastramento.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import br.com.fsbr.cadastramento.enumeration.Estados;
import br.com.fsbr.cadastramento.model.PessoaFisica;
import br.com.fsbr.cadastramento.repository.PessoaFisicaRepository;

public class PessoaFisicaForm {
	
	@NotNull @NotEmpty @Length(min = 5)
	private String nome;
	
	@NotNull @NotEmpty
	private String cidade;
	
	@NotNull

	private Estados estado;
	
	@NotNull @NotEmpty
	@Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message="O formato do CPF está incorreto (tente xxx.xxx.xxx-xx)")
	private String CPF;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public Estados getEstado() {
		return estado;
	}
	public void setEstado(Estados estado) {
		this.estado = estado;
	}
	public String getCPF() {
		return CPF;
	}
	public void setCPF(String cPF) {
		CPF = cPF;
	}
	public PessoaFisica converter() {
			
		return new PessoaFisica(nome, CPF, estado, cidade);
	}
	
	public PessoaFisica atualizar(Long id, PessoaFisicaRepository pessoaFisicaRepository) {
		
		PessoaFisica pf = pessoaFisicaRepository.findById(id).get();
		
		pf.setNome(this.nome);
		pf.setCPF(this.CPF);
		pf.setCidade(this.cidade);
		pf.setEstado(this.estado);
		
		return pf;
	}
	
}
