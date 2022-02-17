package br.com.youbook.youbook.security;

import br.com.youbook.youbook.models.Users;
import br.com.youbook.youbook.repository.UsersRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service(value = "userService")
public class ImplementsUserDetailsService implements UserDetailsService{

    @Autowired
    private UsersRepository usersRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username)  {
        Optional<Users> opt = usersRepository.findByUsername(username);
        Users user = null;
        
        if(opt.isPresent()) {
            user = opt.get();
        }
        
        if (user == null) {
             throw new UsernameNotFoundException(username);
        }
        return user;
    }
    
}
