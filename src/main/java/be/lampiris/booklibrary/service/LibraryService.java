package be.lampiris.booklibrary.service;

import be.lampiris.booklibrary.model.Library;
import be.lampiris.booklibrary.repository.LibraryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibraryService {

    private LibraryRepository repository;

    @Autowired
    public LibraryService(LibraryRepository repository) {
        this.repository = repository;
    }

    public Library save(Library library) {
        return repository.save(library);
    }

    public Library update(Long id, Library library) {
        Library dbLibrary = find(id);
        BeanUtils.copyProperties(library, dbLibrary, "id");
        return repository.save(dbLibrary);
    }

    public void delete(Long id) {
        Library savedLibrary = find(id);
        repository.delete(savedLibrary);
    }

    public Optional<Library> findById(Long id) {
        return repository.findById(id);
    }

    private Library find(Long id) {
        Library savedLibrary = this.findById(id).orElse(null);
        if (savedLibrary == null) {
            throw new EmptyResultDataAccessException(1);
        }
        return savedLibrary;
    }

    public List<Library> findAll() {
        return this.repository.findAll();
    }
}
