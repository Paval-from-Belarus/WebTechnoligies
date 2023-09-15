package by.bsuir.poit.database;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Paval Shlyk
 * @since 01/09/2023
 */

class BookTest {
public static int nextIsbn = 1;

private static final Random random = ThreadLocalRandom.current();

@BeforeAll
public static void init() {
      nextIsbn = 1;
}

private static String getRandomString(int length) {
      char[] letters = new char[length]; //each char has two bytes length
      byte[] buffer = new byte[2];
      for (int i = 0; i < length; i += 1) {
	    random.nextBytes(buffer);
	    letters[i] = (char) (buffer[1] << 8 | buffer[0]);
      }
      return String.valueOf(letters);
}

public static final int DEFAULT_TITLE_LENGTH = 20;
public static final int DEFAULT_AUTHOR_LENGTH = 20;

private static List<Book> getRandomTestCases(int instanceCnt) {
      Random random = ThreadLocalRandom.current();
      var list = new ArrayList<Book>(instanceCnt);
      for (int i = 0; i < instanceCnt; i++) {
	    var book = Book.builder()
			   .title(getRandomString(DEFAULT_TITLE_LENGTH))
			   .author(getRandomString(DEFAULT_AUTHOR_LENGTH))
			   .price(random.nextInt())
			   .build();
	    list.add(book);
      }
      return list;
}

@Test
public void cloneTest() throws CloneNotSupportedException {
      String author = "Vasya";
      String title = "Vasya's Achievements";
      int bookPrice = 0x42;
      Book book = Book.builder().author(author).title(title).price(bookPrice).build();
      assertDoesNotThrow(book::clone);
      Book cloned = book.clone();
      assertEquals(cloned, book);
      assertNotSame(book, cloned);
      cloned.setPrice(42);
      assertNotEquals(cloned, book);
      author = "Petya";
      book.setAuthor(author);
      assertNotEquals(book, cloned);
      assertNotSame(book, cloned);
}

@Test
public void sortTest() throws CloneNotSupportedException {
      Book bookPrototype = Book.builder().author("Vasya").title("Let's do this").price(0x42).isbn(13).build();
      List<Book> books = new java.util.ArrayList<>(List.of(
	  bookPrototype, bookPrototype,
	  bookPrototype.clone().setIsbn(nextIsbn++), bookPrototype.clone().setIsbn(nextIsbn++),
	  bookPrototype.clone().setIsbn(nextIsbn++), bookPrototype.clone().setIsbn(nextIsbn)
      ));
      books.sort(Comparator.naturalOrder());
      //assert books.size() != 0;//obviously
      int minIsbn = books.get(0).getIsbn();
      for (Book entry : books) {
	    assertTrue(entry.getIsbn() >= minIsbn);
	    minIsbn = entry.getIsbn();//let's change isbn to the next value
      }
}

@Test
public void comparatorsTest() throws CloneNotSupportedException {
      var books = getRandomTestCases(20);
      books.sort(BookComparator.byTitle());
      String minTitle = books.get(0).getTitle();
      for (Book book : books) {
	    assertTrue(minTitle.compareTo(book.getTitle()) >= 0);
	    minTitle = book.getTitle();
      }
      Collections.shuffle(books);
      books.sort(BookComparator.byAuthorAndTitle());
      String minAuthor = books.get(0).getAuthor();
      minTitle = books.get(0).getTitle();
      for (Book book : books) {
	    assertTrue(book.getAuthor().compareTo(minAuthor) >= 0);
	    assertTrue(book.getTitle().compareTo(minTitle) >= 0);
	    minAuthor = book.getAuthor();
	    minTitle = book.getTitle();
      }
      Collections.shuffle(books);
      books.sort(BookComparator.byTitleAndAuthor());
      minAuthor = books.get(0).getAuthor();
      minTitle = books.get(0).getTitle();
      for (Book book : books) {
	    assertTrue(book.getTitle().compareTo(minTitle) >= 0);
	    assertTrue(book.getAuthor().compareTo(minAuthor) >= 0);
	    minAuthor = book.getAuthor();
	    minTitle = book.getTitle();
      }
      Collections.shuffle(books);
      books.sort(BookComparator.byAuthorAndTitleAndPrice());
      int minPrice = books.get(0).getPrice();
      minAuthor = books.get(0).getAuthor();
      minTitle = books.get(0).getTitle();
      for (Book book : books) {
	    assertTrue(book.getTitle().compareTo(minTitle) >= 0);
	    assertTrue(book.getAuthor().compareTo(minAuthor) >= 0);
	    assertTrue(book.getPrice() >= minPrice);
	    minAuthor = book.getAuthor();
	    minTitle = book.getTitle();
	    minPrice = book.getPrice();
      }
}
}