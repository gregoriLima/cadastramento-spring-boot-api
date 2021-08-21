package br.com.fsbr.cadastramento.config.security;

import java.util.Date;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.fsbr.cadastramento.model.Usuario;
import br.com.fsbr.cadastramento.repository.UsuarioRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
//@PropertySource("classpath:application.properties")
public class TokenService {

   // tempo de expiração do token 
	@Value("${cadastramento.jwt.expiration}")
    private String expiration;
            
    @Value("${cadastramento.jwt.secret}")
    private String secret;

    @Autowired
    private UsuarioRepository repository;
    
    public String gerarToken(Authentication authenticate, String email) {
	
	Optional<Usuario> Usuario = repository.findByEmail(email);
	Long idDoUsuario = 0L;
	if(Usuario.isPresent())
	    idDoUsuario = Usuario.get().getId();
	
	Date hoje = new Date();
	
	Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration));
	
	// gerando jwt para o usuário
	return Jwts.builder()
		.setIssuer("API de cadastramento.")
		.setSubject(idDoUsuario.toString()) // colocando a id do usuário no jwt
		.setIssuedAt(hoje)
		.setExpiration(dataExpiracao)
		.signWith(SignatureAlgorithm.HS256, secret)// encriptando o token com SHA-256
		.compact();
    }


    // método que valida o jwt enviado pelo usuário
    public boolean isTokenValido(String token) {
	
		try { // caso o jwt for inválido, lança uma exception
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
			return true; 
		} catch (Exception e) {
			return false;
		}
    }

    // método que retorna o id do usuário que está dento do jwt
    public Long getIdUsuario(String token) {
		
		Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();// pegando o objeto do token
		return Long.parseLong(claims.getSubject()); 
   
    }

    @PostConstruct
    public void init() {
        System.out.println("================== " + secret + " ================== ");
    }
    
}
