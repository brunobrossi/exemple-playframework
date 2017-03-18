package actions;

import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import dao.IAccessRegisterDAO;
import dao.IUsuarioDAO;
import models.AccessRegister;
import models.Usuario;
import play.mvc.Action;
import play.mvc.Http.Context;
import play.mvc.Result;



public class ActionAccessRegister extends Action.Simple{
    
    @Inject
    private IUsuarioDAO usuarioDAO;
    
    @Inject
    private IAccessRegisterDAO accessRegisterDAO;

    @Override
    public CompletionStage<Result> call(Context context) {
	
	String code = context.request().getHeader("API-Token");
	String uri = context.request().uri();
	Usuario usuario = usuarioDAO.findByTokenApi(code).get();
	AccessRegister accessRegister = new AccessRegister(usuario, uri);
	accessRegisterDAO.save(accessRegister);
	return delegate.call(context);
    }

}
