package com.cursofinancas.financas.service;

import java.util.Optional;

import org.junit.Test.None;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.cursofinancas.financas.exception.ErroDeAutenticacao;
import com.cursofinancas.financas.exception.RegraNegocioException;
import com.cursofinancas.financas.model.entity.Usuario;
import com.cursofinancas.financas.model.repository.UsuarioRepository;
import com.cursofinancas.financas.service.impl.UsuarioServiceImpl;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {
	
	
	@MockBean
	UsuarioRepository repository;
	
	@SpyBean
	UsuarioServiceImpl service;
	
	
	@Test
	public void naoDeveSalvarUsuarioComEmailJaCadastrado() {
		//cenario
		Usuario usuario = criarUsuarioComId();
		Mockito.doThrow(RegraNegocioException.class).when(service).validarEmail(usuario.getEmail());
		
		//acao
		
		//verificacao
		Mockito.verify( repository, Mockito.never()).save(usuario);
		Assertions.assertThrows(RegraNegocioException.class, ()-> service.salvarUsuario(usuario));
	}
	
	@Test
	public void deveSalvarUsuario() {
		//cenario
		Mockito.doNothing().when(service).validarEmail(Mockito.anyString());
		Usuario usuario = criarUsuarioComId();
		
		Mockito.when(repository.save(Mockito.any(Usuario.class))).thenReturn(usuario);
		
		//acao
		Usuario usuarioSalvo = service.salvarUsuario(usuario);
		
		//verificacao
		Assertions.assertTrue(usuarioSalvo.getNome().equals("usuario"));
		Assertions.assertTrue(usuarioSalvo.getEmail().equals("email@email.com"));
		Assertions.assertTrue(usuarioSalvo.getSenha().equals("senha"));
		
	}
	
	@Test
	public void deveLancarErroQuandoExistirUsuarioNaAutenticacaoMasSenhaInvalida() {
		//cenario
		
		String email = "email@email.com";
		String senha = "senhaDiferente";
		
		Usuario usuario = criarUsuarioComId();		
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));
		
		//acao
		Exception exception = Assertions.assertThrows(ErroDeAutenticacao.class, ()-> service.autenticar(email, senha));
		
		//validacao
		String expectedMessage = "Senha inválida";
	    String actualMessage = exception.getMessage();
	 
	    Assertions.assertTrue(actualMessage.contains(expectedMessage));
	    
	}
	
	@Test
	public void deveLancarErroQuandoNaoEncontrarUsuarioComEmailInformado() {
		
		//cenario
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
		
		//acao
		Exception exception = Assertions.assertThrows(ErroDeAutenticacao.class,()->service.autenticar("email@email.com", "senha"));
		
		//validacao
		String expectedMessage = "Usuario não encontrado para o email informado";
	    String actualMessage = exception.getMessage();
	 
	    Assertions.assertTrue(actualMessage.contains(expectedMessage));
			    
	}
	
	
	
	@Test
	public void deveAutenticarUmUsuarioComSucesso() {
		//cenario
		String email = "email@email.com";
		String senha = "senha";
		
		Usuario usuario = criarUsuarioComId();
		
		Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));
		
		//acao
		Usuario result = service.autenticar(email, senha);
		
		//validacao
		Assertions.assertNotEquals(None.class, result);
		
	}
	
	
	
	@Test
	public void deveValidarEmail() {
		
		//cenario
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);
		
		//acao
		service.validarEmail("email@email.com");
		
	}
	
	@Test
	public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado() {

		//cenario
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);
		
		////verificacao
	    Assertions.assertThrows(RegraNegocioException.class, ()->service.validarEmail("email@email.com"));
		
	}
	
	public static Usuario criarUsuarioComId() {
		return Usuario.builder()
				.id(1l)
				.nome("usuario")
				.email("email@email.com")
				.senha("senha")
				.build();
	}
	
	
}
