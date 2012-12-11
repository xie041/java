package org.jboss.resteasy.examples.library.data;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
@XmlRootElement(name="books")
public class BookListing
{
   private List<Book> books;

   public BookListing()
   {
   }

   public BookListing(List<Book> books)
   {
      this.books = books;
   }

   @XmlElement(name="book")
   public List<Book> getBooks()
   {
      return books;
   }
}
