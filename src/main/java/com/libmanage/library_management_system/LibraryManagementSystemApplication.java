package com.libmanage.library_management_system;

import com.libmanage.library_management_system.entity.AuthorMaster;
import com.libmanage.library_management_system.entity.BookInventoryMaster;
import com.libmanage.library_management_system.entity.BookMaster;
import com.libmanage.library_management_system.entity.GenreMaster;
import com.libmanage.library_management_system.service.BookMasterService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Date;

@SpringBootApplication
public class LibraryManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryManagementSystemApplication.class, args);
	}

	public JavaMailSender javaMailSender(MailProperties mailProperties) {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(mailProperties.getHost());
		mailSender.setPort(mailProperties.getPort());
		mailSender.setUsername(mailProperties.getUsername());
		mailSender.setPassword(mailProperties.getPassword());
		return mailSender;
	}
}
//	@Bean
//	public CommandLineRunner initialCreate(BookMasterService bookMasterService){
//		return(args)->{
//			BookMaster book1 = new BookMaster(
//			null, "ABC", 1L, 1L,true, new Date(), 1L, new Date(),1L);
//			AuthorMaster author1 = new AuthorMaster(1L,"Dummy first", "Dummy firstlast", new Date(),1L, new Date(),1L);
//			GenreMaster genre1 = new GenreMaster(3L,"Business books", new Date(), 1L, new Date(), 1L);
//			book1.addAuthor(author1);
//			book1.addGenre(genre1);
//			bookMasterService.createBook(book1);
//
//			BookMaster book2 = new BookMaster(null,"PQR", 2L, 2L,true, new Date(), 1L, new Date(),1L);
//			AuthorMaster author2 = new AuthorMaster(2L,"Dummy second", "Dummy secondlast", new Date(),1L, new Date(),1L);
//			GenreMaster genre2 = new GenreMaster(3L,"Horror books", new Date(), 1L, new Date(), 1L);
//			book1.addAuthor(author1);
//			book1.addGenre(genre1);
//			bookMasterService.createBook(book2);
//
//			BookMaster book3 = new BookMaster(null,"XYZ", 3L, 3L,true, new Date(), 1L, new Date(),1L);
//			AuthorMaster author3 = new AuthorMaster(3L,"Dummy third", "Dummy thirdlast", new Date(),1L, new Date(),1L);
//			GenreMaster genre3 = new GenreMaster(3L,"Sci-Fi books", new Date(), 1L, new Date(), 1L);
//			book1.addAuthor(author1);
//			book1.addGenre(genre1);
//			bookMasterService.createBook(book3);
//		};
//	}


