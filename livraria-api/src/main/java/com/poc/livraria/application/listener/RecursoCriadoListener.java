package com.poc.livraria.application.listener;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.poc.livraria.application.event.RecursoCriadoEvent;
/**
 * @author Andre Valle
 * 
 * Classe responsável por adicionar no metodo response o caminho do novo recurso, após ser criado.
 * Ex: GET localhost:8080/livros/100
 *
 */
@Component
public class RecursoCriadoListener implements ApplicationListener<RecursoCriadoEvent>{

	@Override
	public void onApplicationEvent(RecursoCriadoEvent recursoCriadoEvent) {
		HttpServletResponse response = recursoCriadoEvent.getResponse();
		Long codigo = recursoCriadoEvent.getCodigo();	
		adicionarHeaderLocation(response, codigo);
	}

	private void adicionarHeaderLocation( HttpServletResponse response, Long codigo) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
				.buildAndExpand(codigo).toUri();
		response.setHeader("Location",uri.toASCIIString());
	}

}
