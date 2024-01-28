package maids.cc.librarymanagementsystem.borrowingrecord.repository;

import maids.cc.librarymanagementsystem.borrowingrecord.entity.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {

    Boolean existsByBookIdAndReturnDateIsNull(Long bookId);

    BorrowingRecord findByBookIdAndPatronIdAndReturnDateIsNull(Long bookId, Long patronId);
    Boolean existsByBookIdAndPatronIdAndReturnDateIsNull(Long bookId, Long patronId);



}
