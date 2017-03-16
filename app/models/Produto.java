package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import play.data.validation.Constraints.Required;

@NamedQueries({

	@NamedQuery(name = "findAll", query = "select p from Produto p"),

	@NamedQuery(name = "findByTipo", query = "select p from Produto p where p.tipo = :tipo") })

@Entity
public class Produto {

    @Required(message = "O titulo é obrigatório")
    private String titulo;
    @Required(message = "O código é obrigatório")
    @Id
    private String codigo;
    @Required(message = "O tipo é obrigatório")
    private String tipo;
    private String descricao;
    @Required(message = "O preço é obrigatório")
    private Double preco;

    public String getTitulo() {
	return titulo;
    }

    public void setTitulo(String titulo) {
	this.titulo = titulo;
    }

    public String getCodigo() {
	return codigo;
    }

    public void setCodigo(String codigo) {
	this.codigo = codigo;
    }

    public String getTipo() {
	return tipo;
    }

    public void setTipo(String tipo) {
	this.tipo = tipo;
    }

    public String getDescricao() {
	return descricao;
    }

    public void setDescricao(String descricao) {
	this.descricao = descricao;
    }

    public Double getPreco() {
	return preco;
    }

    public void setPreco(Double preco) {
	this.preco = preco;
    }

}
