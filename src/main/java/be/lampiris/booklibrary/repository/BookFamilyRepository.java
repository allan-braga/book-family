package be.lampiris.booklibrary.repository;

import be.lampiris.booklibrary.model.BookFamily;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookFamilyRepository extends JpaRepository<BookFamily, Long> {

}
