package maids.cc.librarymanagementsystem.book.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import maids.cc.librarymanagementsystem.book.enums.Language;

import java.util.Date;

@Entity
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String isbn;
    private String title;
    private String author;
    private Date publicationDate;

    @Enumerated(EnumType.STRING) // to store the enum as string in the database
    private Language language;
    private String description;

}
