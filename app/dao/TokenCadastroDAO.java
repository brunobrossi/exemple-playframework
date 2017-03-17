package dao;

import java.util.Optional;

import javax.inject.Inject;
import javax.persistence.TypedQuery;

import models.TokenCadastro;
import play.db.jpa.JPAApi;

public class TokenCadastroDAO implements ITokenCadastroDAO {

    @Inject
    private JPAApi jpa;

    @Override
    public void save(TokenCadastro token) {
	jpa.em().persist(token);
    }

    @Override
    public Optional<TokenCadastro> findByToken(String token) {

	TypedQuery<TokenCadastro> query = jpa.em().createNamedQuery("findByToken", TokenCadastro.class)
		.setParameter("token", token);

	return query.getResultList().stream().findAny();
    }

    @Override
    public void delete(TokenCadastro tokenRecebido) {
	jpa.em().remove(tokenRecebido);
    }
    
    

}
