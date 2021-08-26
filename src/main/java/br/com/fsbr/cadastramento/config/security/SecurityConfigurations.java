package br.com.fsbr.cadastramento.config.security;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import br.com.fsbr.cadastramento.repository.UsuarioRepository;

//configurando a segurança

@EnableWebSecurity
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

 @Override
 @Bean
 protected AuthenticationManager authenticationManager() throws Exception {
     // TODO Auto-generated method stub
     return super.authenticationManager();
 }
 
 @Autowired
 private TokenService tokenService;
 
 @Autowired
 private AutenticacaoService autenticacaoService;
 
 @Autowired
 private UsuarioRepository usuarioRepository;
 
 // configurações de controle de acesso
 @Override
 protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	 // dizendo onde está a lógica para autenticação e o algorítimo de hash para a senha
	auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());

 }

 // configurações de acesso aos endpoints
 @Override
 protected void configure(HttpSecurity httpSecurity) throws Exception {
	
	 httpSecurity.authorizeRequests()
		.antMatchers(HttpMethod.POST, "/auth").permitAll()
		.antMatchers(HttpMethod.GET, "/cadastro/estados").permitAll()
		.antMatchers(HttpMethod.GET, "/actuator/health").permitAll()
		.anyRequest().authenticated()
		.and().csrf().disable()  
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // definindo que não será utilizado session na autenticação
		.and().addFilterBefore(new AutenticacaoViaTokenFilter(tokenService, usuarioRepository), UsernamePasswordAuthenticationFilter.class)
		.cors().configurationSource(request -> { // habilitando cors config para testes em dev
		      var cors = new CorsConfiguration();
		      cors.setAllowedOrigins(List.of("http://localhost:3000", "http://127.0.0.1:80", "http://example.com"));
		      cors.setAllowedMethods(List.of("GET","POST", "PUT", "DELETE", "OPTIONS"));
		      cors.setAllowedHeaders(List.of("*"));
		      return cors;
		    }); 
 }
 
	 // configurações de recursos estáticos
	 @Override
	 public void configure(WebSecurity web) throws Exception {
		
		 // liberando os arquivos da aplicação web do swagger para a documentação
			web.ignoring().antMatchers("/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**", "/swagger-resources/**");
			
	 }
	 
	
}