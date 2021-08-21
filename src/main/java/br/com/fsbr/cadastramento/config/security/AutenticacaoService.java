package br.com.fsbr.cadastramento.config.security;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.fsbr.cadastramento.model.Usuario;
import br.com.fsbr.cadastramento.repository.UsuarioRepository;


@Service
public class AutenticacaoService implements UserDetailsService{ 
    
    @Autowired
    private UsuarioRepository repository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
	Optional<Usuario> usuario = repository.findByEmail(username);
	
	// se for encontrado um usuário...
	if (usuario.isPresent()) {
	    Usuario user = usuario.get();
	  
	    return new org.springframework.security.core.userdetails.User(user.getNome(), user.getSenha(), user.getAuthorities()); // esse retorno evita o erro "Empty encoded password"

	} 
		
	    throw new UsernameNotFoundException("Dados de login inválidos!");
	
    } 
    
}
