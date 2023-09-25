package by.bsuir.poit.database;

import lombok.*;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

/**
 * @author Paval Shlyk
 * @since 01/09/2023
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Accessors(chain = true)
public class Book implements Cloneable, Comparable<Book>{
private String title;
private String author;
private int price;
private int isbn;
private static int edition;
@Override
public boolean equals(Object object) {
      if (object == this) {
            return true;
      }
      boolean equals = false;
      if (object instanceof Book other) { //instanceof return false for null values
	    equals = this.title.equals(other.title) && this.author.equals(other.author) && this.price == other.price;
      }
      return equals;
}
@Override
public int compareTo(@NotNull Book o) {
      return this.isbn - o.isbn;
}
@Override
public Book clone() throws CloneNotSupportedException {
      return (Book) super.clone();
}
@Override
public int hashCode() {
      return title.hashCode() + author.hashCode() + price;
}
@Override
public String toString() {
      return String.format("Book=(title: %s, author:%s, price: %d)", title, author, price);
}

}
