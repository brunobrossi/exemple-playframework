package controllers;

import java.util.Optional;

import javax.inject.Inject;

import akka.util.Crypt;
import autenticadores.UsuarioAuth;
import dao.ITokenApiDAO;
import dao.ITokenCadastroDAO;
import dao.IUsuarioDAO;
import models.EmailCadastroModelo;
import models.TokenApiProd;
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
import views.html.formularioNovoUsuario;
import views.html.painel;

public class UsuarioController extends Controller {

    @Inject
    private FormFactory formularios;

    @Inject
    private IUsuarioDAO usuarioDAO;

    @Inject
    private ValidadorUsuario validadorusuario;

    @Inject
    private ITokenCadastroDAO tokenCadastroDAO;

    @Inject
    private MailerClient mailSender;

    @Inject
    private ITokenApiDAO tokenApiDAO;

    @Transactional
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
	tokenCadastroDAO.save(tokenCadastro);
	mailSender.send(new EmailCadastroModelo(tokenCadastro));
	flash("success", "Um email foi enviado para que você confirme seu cadastro!");
	return redirect(routes.LoginController.formularioLogin());
    }

    public Result formularioNovoUsuario() {

	Form<Usuario> form = formularios.form(Usuario.class);
	return ok(formularioNovoUsuario.render(form));
    }

    @Transactional
    public Result confirmaUsuario(String email, String token) {

	Optional<TokenCadastro> possivelToken = tokenCadastroDAO.findByToken(token);
	Optional<Usuario> possivelUsuario = usuarioDAO.findByEmail(email);

	if (possivelToken.isPresent() && possivelUsuario.isPresent()) {
	    TokenCadastro tokenRecebido = possivelToken.get();
	    Usuario usuarioRecebido = possivelUsuario.get();
	    if (tokenRecebido.getUsuario().equals(usuarioRecebido)) {
		tokenCadastroDAO.delete(tokenRecebido);
		TokenApiProd tokenProdApi = new TokenApiProd(usuarioRecebido);
		tokenApiDAO.save(tokenProdApi);
		usuarioRecebido.setTokenApi(tokenProdApi);
		usuarioRecebido.setHabilitado(true);
		usuarioDAO.update(usuarioRecebido);
		LoginController.insertUserSession(usuarioRecebido);
		flash("success", "Seu usuário foi confirmado com sucesso! Bem-vindo!");
		return redirect(routes.UsuarioController.painel());
	    }
	}
	flash("danger", "Erro na confirmação do seu usuário");
	return redirect(routes.LoginController.formularioLogin());
    }

    @Transactional
    @Authenticated(UsuarioAuth.class)
    public Result painel() {
	
	return ok(painel.render());
    }

}
