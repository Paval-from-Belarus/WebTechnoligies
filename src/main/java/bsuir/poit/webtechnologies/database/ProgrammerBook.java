package bsuir.poit.webtechnologies.database;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * @author Paval Shlyk
 * @since 01/09/2023
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ProgrammerBook {
private String language;
private int level;

@Override
public boolean equals(Object obj) {
      boolean equals = false;
      if (obj instanceof ProgrammerBook other) {
	    equals = this.language.equals(other.language) && this.level == other.level;
      }
      return equals;
}
@Override
public int hashCode() {
      return this.language.hashCode() * level;
}

@Override
public String toString() {
      return String.format("ProgrammerBook=(language: %s, level: %d)", this.language, this.level);
}
}
