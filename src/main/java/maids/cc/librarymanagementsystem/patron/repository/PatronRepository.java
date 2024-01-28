package maids.cc.librarymanagementsystem.patron.repository;

import maids.cc.librarymanagementsystem.patron.entity.Patron;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatronRepository extends JpaRepository<Patron, Long> {

}
