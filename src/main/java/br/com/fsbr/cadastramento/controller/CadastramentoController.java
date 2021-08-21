/**
 * 
 */
package br.com.fsbr.cadastramento.controller;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.fsbr.cadastramento.enumeration.Estados;
import br.com.fsbr.cadastramento.form.PessoaFisicaForm;
import br.com.fsbr.cadastramento.model.PessoaFisica;
import br.com.fsbr.cadastramento.model.PessoaFisicaDTO;
import br.com.fsbr.cadastramento.repository.PessoaFisicaRepository;

@RestController
@RequestMapping("/cadastro")
public class CadastramentoController {

	@Autowired
	private PessoaFisicaRepository pessoaFisicaRepository;

	@GetMapping("/estados")
	public Estados[] consultaEstados() {

		return Estados.values();

	}

	@Transactional
	@GetMapping("/{id}")
	public ResponseEntity<PessoaFisicaDTO> consuta(@PathVariable Long id) {

		Optional<PessoaFisica> pf = pessoaFisicaRepository.findById(id);

		if (pf.isPresent()) {
			return ResponseEntity.ok(new PessoaFisicaDTO(pf.get()));
		}

		return ResponseEntity.notFound().build();

	}

	@Transactional
	@GetMapping
	@Cacheable(value = "listaDePessoaFisica")
	public ResponseEntity<Page<PessoaFisica>> lista(
			@RequestParam(required = false) @PageableDefault(sort = "id", direction = Direction.ASC) Pageable paginacao) {

		if (paginacao == null)
			paginacao = PageRequest.of(0, Integer.MAX_VALUE);

		Page<PessoaFisica> pf = pessoaFisicaRepository.findAll(paginacao);

		return ResponseEntity.ok(pf);

	}

	@Transactional
	@PostMapping
	@CacheEvict(value = "listaDePessoaFisica", allEntries = true)
	public ResponseEntity<PessoaFisicaDTO> cadastrar(@RequestBody @Valid PessoaFisicaForm formPessoaFisica, UriComponentsBuilder uriBuilder) {

		PessoaFisica pf = formPessoaFisica.converter();

		pessoaFisicaRepository.save(pf);
		
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(pf.getId()).toUri();
		return ResponseEntity.created(uri).body(new PessoaFisicaDTO(pf));
	}
	
	

	@Transactional
	@PutMapping("/{id}")
	@CacheEvict(value = "listaDePessoaFisica", allEntries = true)
	public ResponseEntity<PessoaFisicaDTO> atualizar(@PathVariable Long id,
			@RequestBody @Valid PessoaFisicaForm formPessoaFisica) {

		PessoaFisica pf = formPessoaFisica.atualizar(id, pessoaFisicaRepository);

		return ResponseEntity.ok(new PessoaFisicaDTO(pf));
	}

	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaDePessoaFisica", allEntries = true)
	public ResponseEntity<?> remover(@PathVariable Long id) {

		pessoaFisicaRepository.deleteById(id);

		return ResponseEntity.ok().build();
	}
}
