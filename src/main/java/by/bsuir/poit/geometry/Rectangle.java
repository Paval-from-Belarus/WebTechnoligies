package by.bsuir.poit.geometry;

/**
 * @author Paval Shlyk
 * @since 01/09/2023
 */
//(x, y) ― is lower left bound
public record Rectangle(double x, double y, double width, double height){
      public boolean isInside(double x, double y) {
	    return x >= this.x && y >= this.y && x <= (this.x + width) && y <= (this.y + height);
      }
}
