package com.apbdoo.BooksStore.services;

import com.apbdoo.BooksStore.models.*;
import com.apbdoo.BooksStore.repositories.*;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class RaportAdminService {
    public static final String DEST = "results/rapoarte/raport.pdf";
    private SenderService senderService;
    private OrderRepository orderRepository;
    private BookInfoRepository bookInfoRepository;
    private ShoppingCartRepository shoppingCartRepository;
    private UserRepository userRepository;
 /*
    @Autowired
    public RaportAdminService(SenderService senderService, OrderRepository orderRepository,
                              BookInfoRepository bookInfoRepository,
                              ShoppingCartRepository shoppingCartRepository,
                              UserRepository userRepository) {
        this.senderService = senderService;
        this.orderRepository = orderRepository;
        this.bookInfoRepository = bookInfoRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
    }

    public void createRaport(LocalDate date1,LocalDate date2, Principal principal) throws IOException {

        File file = new File(DEST);
        file.getParentFile().mkdirs();

        FileOutputStream fos = new FileOutputStream(DEST);
        PdfWriter writer = new PdfWriter(fos);

        PdfDocument pdf = new PdfDocument(writer);

        PdfFont font = PdfFontFactory.createFont(FontConstants.COURIER_BOLDOBLIQUE);

        Document document = new Document(pdf);
        Text text = new Text("BooksStore");


        text.setFont(font);
        text.setFontColor(Color.BLUE);


        Paragraph paragraph1 = new Paragraph();
        paragraph1.add(text);
        document.add(paragraph1);


        document.add(new Paragraph("Buna ziua,"));
        document.add(new Paragraph("Mai jos aveti o lista cu cele mai vandute carti din aceasta perioada si profitul pe care l-ati obtinut."));
        document.add(new Paragraph("Cu drag, "));
        document.add(new Paragraph("Echipa BooksStore "));


        List<Book> listOfBestSellers = BestSellers(date1, date2);
        List<BookInfo>  listOfBestSellersInfo = new ArrayList<>();

        for(int i = 1; i<= 5 ; i++){
            BookInfo bookInfo = bookInfoRepository.findByBookTitle(listOfBestSellers.get(i).getBookInfo().getBookTitle());
            listOfBestSellersInfo.add(bookInfo);
        }


        Table table = new Table(4);
        table.addCell(new Cell().add("Nr Crt"));
        table.addCell(new Cell().add("Titlul cartii"));
        table.addCell(new Cell().add("ISBN"));
        table.addCell(new Cell().add("Data publicarii"));
        int i = 1;
        for (BookInfo bookInfo : listOfBestSellersInfo) {
            table.addCell(new Cell().add("" + i));
            table.addCell(new Cell().add(bookInfo.getBookTitle()));
            table.addCell(new Cell().add(bookInfo.getBookISBN()));
            table.addCell(new Cell().add(" " + bookInfo.getBookPublicationDate()));
            i++;
        }


        document.add(table);

        Paragraph profit = new Paragraph();
        profit.add("Profitul obtinut in perioada " + date1 + "-" + date2 + " este " + CalculateProfit(date1,date2));
        document.add(profit);

        document.close();

        User user = userRepository.findByUsername(principal.getName());

        senderService.sendMail(DEST, user.getEmail(), user.getFirstName() + " " + user.getLastName());
    }

    public double CalculateProfit(LocalDate date1, LocalDate date2) {
        List<Order> listOfOrders = orderRepository.findByOrderDateBetween(date1, date2);
        int i = 1;
        double profit = 0;
        for (Order order : listOfOrders) {
            profit = profit + 0.3 * order.getTotalPrice();
            i++;
        }

        return profit;
    }

    private void swap (BookQuantity book1, BookQuantity book2 ){
        BookQuantity temp = new BookQuantity();
        temp = book1;
        book1 = book2;
        book2 = temp;
    }
    /*Am introdus doua date
    Am ales lista comenzilor din intervalul acela
    Pentru fiecare comanda am gasit shopping cart-ul asociat si am facut o lista a tuturor shopping
    Pntru fiecare shooping cart am facut o lista de carti si o lista de cantitati asociate
    Am unit cele doua liste intr-una singura pe baza clasei BookQuantity
    Am parcurs lista de bookquantity si la gasirea duplicatelor am adaugat cantitatea din duplicat in original si am sters duplicatul.
     Am sortat lista de carti pentru a le afisa pe cele mai vandute*/

/*
    public List<Book> BestSellers (LocalDate date1, LocalDate date2) {
        List<Order> listOfOrders = orderRepository.findByOrderDateBetween(date1, date2);
        int i = 1;
        List<ShoppingCart> shoppingCartList = new ArrayList<>();
        for(Order order : listOfOrders ) {
            User user = order.getUser();
            List<Book> books = order.getBooks();
            ShoppingCart shoppingCart = shoppingCartRepository.findByUserIdAndBooksIn(user.getId(), books);
            shoppingCartList.add(shoppingCart);
            i++;
        }

        List<Book> listOfBooksOrdered = new ArrayList<>();
        int [] listOfQuantities = new int [20];
        List<BookQuantity> listOfBooksAndQuantities = new ArrayList<>();

        for(int j = 1; j <= shoppingCartList.size(); j++) {
            listOfBooksOrdered.addAll(shoppingCartList.get(i).getBooks());
            listOfQuantities[j] = shoppingCartList.get(j).getQuantity();
        }

        for (int k = 1; k<= listOfBooksOrdered.size(); k++){
            BookQuantity bookQuantity = new BookQuantity(listOfBooksOrdered.get(k),listOfQuantities[k]);
            listOfBooksAndQuantities.add(bookQuantity);
        }

        for(int j = 1; j < listOfBooksAndQuantities.size(); j++ ) {
            for (int k = j + 1; k <= listOfBooksAndQuantities.size(); k++) {
                if (listOfBooksAndQuantities.get(j) == listOfBooksAndQuantities.get(k)) {
                    int quantityToAdd = listOfBooksAndQuantities.get(k).getQuantity();
                    listOfBooksAndQuantities.get(j).setQuantity(listOfBooksAndQuantities.get(j).getQuantity() + quantityToAdd);
                    listOfBooksAndQuantities.remove(listOfBooksAndQuantities.get(k));
                }
            }
        }


        for (i = 1; i < listOfBooksAndQuantities.size(); i++ ){
            for (int j = i + 1; j <= listOfBooksAndQuantities.size(); j++)
                if(listOfBooksAndQuantities.get(i).getQuantity() < listOfBooksAndQuantities.get(j).getQuantity())
                   swap(listOfBooksAndQuantities.get(i),listOfBooksAndQuantities.get(j));
        }

        List<Book> listOfBestsellers = new ArrayList<>();

        for(i = 1; i<=5; i++){
            listOfBestsellers.add(listOfBooksAndQuantities.get(i).getBook());
        }
        return listOfBestsellers;
    }
    */
}
