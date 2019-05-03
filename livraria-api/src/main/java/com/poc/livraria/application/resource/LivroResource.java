package com.poc.livraria.application.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.poc.livraria.application.event.RecursoCriadoEvent;
import com.poc.livraria.domain.Livro;
import com.poc.livraria.domain.LivroService;
import com.poc.livraria.infrastructure.repository.LivroRepository;
import com.poc.livraria.infrastructure.repository.filter.LivroFilter;

/**
 * @author Andre Valle
 *
 */
@RestController
@RequestMapping("/livros")
@CrossOrigin(origins = "*")
public class LivroResource {
	
	@Autowired
	private LivroRepository livroRepository;
	
	@Autowired
	private LivroService livroService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	
	/**
	 * Filtra livros baseado na pesquisa, possui paginação e ordenação
	 * GET http://localhost:8080/livros?size=2&page=0&autor=Evans&sort=preco
	 * @param livroFilter Pesquisa de livros 
	 * @param pageable dados de paginação e ordenação da consulta
	 * @return Lista de livros
	 */
	@GetMapping
	public Page<Livro> pesquisa(LivroFilter livroFilter, Pageable pageable){
		return livroRepository.filtrar(livroFilter, pageable);
	}

	/**
	 * Listar todos os livros(não possui ordenação, paginação ou critério) 
	 * GET http://localhost:8080/livros
	 * @return lista todos os livros
	 */
	@GetMapping("/todos")
	public List<Livro> listar(){
		return livroRepository.findAll();
	}
	
	/**
	 * Incluir novo Livro
	 * POST http://localhost:8080/livros
	 * @param livro livro que será incluído
	 * @param response recupera o HttpServletResponse, para adicionar informações sobre o livro criado 
	 * @return livro salvo e link do novo recurso 
	 */
	@PostMapping
	public ResponseEntity<Livro> criar(@Valid @RequestBody Livro livro, HttpServletResponse response) {
		Livro livroSalvo =  livroRepository.save(livro); 
		publisher.publishEvent(new RecursoCriadoEvent(this, response, livroSalvo.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(livroSalvo);
	}
	
	
	/**
	 * Pesquisa livro pelo codigo
	 * GET http://localhost:8080/livros/{codigo}
	 * @param codigo identificador do livro que deseja encontrar 
	 * @return livro livro pesquisado
	 */
	@GetMapping("/{codigo}")
	public ResponseEntity<Livro> buscarPeloCodigo(@PathVariable Long codigo) {
		Livro livroEncontrado = livroRepository.findOne(codigo);
		return livroRepository != null?ResponseEntity.ok(livroEncontrado):ResponseEntity.notFound().build();
	}
	
	/**
	 * Remover livro
	 * DELETE http://localhost:8080/livros/{codigo}
	 * @param codigo livro que deseja remover 
	 */
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		livroRepository.delete(codigo);
	}
	
	/**
	 * 
	 * Atualizar dados do livro integralmente
	 * PUT http://localhost:8080/livros/{codigo}
	 * @param codigo identificador do livro que deseja atualizar
	 * @param livro dados que serão atualizados
	 * @return livro atualizado
	 */
	@PutMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Livro> atualizar (@Valid @PathVariable Long codigo, @RequestBody Livro livro){
		Livro livroSalvo = livroService.atualizar(codigo, livro);
		return ResponseEntity.ok(livroSalvo);
	}
	
	
}
