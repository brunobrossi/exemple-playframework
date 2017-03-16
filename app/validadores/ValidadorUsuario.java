package validadores;

import javax.inject.Inject;

import dao.IUsuarioDAO;
import models.Usuario;
import play.data.Form;
import play.data.validation.ValidationError;

public class ValidadorUsuario {

    @Inject
    private IUsuarioDAO usuarioDAO;

    public boolean temErros(Form<Usuario> fromRequest) {
	validaEmail(fromRequest);
	validaSenha(fromRequest);
	return fromRequest.hasErrors();
    }

    private void validaSenha(Form<Usuario> fromRequest) {

	String senha = fromRequest.field("senha").valueOr("");
	String confirmaSenha = fromRequest.field("confirmacaoSenha").valueOr("");

	if (confirmaSenha.isEmpty()) {
	    fromRequest.reject(new ValidationError("confirmacaoSenha", "Confirmação de senha não pode ser nula"));
	} else if (!senha.equals(confirmaSenha)) {
	    fromRequest.reject(new ValidationError("senha", ""));
	    fromRequest.reject(new ValidationError("confirmacaoSenha", "As senhas digitadas são diferentes"));
	}

    }

    private void validaEmail(Form<Usuario> fromRequest) {

	Usuario usuario = fromRequest.get();
	if (usuarioDAO.findByEmail(usuario.getEmail()).isPresent()) {
	    fromRequest.reject(new ValidationError("email", "Este email já foi cadastrado"));
	}
    }

}
