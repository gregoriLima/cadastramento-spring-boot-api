package br.com.fsbr.cadastramento.repository;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.fsbr.cadastramento.model.Usuario;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class UsuarioRepositoryTest {

	@Autowired
	private UsuarioRepository repository;
	
	// testando se o usuário foi carregado corretamente no DB
	@Test
	public void deveCarregarUmUsuario() {
		
		String email = "gregori@gmail.com";
		
		Usuario user = repository.findByEmail(email).get();
		
		Assert.assertNotNull(user);
		Assert.assertEquals("Grégori", user.getNome());
		
	}

}
