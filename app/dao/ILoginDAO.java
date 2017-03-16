package dao;

import java.util.Optional;

import models.Usuario;

public interface ILoginDAO {
    
    public Optional<Usuario> doValidacaoUsuario(String email, String senha);
    
    public Optional<Usuario> doValidacaoSessao(String email);

}
