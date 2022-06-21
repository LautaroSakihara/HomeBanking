package com.mindhub.HomeBanking.Configuration;

import com.mindhub.HomeBanking.Repositories.ClientRepository;
import com.mindhub.HomeBanking.models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration//indica a spring que debe crear un objeto de este tipo cuando se está iniciando la
// aplicación para que cuando se configure el módulo de spring utilice ese objeto ya creado.

public class WebAuthentication extends GlobalAuthenticationConfigurerAdapter {

    @Autowired

    ClientRepository clientRepository;



    @Override

    public void init(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(inputName-> {

            Client client = clientRepository.findByEmail(inputName);

            if (client != null) {

            if (client.getEmail().contains("@admin.com")){
                return new User(client.getEmail(), client.getPassword(),

                        AuthorityUtils.createAuthorityList("ADMIN","CLIENT"));
            }else {
                return new User(client.getEmail(), client.getPassword(),

                        AuthorityUtils.createAuthorityList("CLIENT"));
            }
            } else {

                throw new UsernameNotFoundException("Unknown client: " + inputName);

            }

        });

    }
    @Bean//genera un objeto de tipo PasswordEncoder en el ApplicationContext
    // para que luego se pueda usar en cualquier parte de la aplicación que se requiera.

    public PasswordEncoder passwordEncoder() {

        return PasswordEncoderFactories.createDelegatingPasswordEncoder();

    }

}


