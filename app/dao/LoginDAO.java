package dao;

import java.util.Optional;

import javax.inject.Inject;
import javax.persistence.TypedQuery;

import models.Usuario;
import play.db.jpa.JPAApi;

public class LoginDAO implements ILoginDAO {

    @Inject
    private JPAApi jpa;

    @Override
    public Optional<Usuario> doValidacaoUsuario(String email, String senha) {

	TypedQuery<Usuario> query = jpa.em().createNamedQuery("findByEmailSenha", Usuario.class)
		.setParameter("email", email).setParameter("senha", senha);
	return query.getResultList().stream().findAny();

    }

    @Override
    public Optional<Usuario> doValidacaoSessao(String code) {

	TypedQuery<Usuario> query = jpa.em().createNamedQuery("findByApiToken", Usuario.class).setParameter("tokenCode",
		code);
	return query.getResultList().stream().findAny();

    }

}
