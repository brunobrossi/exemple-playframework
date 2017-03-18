package autenticadores;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import dao.IUsuarioDAO;
import models.Usuario;
import play.libs.Json;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security.Authenticator;

public class ProdutoApiAuth extends Authenticator {

    @Inject
    private IUsuarioDAO usuarioDAO;

    @Override
    public String getUsername(Context context) {


	String codigo = context.request().getHeader("API-Token");
	Optional<Usuario> possivelUsuario = usuarioDAO.findByTokenApi(codigo);
	if (possivelUsuario.isPresent()) {
	    return possivelUsuario.get().getNome();
	}

	return null;
    }

    @Override
    public Result onUnauthorized(Context context) {
	Map<String, String> parametrosDoErro = new HashMap<>();
	parametrosDoErro.put("código", "401");
	parametrosDoErro.put("mensagem", "Não autorizado!");
	Map<String, Object> erros = new HashMap<>();
	erros.put("errors", parametrosDoErro);
	return unauthorized(Json.toJson(erros));
    }

}
