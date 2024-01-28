package maids.cc.librarymanagementsystem.patron.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

@Entity
@Getter
@Setter
public class Patron {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phoneNumber;

    //referring side
//    @OneToMany(mappedBy = "patron")
//    private List<BorrowingRecord> borrowingRecordList;

}
