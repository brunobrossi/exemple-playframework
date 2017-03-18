package dao;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.persistence.TypedQuery;

import models.Usuario;
import play.db.jpa.JPAApi;

public class UsuarioDAO implements IUsuarioDAO {

    @Inject
    private JPAApi jpa;

    public void save(Usuario usuario) {
	jpa.em().persist(usuario);
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {

	TypedQuery<Usuario> query = jpa.em().createNamedQuery("findByEmailValida", Usuario.class).setParameter("email",
		email);
	return query.getResultList().stream().findAny();

    }

    @Override
    public List<Usuario> findAll() {

	TypedQuery<Usuario> query = jpa.em().createNamedQuery("findAllUsuarios", Usuario.class);
	return query.getResultList();

    }

    @Override
    public void update(Usuario usuarioRecebido) {
	jpa.em().merge(usuarioRecebido);
    }

    @Override
    public Optional<Usuario> findByTokenApi(String tokenApi) {

	TypedQuery<Usuario> query = jpa.em().createNamedQuery("findByApiToken", Usuario.class).setParameter("tokenCode",
		tokenApi);
	return query.getResultList().stream().findFirst();

    }

}
