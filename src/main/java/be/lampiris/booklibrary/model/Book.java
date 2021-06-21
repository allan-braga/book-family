package be.lampiris.booklibrary.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    private String description;

    private String author;

    private int pages;

    @ManyToOne
    @JoinColumn(name = "family_id", nullable = false)
    private BookFamily bookFamily;
}
