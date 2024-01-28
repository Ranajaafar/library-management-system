package maids.cc.librarymanagementsystem.auth.repository;

import maids.cc.librarymanagementsystem.auth.entity.Librarian;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LibrarianRepository extends JpaRepository<Librarian, Long>{

    Optional<Librarian> findByUsername(String username);

}