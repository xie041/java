package org.jboss.resteasy.examples.hello;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.examples.library.data.Book;
import org.jboss.resteasy.examples.library.data.BookListing;

import com.google.inject.Inject;

@Path("hello")
public class HelloResource
{
   private final IGreeter greeter;

   @Inject
   public HelloResource(final IGreeter greeter)
   {
      this.greeter = greeter;
   }

   @GET
   @Path("{name}")
   public String hello(@PathParam("name") final String name) {
      return greeter.greet(name);
   }
   
   @GET
   @Produces({MediaType.APPLICATION_XML})
   @Path("book")
   public BookListing book() {
	   List<Book> books = new ArrayList<Book>();
	   books.add(new Book("张三", "ISBN-11111-25011", "平凡的世界"));
	   books.add(new Book("李四", "ISBN-11111-25011", "平凡的世界"));
	   books.add(new Book("王五", "ISBN-11111-25011", "平凡的世界"));
	   books.add(new Book("赵六", "ISBN-11111-25011", "平凡的世界"));
	   books.add(new Book("陈七", "ISBN-11111-25011", "平凡的世界"));
	   
	   BookListing list = new BookListing(books);
      return list;
   }
  
}