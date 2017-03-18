package dao;

import javax.inject.Inject;

import models.AccessRegister;
import play.db.jpa.JPAApi;

public class AccessRegisterDAO implements IAccessRegisterDAO{

    @Inject
    private JPAApi jpa;
    
    @Override
    public void save(AccessRegister access) {
	jpa.em().persist(access);
    }
    
    

}
