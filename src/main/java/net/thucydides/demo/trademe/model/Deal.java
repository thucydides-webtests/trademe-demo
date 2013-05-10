package net.thucydides.demo.trademe.model;

public class Deal {

    private final String title;
    private final String price;
    private final String details;

    public Deal(String title, String price, String details) {
        this.title = title;
        this.price = price;
        this.details = details;
    }

    public String getTitle() {
        return title;
    }
    public String getPrice() {
        return price;
    }
    public String getDetails() {
        return details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Deal deal = (Deal) o;

        if (title != null ? !title.equals(deal.title) : deal.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return title != null ? title.hashCode() : 0;
    }
}


