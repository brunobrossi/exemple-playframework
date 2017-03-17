package controllers;

import java.util.Optional;

import javax.inject.Inject;

import akka.util.Crypt;
import autenticadores.UsuarioAuth;
import dao.ILoginDAO;
import models.Usuario;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security.Authenticated;
import views.html.formularioLogin;

public class LoginController extends Controller {

    public static final String AUTH = "auth";

    @Inject
    private FormFactory formularios;

    @Inject
    private ILoginDAO loginDAO;

    public Result formularioLogin() {
	return ok(formularioLogin.render(formularios.form()));
    }

    @Transactional
    public Result fazLogin() {

	DynamicForm form = formularios.form().bindFromRequest();
	String email = form.get("email");
	String senha = Crypt.sha1(form.get("senha"));
	Optional<Usuario> userCandidato = loginDAO.doValidacaoUsuario(email, senha);
	if (userCandidato.isPresent()) {
	    Usuario user = userCandidato.get();
	    if (user.isHabilitado()) {
		insertUserSession(user);
		flash("success", "Login efetuado com sucesso!");
		return redirect(routes.UsuarioController.painel());
	    } else {
		flash("danger", "Usuario não confirmado! ");
	    }
	} else {
	    flash("danger", "Usuário e/ou senha inválidos");
	}

	return redirect(routes.LoginController.formularioLogin());
    }

    @Transactional
    @Authenticated(UsuarioAuth.class)
    public Result fazLogout() {
	session().clear();
	flash("success", "Logout efetuado!");
	return redirect(routes.LoginController.formularioLogin());
    }

    public static void insertUserSession(Usuario user) {
	session(AUTH, user.getTokenApi().getCode());
    }

}
