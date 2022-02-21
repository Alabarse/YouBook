package br.com.youbook.youbook.security;

import br.com.youbook.youbook.models.Users;
import br.com.youbook.youbook.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
public class ImplementsUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Users usuario = usersRepository.findByUsername(username);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario não encontrado");
        }
        return usuario;
    }
}
