package dao;

import java.util.Optional;

import models.TokenCadastro;

public interface ITokenCadastroDAO {

    public void save(TokenCadastro token);
    
    
    public Optional<TokenCadastro> findByToken(String token);


    public void delete(TokenCadastro tokenRecebido); 


}
