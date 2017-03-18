package controllers;

import javax.inject.Inject;

import autenticadores.AdminAuth;
import dao.IProdutoDAO;
import dao.IUsuarioDAO;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security.Authenticated;
import views.html.*;

@Authenticated(AdminAuth.class)
public class AdminController extends Controller{

    @Inject
    private IUsuarioDAO usuarioDAO;

    @Inject
    private IProdutoDAO produtoDAO;
    

    @Transactional
    public Result usuarios() {
	return ok(usuarios.render(usuarioDAO.findAll()));
    }

    @Transactional
    public Result produtos() {
	return ok(produtos.render(produtoDAO.findAll()));
    }

}
