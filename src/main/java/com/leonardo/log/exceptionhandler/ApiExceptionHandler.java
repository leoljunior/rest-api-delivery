package com.leonardo.log.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.leonardo.log.domain.exception.EntidadeNaoEncontradaException;
import com.leonardo.log.domain.exception.NegocioException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private MessageSource messageSource; //essa interface é para resolver mensagem e para aplicar as configurações do messages.properties

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<Problema.Campo> campos = new ArrayList<>();
																			//poderia ser feito com api stream
		for (ObjectError error : ex.getBindingResult().getAllErrors()) { //MethodArgumentNotValidException ex variavel que esta sendo tratada, pegamos todos erros
			String nome = ((FieldError) error).getField(); //temos que fazer o cast pra pegar o getField.
//			String mensagem = error.getDefaultMessage(); com as mensagens customizadas agora não pegamos mais o default
			String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale()); //passamos aqui o ObjectError e o Locale
			
			campos.add(new Problema.Campo(nome, mensagem));
		}
		
		Problema problema = new Problema();
		problema.setStatus(status.value());
		problema.setDate(OffsetDateTime.now());
		problema.setTitle("Um ou mais campos estão inválidos. Preencha corretamente e tente novamente.");
		problema.setFields(campos);
		
		return handleExceptionInternal(ex, problema, headers, status, request);
	}
	
	
	
	@ExceptionHandler(NegocioException.class) //essa anotação indica que sera esse metodo que tratara a exception indicada
	public ResponseEntity<Object> handleNegocio(NegocioException ex, WebRequest request) {
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		Problema problema = new Problema();
		problema.setStatus(status.value()); //status do problema
		problema.setDate(OffsetDateTime.now()); //horario
		problema.setTitle(ex.getMessage()); //msg de erro, no caso a proprio que setamos no service ao lançar a exception
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
//		1param=exception/2param=corpo(Problema)/3param=headers/4param=status/5param request
//		Param 3 headers pode ser instanciado no corpo do metodo e customizado o cabecalho.
	}
	
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<Object> handleNegocio(EntidadeNaoEncontradaException ex, WebRequest request) {
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		
		Problema problema = new Problema();
		problema.setStatus(status.value()); //status do problema
		problema.setDate(OffsetDateTime.now()); //horario
		problema.setTitle(ex.getMessage()); //msg de erro, no caso a proprio que setamos no service ao lançar a exception
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
//		1param=exception/2param=corpo(Problema)/3param=headers/4param=status/5param request
//		Param 3 headers pode ser instanciado no corpo do metodo e customizado o cabecalho.
	}
	
}
