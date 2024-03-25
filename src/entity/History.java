/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author admin
 */
@Entity
public class History implements Serializable{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Book book;
    @OneToOne
    private User user;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date takeOutBook;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date returnBook;

    public History() {
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getTakeOutBook() {
        return takeOutBook;
    }

    public void setTakeOutBook(Date takeOutBook) {
        this.takeOutBook = takeOutBook;
    }

    public Date getReturnBook() {
        return returnBook;
    }

    public void setReturnBook(Date returnBook) {
        this.returnBook = returnBook;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.book);
        hash = 83 * hash + Objects.hashCode(this.user);
        hash = 83 * hash + Objects.hashCode(this.takeOutBook);
        hash = 83 * hash + Objects.hashCode(this.returnBook);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final History other = (History) obj;
        if (!Objects.equals(this.book, other.book)) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        if (!Objects.equals(this.takeOutBook, other.takeOutBook)) {
            return false;
        }
        if (!Objects.equals(this.returnBook, other.returnBook)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.YYYY");
        return "History{" 
                + "book=" + book.getTitle()
                + ", user=" + user.getReader().getFirstname()
                + " " + user.getReader().getLastname()
                + " login="+user.getLogin()
                + ", takeOutBook=" + sdf.format(takeOutBook) 
                + ", returnBook=" + sdf.format(returnBook)
                + '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}
