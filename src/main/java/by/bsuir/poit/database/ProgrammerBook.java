package by.bsuir.poit.database;

public class ProgrammerBook extends Book{
private String language;
private int level;

@Override
public boolean equals(Object obj) {
      if (obj == this) {
            return true;
      }
      boolean equals = false;
      if (obj instanceof ProgrammerBook other) {
	    equals = this.language.equals(other.language) && this.level == other.level;
      }
      return equals;
}
@Override
public int hashCode() {
      int result = super.hashCode();
      if (language != null) {
            result += language.hashCode() * level;
      }
      return result;
}

@Override
public String toString() {
      return String.format("ProgrammerBook=(language: %s, level: %d)", this.language, this.level);
}
}
