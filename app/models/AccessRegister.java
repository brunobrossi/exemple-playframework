package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class AccessRegister {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Usuario user;
    private Date date;
    private String uri;


    public AccessRegister(Usuario user, String uri) {
	this.user = user;
	this.uri = uri;
	this.date = new Date();

    }

    public AccessRegister() {
	
    }

    public Long getId() {
	return id;
    }

    public Usuario getUser() {
	return user;
    }

    public Date getDate() {
	return date;
    }

    public String getUri() {
	return uri;
    }

}
