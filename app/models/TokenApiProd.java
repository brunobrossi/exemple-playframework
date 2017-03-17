package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import akka.util.Crypt;

@Entity
public class TokenApiProd {

    @Id
    @GeneratedValue
    private long id;
    @OneToOne
    private Usuario usuario;
    private String code;
    private Date expiration;

    public TokenApiProd(Usuario usuario) {
	this.usuario = usuario;
	this.expiration = new Date();
	this.code = Crypt.sha1(expiration.toString() + usuario.toString() + Crypt.generateSecureCookie());
    }
    
    public TokenApiProd() {
    }

    public long getId() {
	return id;
    }

    public Usuario getUsuario() {
	return usuario;
    }

    public String getCode() {
	return code;
    }

    public Date getExpiration() {
	return expiration;
    }

}
