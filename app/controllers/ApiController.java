package controllers;

import java.util.Map;

import javax.inject.Inject;

import actions.ActionAccessRegister;
import autenticadores.ProdutoApiAuth;
import dao.IProdutoDAO;
import models.EnvelopeDeProdutos;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security.Authenticated;
import play.mvc.With;


@Authenticated(ProdutoApiAuth.class)
@With(ActionAccessRegister.class)
public class ApiController extends Controller {

    @Inject
    IProdutoDAO produtoDao;

    @Inject
    FormFactory formularios;

    @Transactional
    public Result todos() {
	EnvelopeDeProdutos envelope = new EnvelopeDeProdutos(produtoDao.findAll());
	return ok(Json.toJson(envelope));

    }

    @Transactional
    public Result porTipo(String tipo) {
	EnvelopeDeProdutos envelope = new EnvelopeDeProdutos(produtoDao.findByTipo(tipo));
	return ok(Json.toJson(envelope));

    }

    @Transactional
    public Result comFiltro() {
	Map<String, String> parametros = formularios.form().bindFromRequest().data();
	EnvelopeDeProdutos envelope = new EnvelopeDeProdutos(produtoDao.comFiltros(parametros));
	return ok(Json.toJson(envelope));
    }

}
