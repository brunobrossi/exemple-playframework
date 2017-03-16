package dao;

import java.util.List;
import java.util.Optional;

import models.Usuario;


public interface IUsuarioDAO {
    
    public void save (Usuario usuario);

    public Optional<Usuario> findByEmail(String email);
    
    public List<Usuario> findAll();

    public void update(Usuario usuarioRecebido);
    
}
