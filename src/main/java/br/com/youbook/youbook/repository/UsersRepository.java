package br.com.youbook.youbook.repository;

import br.com.youbook.youbook.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long>{
    Users findByUsername(String username);
    Users deleteByUsername(String username);
}
