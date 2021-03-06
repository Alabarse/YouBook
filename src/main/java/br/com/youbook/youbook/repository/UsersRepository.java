package br.com.youbook.youbook.repository;

import br.com.youbook.youbook.models.Users;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, String>{
    Users findByUsername(String username);
    Users deleteByUsername(String username);
}
