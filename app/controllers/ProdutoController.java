package controllers;

import javax.inject.Inject;

import dao.IProdutoDAO;
import models.Produto;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import validadores.ValidadorProduto;
import views.html.formularioNovoProduto;

@Transactional
public class ProdutoController extends Controller {

    @Inject
    FormFactory formularios;

    @Inject
    IProdutoDAO produtoDao;

    @Inject
    ValidadorProduto validador;

    public Result salvaNovoProduto() {

	Form<Produto> formProduto = formularios.form(Produto.class).bindFromRequest();

	if (validador.temErros(formProduto)) {
	    flash("danger", "Há erros no formulário de cadastro de novo produto");
	    return badRequest(formularioNovoProduto.render(formProduto));
	}
	produtoDao.save(formProduto.get());
	flash("success", "Seu novo produto '" + formProduto.get().getTitulo() + "' foi criado com sucesso");
	return redirect(routes.ProdutoController.formularioNovoProduto());
    }

    public Result formularioNovoProduto() {
	Produto produto = new Produto();
	produto.setTipo("E-BOOK");
	Form<Produto> formulario = formularios.form(Produto.class).fill(produto);
	return ok(formularioNovoProduto.render(formulario));
    }

}
