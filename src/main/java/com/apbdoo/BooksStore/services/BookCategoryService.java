package com.apbdoo.BooksStore.services;

import com.apbdoo.BooksStore.dto.ResultDTO;
import com.apbdoo.BooksStore.models.BookCategory;
import com.apbdoo.BooksStore.repositories.BookCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class BookCategoryService {

    private BookCategoryRepository bookCategoryRepository;

    @Autowired
    public BookCategoryService(BookCategoryRepository bookCategoryRepository) {
        this.bookCategoryRepository = bookCategoryRepository;
    }

    public void seedBookCategories() {
        seedBookCategory("Standard");
        seedBookCategory("Realistic");
        seedBookCategory("Fiction");
        seedBookCategory("Poems");
    }

    private void seedBookCategory(String bookCategoryDescription) {
        BookCategory bookCategory = bookCategoryRepository.findByBookCategoryDescription(bookCategoryDescription);
        if (bookCategory == null) {
            bookCategory = new BookCategory().setBookCategoryDescription(bookCategoryDescription);
            bookCategoryRepository.save(bookCategory);
        }
    }


    public List<BookCategory> getAllCategories() {
        return (List<BookCategory>) bookCategoryRepository.findAll();
    }

    public BookCategory createBookCategory(@RequestBody String input) {
        List<BookCategory> bookCategories = getAllCategories();
        BookCategory lastElem = bookCategories.get(bookCategories.size() - 1);
        BookCategory newBookCategory = new BookCategory(input);
        bookCategoryRepository.save(newBookCategory);
        return newBookCategory;
    }

    public ResultDTO updateBookCategory(@RequestBody BookCategory bookCategory) {
        bookCategoryRepository.save(bookCategory);
        return new ResultDTO().setType("success").setMessage("Book category updated.");
    }

    public ResultDTO deleteBookCategory(@PathVariable("id") int id) {
        Optional<BookCategory> currentBookCategory = bookCategoryRepository.findById(id);
        if (currentBookCategory.isPresent()) {
            bookCategoryRepository.deleteById(id);
            return new ResultDTO().setType("success").setMessage("Book category deleted.");
        }
        return new ResultDTO().setType("fail").setMessage("Book category does not exists");
    }
}
