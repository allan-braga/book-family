package be.lampiris.booklibrary.service;

import be.lampiris.booklibrary.model.BookFamily;
import be.lampiris.booklibrary.model.Library;
import be.lampiris.booklibrary.repository.BookFamilyRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookFamilyService {

    private final BookFamilyRepository repository;
    private LibraryService libraryService;

    @Autowired
    public BookFamilyService(BookFamilyRepository repository, LibraryService libraryService) {
        this.repository = repository;
        this.libraryService = libraryService;
    }

    public BookFamily save(BookFamily bookFamily) {
        validateLibrary(bookFamily.getLibrary());
        return repository.save(bookFamily);
    }

    public BookFamily update(Long id, BookFamily bookFamily) {
        validateLibrary(bookFamily.getLibrary());
        BookFamily dbBook = find(id);
        BeanUtils.copyProperties(bookFamily, dbBook, "id");
        return repository.save(dbBook);
    }

    public void delete(Long id) {
        BookFamily savedBook = find(id);
        repository.delete(savedBook);
    }

    public List<BookFamily> findAll() {
        return repository.findAll();
    }

    public Optional<BookFamily> findById(Long id) {
        return repository.findById(id);
    }

    private BookFamily find(Long id) {
        BookFamily savedBook = this.findById(id).orElse(null);
        if (savedBook == null) {
            throw new EmptyResultDataAccessException(1);
        }
        return savedBook;
    }

    private void validateLibrary(final Library library) {
        libraryService.findById(library.getId()).orElseThrow(() -> new EmptyResultDataAccessException(1));
    }
}
