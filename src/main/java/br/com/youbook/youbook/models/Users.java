package br.com.youbook.youbook.models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Repository;

@Data
@Entity
public class Users implements Serializable{
    
    @Id
    @NotNull        
    String email;
    @NotNull
    String password;
    @NotNull
    String username;
    
}
