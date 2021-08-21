package br.com.fsbr.cadastramento.model;

public class TokenDTO {

    private String tipo; // tipo Bearer
    private String token;

    public TokenDTO(String token, String tipo) {

	this.tipo = tipo;
	this.token = token;
	// TODO Auto-generated constructor stub
    }

    public String getTipo() {
        return tipo;
    }

    public String getToken() {
        return token;
    }
}