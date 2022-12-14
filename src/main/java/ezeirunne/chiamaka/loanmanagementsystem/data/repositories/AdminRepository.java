package ezeirunne.chiamaka.loanmanagementsystem.data.repositories;

import ezeirunne.chiamaka.loanmanagementsystem.data.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface AdminRepository extends JpaRepository<Admin, Long> {

    boolean existsByEmail(String email);

    Optional<Admin> findAdminByEmail(String email);
}
