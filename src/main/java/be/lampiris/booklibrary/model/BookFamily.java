package be.lampiris.booklibrary.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "book_family")
public class BookFamily {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "library_id", nullable = false)
    private Library library;
}
