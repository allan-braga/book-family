package be.lampiris.booklibrary.resources;

import be.lampiris.booklibrary.event.ResourceCreatedEvent;
import be.lampiris.booklibrary.model.Library;
import be.lampiris.booklibrary.service.LibraryService;
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
@RequestMapping("/libraries")
public class LibraryResource {

    @Autowired
    private LibraryService service;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public List<Library> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Library> getById(@PathVariable Long id) {
        Library library = service.findById(id).orElse(null);
        return library != null ? ResponseEntity.ok(library) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Library> insert(@Valid @RequestBody Library library, HttpServletResponse response) {
        Library dbLibrary = service.save(library);
        publisher.publishEvent(new ResourceCreatedEvent(this, response, dbLibrary.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(dbLibrary);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Library> update(@PathVariable Long id, @Valid @RequestBody Library library) {
        return ResponseEntity.ok(service.update(id, library));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
