package bsuir.poit.webtechnologies.database;

import java.util.Comparator;

/**
 * @author Paval Shlyk
 * @since 01/09/2023
 */

public class BookComparator {
public static Comparator<Book> byTitle() {
      return BookTitleComparator.INSTANCE;
}
public static Comparator<Book> byTitleAndAuthor() {
      return BookTitleAndAuthorComparator.INSTANCE;
}
public static Comparator<Book> byAuthorAndTitle() {
      return BookAuthorAndTitleComparator.INSTANCE;
}
public static Comparator<Book> byAuthorAndTitleAndPrice() {
      return BookAuthorAndTitleAndPriceComparator.INSTANCE;
}
private enum BookTitleComparator implements Comparator<Book> {
      INSTANCE;
      @Override
      public int compare(Book o1, Book o2) {
	    return o1.getTitle().compareTo(o2.getTitle());
      }
}

private enum BookTitleAndAuthorComparator implements Comparator<Book> {
      INSTANCE;
      @Override
      public int compare(Book o1, Book o2) {
	    if (o1.getTitle().equals(o2.getTitle())) {
		  return o1.getAuthor().compareTo(o2.getAuthor());
	    }
	    return o1.getTitle().compareTo(o2.getTitle());
      }
}

private enum BookAuthorAndTitleComparator implements Comparator<Book> {
      INSTANCE;
      @Override
      public int compare(Book o1, Book o2) {
	    if (o1.getAuthor().equals(o2.getAuthor())) {
		  return o1.getTitle().compareTo(o2.getTitle());
	    }
	    return o1.getAuthor().compareTo(o2.getAuthor());
      }
}
private enum BookAuthorAndTitleAndPriceComparator implements Comparator<Book> {
      INSTANCE;
      @Override
      public int compare(Book o1, Book o2) {
	    int comparingResult = o1.getAuthor().compareTo(o2.getAuthor());
	    if (comparingResult != 0) {
		  return comparingResult;
	    }
	    comparingResult = o1.getTitle().compareTo(o2.getTitle());
	    if (comparingResult != 0) {
		  return comparingResult;
	    }
	    return o1.getPrice() - o2.getPrice();
      }
}
}
