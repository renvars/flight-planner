package io.codelex.flightplanner.domain;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PageResult<CorrectFlight> {
    private int page;
    private final AtomicInteger totalItems;
    private List<CorrectFlight> items;

    public PageResult(int page, int totalItems, List<CorrectFlight> items) {
        this.page = page;
        this.totalItems = new AtomicInteger(totalItems);
        this.items = items;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalItems() {
        return totalItems.get();
    }

    public void setTotalItems(int totalItems) {
        this.totalItems.getAndSet(totalItems);
    }

    public List<CorrectFlight> getItems() {
        return items;
    }

    public void setItems(List<CorrectFlight> items) {
        this.items = items;
    }

    public void addItems(CorrectFlight correctFlight) {
        this.items.add(correctFlight);
        this.totalItems.getAndAdd(1);
    }
}
