package pl.com.bottega.cqrs.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PaginatedResult<T> implements Serializable {
    private final List<T> items;

    public List<Integer> getPages() {
        return pages;
    }

    private final List<Integer> pages= new ArrayList<Integer>();;
    private final int pageSize;
    private final int pageNumber;
    private final int pagesCount;
    private final int totalItemsCount;

    public PaginatedResult(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        items = Collections.emptyList();
        pagesCount = 0;
        totalItemsCount = 0;
        fillPages(pagesCount);
    }

    public PaginatedResult(List<T> items, int pageNumber, int pageSize, int totalItemsCount) {
        this.items = items;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.pagesCount = countPages(pageSize, totalItemsCount);
        this.totalItemsCount = totalItemsCount;
        fillPages(pagesCount);
    }

    private void fillPages(int pagesCount) {
        if (pagesCount == 0)
        {
            return;
        }
        for (int i = 1; i <= pagesCount; i++) {
            pages.add(i);
        }
    }


    private int countPages(int size, int itemsCount) {
        return (int) Math.ceil((double) itemsCount / size);
    }

    public List<T> getItems() {
        return items;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPagesCount() {
        return pagesCount;
    }

    public int getTotalItemsCount() {
        return totalItemsCount;
    }
}