package br.com.fsbr.cadastramento.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.fsbr.cadastramento.model.PessoaFisica;
import br.com.fsbr.cadastramento.model.PessoaFisicaDTO;

public interface PessoaFisicaRepository extends JpaRepository<PessoaFisica, Long>{
	
	Optional<List<PessoaFisica>> findByCpfContainingIgnoreCase(String cpf);
	Optional<PessoaFisica> findByCpf(String cpf);
	
	Optional<List<PessoaFisica>> findByNomeContainingIgnoreCase(String nome);
	Optional<PessoaFisica> findByNome(String nome);
	
//	@Query("SELECT new br.com.fsbr.cadastramento.model.PessoaFisicaDTO FROM PessoaFisica")
//	Optional<PessoaFisicaDTO> findAllDto();
	
}
