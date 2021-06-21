package be.lampiris.booklibrary.resources;

import be.lampiris.booklibrary.event.ResourceCreatedEvent;
import be.lampiris.booklibrary.model.Book;
import be.lampiris.booklibrary.service.BookService;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookResource {

    @Autowired
    private BookService service;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public List<Book> findAll() {
        return service.findAll();
    }

    @GetMapping("/export")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public void exportAll(HttpServletResponse response) throws IOException {
        XSSFWorkbook workbook = service.export(null);
        response.addHeader("Content-Disposition", "attachment; filename=books.xlsx");
        workbook.write(response.getOutputStream());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Book> getById(@PathVariable Long id) {
        Book Book = service.findById(id).orElse(null);
        return Book != null ? ResponseEntity.ok(Book) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/export")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public void export(@PathVariable Long id, HttpServletResponse response) throws IOException {
        XSSFWorkbook workbook = service.export(id);
        response.addHeader("Content-Disposition", "attachment; filename=book.xlsx");
        workbook.write(response.getOutputStream());
    }


    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Book> insert(@Valid @RequestBody Book Book, HttpServletResponse response) {
        Book dbBook = service.save(Book);
        publisher.publishEvent(new ResourceCreatedEvent(this, response, dbBook.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(dbBook);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Book> update(@PathVariable Long id, @Valid @RequestBody Book Book) {
        return ResponseEntity.ok(service.update(id, Book));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }


}
