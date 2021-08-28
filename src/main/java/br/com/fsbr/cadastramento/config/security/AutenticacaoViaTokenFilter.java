package br.com.fsbr.cadastramento.config.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.fsbr.cadastramento.model.Usuario;
import br.com.fsbr.cadastramento.repository.UsuarioRepository;

public class AutenticacaoViaTokenFilter extends OncePerRequestFilter { // filtro do Spring que é chamado uma vez só a cada requisição

    private TokenService tokenService;
    
    private UsuarioRepository repository;
    
    public AutenticacaoViaTokenFilter(TokenService tokenService, UsuarioRepository repository) {

        this.tokenService = tokenService;
        this.repository = repository;
    }

    // Pegando o token do cabeçalho, verificando se está ok e autenticando o usuário
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	    throws ServletException, IOException {
			
		String token = recuperarToken(request);
		
		boolean tokenValido = tokenService.isTokenValido(token);
		
		if (tokenValido) {
		    autenticarCliente(token);
		} 
		
		filterChain.doFilter(request, response);
		
    }

    	
    // forçando a autenticação, já que o token foi validado
    private void autenticarCliente(String token) {
	
	// recuperando o id do usuário que está inserido no token
	Long idUsuario = tokenService.getIdUsuario(token);
	
	Usuario usuario = repository.findById(idUsuario).get(); 

	UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
	
	SecurityContextHolder.getContext().setAuthentication(authentication);

    	}

	private String recuperarToken(HttpServletRequest request) {
	   
	     String token = request.getHeader("Authorization");
	     
	     // verificando se o cabeçalho está correto:
	     if(token == null || token.isEmpty() || !token.startsWith("Bearer "))
		 return null;
	     
	     return token.split(" ")[1]; // devolvendo só o token
	}

}
