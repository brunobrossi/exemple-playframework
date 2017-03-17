package dao;

import javax.inject.Inject;

import models.TokenApiProd;
import play.db.jpa.JPAApi;

public class TokenApiDAO implements ITokenApiDAO{

    @Inject
    private JPAApi jpa;
    
    @Override
    public void save(TokenApiProd tokenProdApi) {
	jpa.em().persist(tokenProdApi);
	
    }

    
    
}
