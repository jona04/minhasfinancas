package com.cursofinancas.financas.model.repository;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.cursofinancas.financas.model.entity.Usuario;

@ExtendWith( SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UsuarioRepositoryTest {
	
	@Autowired
	UsuarioRepository repository;
	
	@Autowired
	TestEntityManager entityManager;
	
	@Test
	public void deveVerificarAExistenciaDeUmEmail() {
		//cenanrio
		Usuario usuario = criarUsuario();
		entityManager.persist(usuario);
		
		//acao / execucao
		boolean result = repository.existsByEmail("email@email.com");
		
		//verificacao
		Assertions.assertTrue(result);
		
	}
	
	@Test
	public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComEmail() {
		
		//cenario

		
		//acao / execucao
		boolean result = repository.existsByEmail("usuario@email.com ");
		
		//verificacao
		Assertions.assertFalse(result);
		
	}
	
	@Test
	public void devePersistirUmUsuarioNaBaseDeDados() {
		//cenario
		Usuario usuario = criarUsuario();
		
		//acao
		Usuario usuarioSalvo = repository.save(usuario);
		
		//verificacao
		Assertions.assertEquals(usuarioSalvo.getNome(), usuarioSalvo.getNome());
	
	}
	
	@Test
	public void deveDeveRetornaVazioQuandoBuscarUsuarioPorEmailQueNaoExiste() {
		//cenario
		
		//acao
		Optional<Usuario> usuarioRetorno = repository.findByEmail("email@email.com");
		
		//verificacao
		Assertions.assertFalse(usuarioRetorno.isPresent());
	}
	
	@Test
	public void deveBuscarUsuarioPorEmail() {
		//cenario
		Usuario usuario = criarUsuario();
		entityManager.persist(usuario);
		
		
		//acao
		Optional<Usuario> usuarioRetorno = repository.findByEmail("email@email.com");
		
		//verificacao
		Assertions.assertEquals(usuarioRetorno.get().getEmail(), usuario.getEmail());
	}
	
	public static Usuario criarUsuario() {
		return Usuario.builder()
				.nome("usuario")
				.email("email@email.com")
				.senha("senha")
				.build();
	}

}
