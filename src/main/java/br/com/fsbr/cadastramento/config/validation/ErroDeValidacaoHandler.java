package br.com.fsbr.cadastramento.config.validation;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;

@RestControllerAdvice
public class ErroDeValidacaoHandler{
	
	@Autowired
	private MessageSource messageSource;
	
	// método para caso falhe a validação dos dados do formulário vindo do cliente
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public List<ErroDeFormularioDTO> handle(MethodArgumentNotValidException exception) {
		List<ErroDeFormularioDTO> dto = new ArrayList<>();
		List<FieldError> fieldErros = exception.getBindingResult().getFieldErrors(); // retorna o nome dos campos onde os erros foram gerados na validação

		// percorrendo os campos onde gerou o erro e gerando um dto para retorno
		fieldErros.forEach(e -> {
			String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale()); // para responder ao cliente de acordo com seu Accept-Language
			ErroDeFormularioDTO erro = new ErroDeFormularioDTO(e.getField(), mensagem);
			dto.add(erro);
		});
		
		return dto;
	}
	
	// método caso falhe a conversão dos dados JSON vindos do cliente com tratamento especial para o tipo Enum
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
	public Map<String, String> handle(HttpMessageNotReadableException exception) {

		String genericMessage = "JSON inválido " + exception.getMessage();
        String errorDetails = genericMessage;
        
        if (exception.getCause() instanceof InvalidFormatException) {
    		InvalidFormatException ifx = (InvalidFormatException) exception.getCause();
    		 if (ifx.getTargetType().isEnum()) {
                 errorDetails = String.format("Valor inválido: '%s' para o campo: '%s'. O valor deveria ser um de: %s.",
                         ifx.getValue(), ifx.getPath().get(ifx.getPath().size()-1).getFieldName(), Arrays.toString(ifx.getTargetType().getEnumConstants()));
             }
        }
        
       Map<String, String> response = new HashMap<>();
       response.put("title", "BAD_REQUEST");
       response.put("message", errorDetails);
       
       return response;
	}
	
	
		// método caso falhe a persistência dos dados
		@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
		@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
		public Map<String, String> handle(SQLIntegrityConstraintViolationException exception) {

		   String genericMessage = "Erro de persistência dos dados: " + exception.getMessage();
		   Map<String, String> response = new HashMap<>();
	       response.put("title", "CONFLICT");
	       response.put("message", genericMessage);
	       
	       return response;
		}

		
		
		// método caso os parâmetros sejam passados incorretamente
		@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
		@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
		public Map<String, String> handle(HttpRequestMethodNotSupportedException exception) {

		   String genericMessage = "Erro de parâmetros: " + exception.getMessage();
	        	        
	       Map<String, String> response = new HashMap<>();
	       response.put("title", "WRONG PARAMETERS");
	       response.put("message", genericMessage);
	       
	       return response;
		}
}
