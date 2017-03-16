package controllers;

import java.util.Map;

import javax.inject.Inject;

import dao.IProdutoDAO;
import models.EnvelopeDeProdutos;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

@Transactional(readOnly = true)
public class ApiController extends Controller{
	
	@Inject
	IProdutoDAO produtoDao;
	
	@Inject
	FormFactory formularios;
	
	
	public Result todos() {
		
		EnvelopeDeProdutos envelope = new EnvelopeDeProdutos(produtoDao.findAll());
		
		return ok(Json.toJson(envelope));
		
	}
	
	public Result porTipo(String tipo) {
		
		EnvelopeDeProdutos envelope = new EnvelopeDeProdutos(produtoDao.findByTipo(tipo));
		
		return ok(Json.toJson(envelope));
		
	}
	
	public Result comFiltro() {
	    Map<String, String> parametros = formularios.form().bindFromRequest().data();
	    EnvelopeDeProdutos envelope = new EnvelopeDeProdutos(produtoDao.comFiltros(parametros));
	    return ok(Json.toJson(envelope));
	}
	

}
