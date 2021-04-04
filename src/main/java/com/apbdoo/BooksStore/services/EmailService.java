package com.apbdoo.BooksStore.services;

import com.apbdoo.BooksStore.models.*;
import com.apbdoo.BooksStore.repositories.BookInfoRepository;
import com.apbdoo.BooksStore.repositories.OrderRepository;
import com.apbdoo.BooksStore.repositories.ShoppingCartRepository;
import com.apbdoo.BooksStore.repositories.UserRepository;
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
import java.util.List;

@Service
public class EmailService {

    public static final String DEST = "results/facturi/factura.pdf";
    private SenderService senderService;
    private UserRepository userRepository;
    private OrderRepository orderRepository;
    private BookInfoRepository bookInfoRepository;
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    public EmailService(SenderService senderService, UserRepository userRepository,
                        OrderRepository orderRepository, BookInfoRepository bookInfoRepository,
                        ShoppingCartRepository shoppingCartRepository) {
        this.senderService = senderService;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.bookInfoRepository = bookInfoRepository;
        this.shoppingCartRepository = shoppingCartRepository;
    }



    public void createPDF(List<Book> listOfBooks, Principal principal) throws IOException {
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

        User user = userRepository.findByUsername(principal.getName());
        Order order = orderRepository.findByUserIdAndOrderDate(user.getId(), LocalDate.now());

        List<ShoppingCart> shoppingCarts = new ArrayList<ShoppingCart>();
        for (Book book : order.getBooks()) {
            shoppingCarts.addAll(shoppingCartRepository.findByUserIdAndBook(user.getId(), book));
        }
        //ShoppingCart shoppingCart = shoppingCartRepository.findByUserIdAndBook(user.getId(),order.getBooks());


        document.add(new Paragraph("Buna ziua,"));
        document.add(new Paragraph("Va multumim pentru interesul acordat platformei BooksStore."));
        document.add(new Paragraph("Comanda dumneavoastra a fost inregistrata cu succes. Mai jos aveti detaliile acesteia. "  ));
        document.add(new Paragraph ("Cu drag, "));
        document.add(new Paragraph ("Echipa BooksStore "));

        Table table = new Table(5);
        table.addCell(new Cell().add("Nr Crt"));
        table.addCell(new Cell().add("Articol"));
        table.addCell(new Cell().add("Cantitate"));
        table.addCell(new Cell().add("Pret"));
        table.addCell(new Cell().add("Pretul Total"));
        int i = 1;
        for (Book book : listOfBooks) {
            BookInfo bookInfo = bookInfoRepository.findById(book.getId()).get();
            table.addCell(new Cell().add("" + i));
            table.addCell(new Cell().add(bookInfo.getBookTitle()));
            //table.addCell(new Cell().add(" " + shoppingCart.getQuantity()));
            table.addCell(new Cell().add(" " + book.getPrice()));
            //table.addCell(new Cell().add(" " + shoppingCart.getQuantity() * book.getPrice()));
            i++;
        }


        document.add(table);

        Paragraph delivery = new Paragraph();
        delivery.add("Adresa de livarare: " + user.getAddress());
        document.add(delivery);

        LocalDate date = LocalDate.now();

        Paragraph orderDate = new Paragraph();
        orderDate.add("Data comenzii: " + date);
        document.add(orderDate);

        Paragraph deliveryDate = new Paragraph();
        deliveryDate.add("Comanda va fi expediata in aproximativ 3 zile lucratoare.");
        document.add(deliveryDate);

        document.close();

        senderService.sendMail(DEST, user.getEmail(), user.getFirstName() + " " + user.getLastName());

    }

}
