package org.jboss.resteasy.examples.library;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.Marshaller;

import org.jboss.resteasy.annotations.providers.jaxb.json.BadgerFish;
import org.jboss.resteasy.examples.library.data.Book;
import org.jboss.resteasy.examples.library.data.BookListing;
import org.jboss.resteasy.plugins.providers.jaxb.json.BadgerContext;

@Path("library")
public class Library
{
   private HashMap<String, Book> books = new HashMap<String, Book>();

   public Library()
   {
      books.put("596529260", new Book("Leonard Richardson", "596529260", "RESTful Web Services"));
      books.put("333333333", new Book("Bill Burke", "596529261", "EJB 3.0"));
      books.put("444444444", new Book("Hartwell", "596529262", "Java±à³ÌË¼Ïë"));
   }

   @GET
   @Path("books/json")
   //@Produces("application/json")
   @Produces({MediaType.APPLICATION_JSON})
   @BadgerFish
   public BookListing getBooksBadger()
   {
      return getListing();
   }

   @GET
   @Path("books/xml")
   @Produces({MediaType.APPLICATION_XML})
   //@Produces("application/json")
   //@Mapped // mapped is the default format
   public BookListing getBooksMapped()
   {
      return getListing();
   }
   
   @GET
   @Path("books/atom")
   @Produces({MediaType.APPLICATION_ATOM_XML})
   public BookListing getAtoms(){
	   return getListing();
   }

   @GET
   @Path("books/html")
   @Produces("text/html")
   public String getBooksBadgerText() throws Exception
   {
      BookListing listing = getListing();
      BadgerContext context = new BadgerContext(BookListing.class);
      StringWriter writer = new StringWriter();
      Marshaller marshaller = context.createMarshaller();
      marshaller.marshal(listing, writer);
      return writer.toString();
   }

   private BookListing getListing()
   {
      ArrayList<Book> list = new ArrayList<Book>();
      list.addAll(books.values());
      return new BookListing(list);
   }
}
