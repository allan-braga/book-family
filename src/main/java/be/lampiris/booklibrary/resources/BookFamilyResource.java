package be.lampiris.booklibrary.resources;

import be.lampiris.booklibrary.event.ResourceCreatedEvent;
import be.lampiris.booklibrary.model.BookFamily;
import be.lampiris.booklibrary.service.BookFamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
    @RequestMapping("/book-families")
public class BookFamilyResource {

    @Autowired
    private BookFamilyService service;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public List<BookFamily> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<BookFamily> getById(@PathVariable Long id) {
        BookFamily bookFamily = service.findById(id).orElse(null);
        return bookFamily != null ? ResponseEntity.ok(bookFamily) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<BookFamily> insert(@Valid @RequestBody BookFamily bookFamily, HttpServletResponse response) {
        BookFamily dbBook = service.save(bookFamily);
        publisher.publishEvent(new ResourceCreatedEvent(this, response, dbBook.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(dbBook);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<BookFamily> update(@PathVariable Long id, @Valid @RequestBody BookFamily bookFamily) {
        return ResponseEntity.ok(service.update(id, bookFamily));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
