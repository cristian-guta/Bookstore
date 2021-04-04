package com.apbdoo.BooksStore.services;

import com.apbdoo.BooksStore.dto.AuthorDTO;
import com.apbdoo.BooksStore.models.Author;
import com.apbdoo.BooksStore.models.Book;
import com.apbdoo.BooksStore.models.BookInfo;
import com.apbdoo.BooksStore.repositories.AuthorRepository;
import com.apbdoo.BooksStore.repositories.BookInfoRepository;
import com.apbdoo.BooksStore.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apbdoo.BooksStore.dto.ResultDTO;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;

@Service
public class AuthorService {

    private AuthorRepository authorRepository;
    private BookRepository bookRepository;
    private BookInfoRepository bookInfoRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository, BookRepository bookRepository, BookInfoRepository bookInfoRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.bookInfoRepository = bookInfoRepository;
    }

    public void seedAuthors() {
        seedAuthor("Mircea","Eliade");
        seedAuthor("Mihai", "Eminescu");
        seedAuthor("Camil", "Petrescu");
        seedAuthor("Horia", "Arama");
    }

    public void seedAuthor(String firstName, String lastName){
        Author author = authorRepository.findByFirstNameAndLastName(firstName, lastName);
        //List<Book> listOfBooks = bookRepository.findByAuthors(author);
        if(author == null) {
            Author newAuthor = new Author().setFirstName(firstName).setLastName(lastName);//.setBooks(listOfBooks);
            authorRepository.save(newAuthor);
        }
    }

/*
    public List<Author> getAllAuthors() {
        return (List<Author>) authorRepository.findAll();
    }

 */

    public Set<Author> getAllAuthors() {
        Set<Author> authors = new HashSet<>();
        authorRepository.findAll().iterator().forEachRemaining(authors::add);
        return authors;
    }

    public AuthorDTO createAuthor(AuthorDTO author) {
        Author newAuthor = new Author()
                .setFirstName(author.getFirstName())
                .setLastName(author.getLastName())
                .setBooks(author.getBooks());
        return new AuthorDTO(authorRepository.save(newAuthor));
    }

  /*  public AuthorDTO editAuthor(int id, String firstName, String lastName) {
        Author updatedAuthor = authorRepository.findById(id);
        Author newAuthor = new Author(id,firstName,lastName);
    //    newAuthor.setFirstName(newAuthor.getFirstName()).setLastName(newAuthor.getLastName()).setBooks(newAuthor.getBooks());
        authorRepository.save(newAuthor);
        return new AuthorDTO(updatedAuthor);
    }*/

    public Author editAuthor(int id, Author author) {
        Author updatedAuthor = authorRepository.findById(id);
        updatedAuthor.setFirstName(author.getFirstName()).setLastName(author.getLastName()).setBooks(author.getBooks());
        authorRepository.save(updatedAuthor);
        return updatedAuthor;
    }


    public int numberOfAuthorsBook(String title){
        BookInfo bookInfo = bookInfoRepository.findByBookTitle(title);
        Book book = bookRepository.findById(bookInfo.getId()).get();
        List<Author> authorsOfBook = authorRepository.findByBooks(book);
        return authorsOfBook.size();
    }

    public ResultDTO deleteAuthor(int id) {
        Author deleteAuthor = authorRepository.findById(id);
        List<Book> listOfBooks = bookRepository.findByAuthors(deleteAuthor);
        List<Book> booksToDelete = new ArrayList<Book>();

        for (Book book : listOfBooks) {
            List<Author> authors = book.getAuthors();
            if (authors.size() == 1)
                booksToDelete.add(book);
        }


        authorRepository.delete(deleteAuthor);
        bookRepository.deleteAll(booksToDelete);

        return new ResultDTO().setType("success").setMessage("Author deleted.");
    }
/*    public ResultDTO deleteAuthor(int id) {
         Author author = authorRepository.findById(id);
         authorRepository.delete(author);
         return new ResultDTO().setType("success").setMessage("Book deleted.");
 }*/
}