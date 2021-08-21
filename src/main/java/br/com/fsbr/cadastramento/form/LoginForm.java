package br.com.fsbr.cadastramento.form;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class LoginForm {
    
    private String email;
    private String senha;
    
    public String getEmail() {
        return email;
    }
    public String getSenha() {
        return senha;
    }
    public UsernamePasswordAuthenticationToken converter() {
	// TODO Auto-generated method stub
	return new UsernamePasswordAuthenticationToken(this.email, this.senha);
    }
    

}