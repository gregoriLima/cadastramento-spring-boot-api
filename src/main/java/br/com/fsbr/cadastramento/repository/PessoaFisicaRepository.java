package br.com.fsbr.cadastramento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.fsbr.cadastramento.model.PessoaFisica;

public interface PessoaFisicaRepository extends JpaRepository<PessoaFisica, Long>{
	
	PessoaFisica findByCpf(String cpf);
	
}
