package autenticadores;

import java.util.Optional;

import javax.inject.Inject;

import controllers.LoginController;
import controllers.routes;
import dao.ILoginDAO;
import models.Usuario;
import play.mvc.Result;
import play.mvc.Http.Context;
import play.mvc.Security.Authenticator;

public class UsuarioAuth extends Authenticator {

    @Inject
    private ILoginDAO loginDAO;

    @Override
    public String getUsername(Context context) {

	String email = context.session().get(LoginController.AUTH);
	Optional<Usuario> userSessao = loginDAO.doValidacaoSessao(email);
	if (userSessao.isPresent()) {
	    userSessao.get().getNome();
	}
	return null;
    }

    @Override
    public Result onUnauthorized(Context context) {
	context.flash().put("danger", "Acesso não autorizado");
	return redirect(routes.LoginController.formularioLogin());
    }

}
