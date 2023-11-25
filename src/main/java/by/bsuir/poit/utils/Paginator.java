package by.bsuir.poit.utils;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Collection;
import java.util.List;

/**
 * This class offers functionality to page over the huge item list.
 * It's strictly not recommended to use this class with a huge collection (to prevent excessive memory allocation passing a huge list in {@link #configure(Collection, String)} method)
 *
 * @author Paval Shlyk
 * @since 22/11/2023
 */
public class Paginator {
public static final String CURRENT_PAGE = "currentPage";
public static final String TOTAL_PAGE_COUNT = "pagesCount";
public static final String PAGE_SIZE = "pageSize";
private final HttpServletRequest request;
private final int pageSize;
private int currentPage;
private int pagesCount;

public Paginator(HttpServletRequest request, int pageSize) throws NumberFormatException {
      this.request = request;
      this.pageSize = pageSize;
      this.currentPage = 1;
      if (request.getParameter(CURRENT_PAGE) != null) {
	    currentPage = Integer.parseInt(request.getParameter(CURRENT_PAGE));
      }
}

/**
 * This method configures a pageable collection by skipping elements based on the current page and page size,
 * and limits the result to the specified page size.
 * It sets the configured list as an attribute in the request,
 * along with the total page count, current page, and page size.
 * Finally, it returns the configured list.
 *
 * @param pageable the collection to be configured
 * @param label    the label to be used for setting the list attribute in the request
 * @param <T>      the type of elements in the collection
 * @return the configured list
 */
public <T> List<T> configure(Collection<T> pageable, String label) {
      List<T> list = pageable.stream()
			 .skip((long) (currentPage - 1) * pageSize)
			 .limit(pageSize)
			 .toList();
      pagesCount = pageable.size() / pageSize + 1;
      request.setAttribute(label, list);
      request.setAttribute(TOTAL_PAGE_COUNT, pagesCount);
      request.setAttribute(CURRENT_PAGE, currentPage);
      request.setAttribute(PAGE_SIZE, pageSize);
      return list;
}

}
