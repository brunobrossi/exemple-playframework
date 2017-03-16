package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

import akka.util.Crypt;

@NamedQueries({

	@NamedQuery(name = "findByToken", query = "select t from TokenCadastro t where t.token = :token")

})

@Entity
public class TokenCadastro {

    @Id
    @GeneratedValue
    private long id;
    private String token;
    @OneToOne
    private Usuario usuario;

    public TokenCadastro(Usuario usuario) {
	this.usuario = usuario;
	this.token = Crypt.sha1(usuario.getNome() + usuario.getEmail() + Crypt.generateSecureCookie());

    }

    public TokenCadastro() {
    }

    public long getId() {
	return id;
    }

    public String getToken() {
	return token;
    }

    public Usuario getUsuario() {
	return usuario;
    }

}
