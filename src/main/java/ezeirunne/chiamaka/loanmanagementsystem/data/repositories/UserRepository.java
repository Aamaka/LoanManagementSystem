package ezeirunne.chiamaka.loanmanagementsystem.data.repositories;

import ezeirunne.chiamaka.loanmanagementsystem.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);

}
