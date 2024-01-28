package maids.cc.librarymanagementsystem.borrowingrecord.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import maids.cc.librarymanagementsystem.book.entity.Book;
import maids.cc.librarymanagementsystem.patron.entity.Patron;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class BorrowingRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //to maintain the relationship, owner side
    @ManyToOne
    @JoinColumn(nullable = false) // by default it will be book_id
    private Book book;

    //to maintain the relationship, owner side
    @ManyToOne
    @JoinColumn(nullable = false) // by default it will be patron_id
    private Patron patron;


    @CreatedDate
    private Date borrowDate;

    private Date returnDate;


}
