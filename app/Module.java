import java.time.Clock;

import com.google.inject.AbstractModule;

import dao.AccessRegisterDAO;
import dao.IAccessRegisterDAO;
import dao.ILoginDAO;
import dao.IProdutoDAO;
import dao.ITokenApiDAO;
import dao.ITokenCadastroDAO;
import dao.IUsuarioDAO;
import dao.LoginDAO;
import dao.ProdutoDAO;
import dao.TokenApiDAO;
import dao.TokenCadastroDAO;
import dao.UsuarioDAO;
import services.ApplicationTimer;
import services.AtomicCounter;
import services.Counter;

/**
 * This class is a Guice module that tells Guice how to bind several different
 * types. This Guice module is created when the Play application starts.
 *
 * Play will automatically use any class called `Module` that is in the root
 * package. You can create modules in other locations by adding
 * `play.modules.enabled` settings to the `application.conf` configuration file.
 */
public class Module extends AbstractModule {

    @Override
    public void configure() {
	// Use the system clock as the default implementation of Clock
	bind(Clock.class).toInstance(Clock.systemDefaultZone());
	// Ask Guice to create an instance of ApplicationTimer when the
	// application starts.
	bind(ApplicationTimer.class).asEagerSingleton();
	// Set AtomicCounter as the implementation for Counter.
	bind(Counter.class).to(AtomicCounter.class);

	bind(IProdutoDAO.class).to(ProdutoDAO.class);
	bind(IUsuarioDAO.class).to(UsuarioDAO.class);
	bind(ITokenCadastroDAO.class).to(TokenCadastroDAO.class);
	bind(ILoginDAO.class).to(LoginDAO.class);
	bind(ITokenApiDAO.class).to(TokenApiDAO.class);
	bind(IAccessRegisterDAO.class).to(AccessRegisterDAO.class);
    }

}
