package br.com.fsbr.cadastramento.controller;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fsbr.cadastramento.config.security.TokenService;
import br.com.fsbr.cadastramento.form.LoginForm;
import br.com.fsbr.cadastramento.model.TokenDTO;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {
	
	 	private static final org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger();

	 	@Autowired
	 	private AuthenticationManager authManager;
	   
	    @Autowired
	    private TokenService tokenService;
	 
	    @PostMapping 
	    public ResponseEntity<TokenDTO> autenticar(@RequestBody @Valid LoginForm form){ 
		
		UsernamePasswordAuthenticationToken dadosLogin = form.converter(); // convertendo os dados de login para o Spring
		
		try {
		      
		    // caso não seja possível logar o usuário, lança uma exception
		    Authentication authenticate = authManager.authenticate(dadosLogin); 
		    	  
		    String token = tokenService.gerarToken(authenticate, dadosLogin.getName());
				    
		
		    log.info("Usuario: " + dadosLogin.getName() + " token gerado: " + token);
		    
		    // retornando o token e o tipo de autenticação
		    return ResponseEntity.ok(new TokenDTO(token, "Bearer"));
		} 
		catch (Exception e) {
		    			
				Map<String, Object> mapResponse = new LinkedHashMap<>();
						mapResponse.put("timestamp", Instant.now().toString());
						mapResponse.put("status", 401);
						mapResponse.put("error", "Unauthorized");
						mapResponse.put("message", "Authentication failed: bad credentials");
						mapResponse.put("path", "/auth");
					 
			
		   log.info("Erro ao autenticar " + dadosLogin.getName());
		    
		   return new ResponseEntity(mapResponse, HttpStatus.UNAUTHORIZED);
		   
		}
		
	   }
}
