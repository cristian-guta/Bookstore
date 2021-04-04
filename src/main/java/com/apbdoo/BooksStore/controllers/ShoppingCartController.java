package com.apbdoo.BooksStore.controllers;

import com.apbdoo.BooksStore.dto.BookDTO;
import com.apbdoo.BooksStore.dto.ResultDTO;
import com.apbdoo.BooksStore.models.Book;
import com.apbdoo.BooksStore.models.ShoppingCart;
import com.apbdoo.BooksStore.models.User;
import com.apbdoo.BooksStore.repositories.BookRepository;
import com.apbdoo.BooksStore.repositories.ShoppingCartRepository;
import com.apbdoo.BooksStore.repositories.UserRepository;
import com.apbdoo.BooksStore.services.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShoppingCartController shoppingCartController;

  /*  @GetMapping("/add/viewCart")
    public String getAll(Principal principal, Model model) {
        //System.out.print(shoppingCartService.getBooksFromShoppingCart(principal));
        //model.addAttribute("booksFromShoppingCart", shoppingCartService.getBooksFromShoppingCart(principal));
        List<ShoppingCart> shoppingCarts= shoppingCartService.getShoppingCarts(principal);
        model.addAttribute("shoppingCarts", shoppingCarts);
        model.addAttribute("totalPrice",shoppingCartController.totalPriceShoppingCart(principal, model));
        return "viewShoppingCart";
    }*/

  /*  @PreAuthorize("permitAll()")
    @RequestMapping(value="/shoppingCartList")
    public String shoppingCartList(Model model){
        model.addAttribute("shoppingCarts", shoppingCartRepository.findAll());
        return "viewShoppingCart";
    }*/

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value ="shoppingCartList/page/{page}")
    public String listShoppingPageByPage(Model model, @PathVariable ("page") int pg,Principal principal) {
        PageRequest pageable = PageRequest.of(pg - 1, 2, Sort.by("quantity"));
        User user = userRepository.findByUsername(principal.getName());
        Page<ShoppingCart> shoppingCartPage = shoppingCartRepository.findAllPage(user.getUsername(),pageable);
        int totalPages = shoppingCartPage.getTotalPages();
        if(totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("activeShoppingList", true);
        model.addAttribute("shoppingCarts", shoppingCartPage.getContent());
        model.addAttribute("totalPrice",shoppingCartController.totalPriceShoppingCart(principal, model));
        return "viewShoppingCart";
    }

    public String totalPriceShoppingCart(Principal principal, Model model){
        Double  price;
        int     quantity;
        Double  totalPrice = 0.0;
        User user = userRepository.findByUsername(principal.getName());
        List<ShoppingCart> shoppingCarts= shoppingCartRepository.findByUserId(user.getId());
        for (ShoppingCart shoppingCart : shoppingCarts)
        {
            price = shoppingCart.getBook().getPrice();
            quantity = shoppingCart.getQuantity();
            totalPrice += price * quantity;
        }
        //model.addAttribute("totalPrice", totalPrice);
        String formattedTotalPrice = String.format("%.02f", totalPrice);
        return formattedTotalPrice;
    }

    @RequestMapping (value="/add/{id}", method=RequestMethod.GET)
    public String addToShoppingCart(@PathVariable("id") int id, Principal principal, Model model){
        Book book = bookRepository.findById(id);
        User user = userRepository.findByUsername(principal.getName());
        List<ShoppingCart> shoppingCartList= shoppingCartRepository.findByUserUsernameAndBook(user.getUsername(),book);
        if (shoppingCartList.isEmpty()) {
            ShoppingCart shoppingCart = new ShoppingCart().setQuantity(1).setBook(book).setUser(user);
            shoppingCartRepository.save(shoppingCart);
        } else {
            int quantity = shoppingCartList.get(0).getQuantity()+1;
            ShoppingCart shoppingCartOld = shoppingCartList.get(0);
            shoppingCartRepository.delete(shoppingCartOld);
            ShoppingCart shoppingCartNew = shoppingCartList.get(0).setQuantity(quantity).setBook(book).setUser(user);
            shoppingCartRepository.save(shoppingCartNew);
        }
        return "redirect:/book/bookList/page/1";
    }

    @RequestMapping (value="/delete/{id}", method=RequestMethod.GET)
    public String deleteShoppingCart(@PathVariable("id") int id, Principal principal){
        shoppingCartService.deleteShoppingCart(id,principal);
        return "redirect:/shoppingCart/shoppingCartList/page/1?";
    }

    @GetMapping("/order")
    public String placeOrder( Principal principal, Model model) {
        model.addAttribute("name", userRepository.findByUsername(principal.getName()));
        return "viewOrder";
    }
}


