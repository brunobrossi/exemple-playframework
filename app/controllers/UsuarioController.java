package controllers;

import java.util.Optional;

import javax.inject.Inject;

import akka.util.Crypt;
import autenticadores.UsuarioAuth;
import dao.ITokenDAO;
import dao.IUsuarioDAO;
import models.EmailCadastroModelo;
import models.TokenCadastro;
import models.Usuario;
import play.api.libs.mailer.MailerClient;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security.Authenticated;
import validadores.ValidadorUsuario;
import views.html.*;

@Transactional
public class UsuarioController extends Controller {

    @Inject
    private FormFactory formularios;

    @Inject
    private IUsuarioDAO usuarioDAO;

    @Inject
    private ValidadorUsuario validadorusuario;

    @Inject
    private ITokenDAO tokenDAO;

    @Inject
    private MailerClient mailSender;

    public Result salvaNovoUsuario() {
	Form<Usuario> fromRequest = formularios.form(Usuario.class).bindFromRequest();
	if (validadorusuario.temErros(fromRequest)) {
	    flash("danger", "Há erros no formulário de cadastro de novo usuário");
	    return badRequest(formularioNovoUsuario.render(fromRequest));
	}
	Usuario usuario = fromRequest.get();
	usuario.setSenha(Crypt.sha1(usuario.getSenha()));
	usuarioDAO.save(usuario);
	TokenCadastro tokenCadastro = new TokenCadastro(usuario);
	tokenDAO.save(tokenCadastro);
	mailSender.send(new EmailCadastroModelo(tokenCadastro));
	flash("success", "Um email foi enviado para que você confirme seu cadastro!");
	return redirect(routes.LoginController.formularioLogin());
    }

    public Result formularioNovoUsuario() {

	Form<Usuario> form = formularios.form(Usuario.class);
	return ok(formularioNovoUsuario.render(form));
    }

    public Result confirmaUsuario(String email, String token) {

	Optional<TokenCadastro> possivelToken = tokenDAO.findByToken(token);
	Optional<Usuario> possivelUsuario = usuarioDAO.findByEmail(email);

	if (possivelToken.isPresent() && possivelUsuario.isPresent()) {

	    TokenCadastro tokenRecebido = possivelToken.get();
	    Usuario usuarioRecebido = possivelUsuario.get();
	    if (tokenRecebido.getUsuario().equals(usuarioRecebido)) {
		tokenDAO.delete(tokenRecebido);
		usuarioRecebido.setHabilitado(true);
		usuarioDAO.update(usuarioRecebido);
		session(LoginController.AUTH, usuarioRecebido.getEmail());
		flash("success", "Seu usuário foi confirmado com sucesso! Bem-vindo!");
		return redirect(routes.UsuarioController.painel());
	    }
	}
	flash("danger", "Erro na confirmação do seu usuário");
	return redirect(routes.LoginController.formularioLogin());
    }

    @Authenticated(UsuarioAuth.class)
    public Result painel() {
	return ok(painel.render());
    }
    

}
