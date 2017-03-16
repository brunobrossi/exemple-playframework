package dao;

import java.util.List;
import java.util.Map;

import models.Produto;


public interface IProdutoDAO {
	
	public Produto find(String codigo);
	
	public void save(Produto produto);

	public List<Produto> findAll();

	public List<Produto> findByTipo(String tipo);

	public List<Produto> comFiltros(Map<String, String> parametros);

}
