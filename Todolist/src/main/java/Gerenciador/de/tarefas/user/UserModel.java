package Gerenciador.de.tarefas.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.Column;
 
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_users")
public class UserModel {

     @Id
     @GeneratedValue(strategy = GenerationType.UUID)
     private UUID id;

     @Column(unique = true)
     private String username;
     public UserModel(String username) {
          this.username = username;
     }

     private String name;
     private String password;

     @CreationTimestamp
     private LocalDateTime createdAt; 

}


