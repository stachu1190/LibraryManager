package com.example.libraryMS.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Date;

@Entity(name = "Borrow")
@Table(name = "borrow")
public class Borrow {
    @Id
    @SequenceGenerator(
            name = "borrow_sequence",
            sequenceName = "borrow_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "borrow_sequence"
    )
    @Column(
            name = "id_borrow",
            updatable = false
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name="id_book", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name="id_client", nullable = false)
    private Client client;

    @Column(
            name="date_borrowed",
            nullable = false
    )
    //@Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateBorrowed;

    @Column(
            name="date_returned"
    )
    //@Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateReturned;

    public Borrow() {}

    public Borrow(Book book, Client client, Date dateBorrowed) {
        this.book = book;
        this.client = client;
        this.dateBorrowed = dateBorrowed;
    }

    public Borrow(Book book, Client client, Date dateBorrowed, Date dateReturned) {
        this.book = book;
        this.client = client;
        this.dateBorrowed = dateBorrowed;
        this.dateReturned = dateReturned;
    }


    public void setId(Long Id) { this.id = Id; }
    public Long getId() {
        return id;
    }
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Date getDateBorrowed() {
        return dateBorrowed;
    }

    public void setDateBorrowed(Date dateBorrowed) {
        this.dateBorrowed = dateBorrowed;
    }

    public Date getDateReturned() {
        return dateReturned;
    }

    public void setDateReturned(Date dateReturned) {
        this.dateReturned = dateReturned;
    }

    @Override
    public String toString() {
        return "Borrow{" +
                "id=" + id +
                ", book=" + book +
                ", client=" + client +
                ", dateBorrowed=" + dateBorrowed +
                ", dateReturned=" + dateReturned +
                '}';
    }
}
