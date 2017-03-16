package validadores;

import java.util.Optional;

import javax.inject.Inject;

import dao.IProdutoDAO;
import models.Produto;
import play.data.Form;
import play.data.validation.ValidationError;

public class ValidadorProduto {

	
	@Inject
	IProdutoDAO produtoDao;
	
	
	public Optional<Produto> isCodigoExistente(Produto produto) {

		Produto p = new Produto();
		p = produtoDao.find(produto.getCodigo());
		return Optional.ofNullable(p);

	}
	
	public boolean temErros(Form<Produto> formulario) {
        Produto produto = formulario.get();
        if (produto.getPreco() < 0.0) {
            formulario.reject(new ValidationError("preco", "Preço tem que ser maior ou igual a zero."));
        }
        if (isCodigoExistente(produto).isPresent()) {
            formulario.reject(new ValidationError("codigo", "Já existe um produto com este código."));
        }
        return formulario.hasErrors();
    }

}
