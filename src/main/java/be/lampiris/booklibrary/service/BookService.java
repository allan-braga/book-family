package be.lampiris.booklibrary.service;

import be.lampiris.booklibrary.excel.BooksExporter;
import be.lampiris.booklibrary.model.Book;
import be.lampiris.booklibrary.model.BookFamily;
import be.lampiris.booklibrary.repository.BookRepository;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository repository;
    private final BookFamilyService bookFamilyService;

    @Autowired
    public BookService(BookRepository repository, BookFamilyService bookFamilyService) {
        this.repository = repository;
        this.bookFamilyService = bookFamilyService;
    }

    public Book save(Book book) {
        validateBookFamily(book.getBookFamily());
        return repository.save(book);
    }

    public Book update(Long id, Book book) {
        validateBookFamily(book.getBookFamily());
        Book dbBook = find(id);
        BeanUtils.copyProperties(book, dbBook, "id");
        return repository.save(dbBook);
    }

    public void delete(Long id) {
        Book savedBook = find(id);
        repository.delete(savedBook);
    }

    public Optional<Book> findById(Long id) {
        return repository.findById(id);
    }

    private Book find(Long id) {
        Book savedBook = this.findById(id).orElse(null);
        if (savedBook == null) {
            throw new EmptyResultDataAccessException(1);
        }
        return savedBook;
    }

    private void validateBookFamily(final BookFamily bookFamily) {
        bookFamilyService.findById(bookFamily.getId())
                .orElseThrow(() -> new EmptyResultDataAccessException(1));
    }

    public XSSFWorkbook export(Long id) {
        BooksExporter booksExporter;
        if (id != null) {
            Book book = this.find(id);
            booksExporter = new BooksExporter(Collections.singletonList(book));
        } else {
            booksExporter = new BooksExporter(repository.findAll());
        }
        return booksExporter.export();
    }

    public List<Book> findAll() {
        return this.repository.findAll();
    }
}
