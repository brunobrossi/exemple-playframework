
package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.Required;

@NamedQueries({

	@NamedQuery(name = "findAllUsuarios", query = "select u from Usuario u"),

	@NamedQuery(name = "findByEmailValida", query = "select u from Usuario u where u.email = :email"),

	@NamedQuery(name = "findByEmailSenha", query = "select u from Usuario u where u.email = :email and u.senha = :senha"),

	@NamedQuery(name = "findByApiToken", query = "select u from Usuario u where u.tokenApi.code = :tokenCode") })

@Entity
public class Usuario {

    @Id
    @GeneratedValue
    private Long id;
    @Required(message = "É necessário um nome para cadastro")
    private String nome;
    @Email
    @Required(message = "É necessário um email para cadastro")
    private String email;
    @Required(message = "É necessário uma senha para cadastro")
    private String senha;
    private boolean habilitado = false;
    @OneToOne(mappedBy = "usuario")
    private TokenApiProd tokenApi;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getNome() {
	return nome;
    }

    public void setNome(String nome) {
	this.nome = nome;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getSenha() {
	return senha;
    }

    public void setSenha(String senha) {
	this.senha = senha;
    }

    public boolean isHabilitado() {
	return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
	this.habilitado = habilitado;
    }

    public TokenApiProd getTokenApi() {
	return tokenApi;
    }

    public void setTokenApi(TokenApiProd tokenApi) {
	this.tokenApi = tokenApi;
    }

}
