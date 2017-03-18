package autenticadores;

import java.util.Optional;

import javax.inject.Inject;

import controllers.LoginController;
import controllers.routes;
import dao.IUsuarioDAO;
import models.Usuario;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security.Authenticator;

public class AdminAuth extends Authenticator {
    
    @Inject
    private IUsuarioDAO usuarioDAO;
    
    @Override
    public String getUsername(Context context) {
        String codigo = context.session().get(LoginController.AUTH);
        Optional<Usuario> possivelUsuario = usuarioDAO.findByTokenApi(codigo);
        if (possivelUsuario.isPresent()) {
            Usuario usuario = possivelUsuario.get();
            if (usuario.isAdmin()) {
        	context.args.put("usuario", usuario);
                return usuario.getNome();
            }
        }
        return null;
    }    
    
    
    @Override
    public Result onUnauthorized(Context context) {
        return redirect(routes.UsuarioController.painel());
    }

}
