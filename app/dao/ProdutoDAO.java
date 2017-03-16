package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import models.Produto;
import play.db.jpa.JPAApi;

public class ProdutoDAO implements IProdutoDAO {

	@Inject
	private JPAApi jpa;

	@Override
	public void save(Produto produto) {
		jpa.em().persist(produto);
	}

	@Override
	public Produto find(String codigo) {
		return jpa.em().find(Produto.class, codigo);
	}

	@Override
	public List<Produto> findAll() {

		TypedQuery<Produto> query = jpa.em().createNamedQuery("findAll", Produto.class);
		return query.getResultList();
	}

	@Override
	public List<Produto> findByTipo(String tipo) {

		TypedQuery<Produto> query = jpa.em().createNamedQuery("findByTipo", Produto.class).setParameter("tipo", tipo);
		return query.getResultList();
	}

	@Override
	public List<Produto> comFiltros(Map<String, String> parametros) {

		CriteriaBuilder criteria = jpa.em().getCriteriaBuilder();
		CriteriaQuery<Produto> query = criteria.createQuery(Produto.class);
		Root<Produto> root = query.from(Produto.class);
		final List<Predicate> predicates = new ArrayList<Predicate>();
		parametros.entrySet().forEach(entrada -> {
			predicates.add(criteria.equal(root.get(entrada.getKey()), entrada.getValue()));
		});

		query.where(predicates.toArray(new Predicate[predicates.size()]));

		return jpa.em().createQuery(query).getResultList();
	}

}
