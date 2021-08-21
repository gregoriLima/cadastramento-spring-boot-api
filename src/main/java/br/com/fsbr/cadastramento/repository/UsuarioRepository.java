package br.com.fsbr.cadastramento.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fsbr.cadastramento.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);
    
}

