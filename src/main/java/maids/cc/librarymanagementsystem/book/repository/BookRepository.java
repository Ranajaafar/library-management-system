package maids.cc.librarymanagementsystem.book.repository;

import maids.cc.librarymanagementsystem.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
