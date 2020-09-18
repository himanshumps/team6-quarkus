package com.quarkushackfest.team6.domain;

import java.util.Objects;

public class Quote {
    private String bookId;
    private String book;
    private String content;

    public Quote() {

    }

    public Quote(String bookId, String book, String content) {
        this.bookId = bookId;
        this.book = book;
        this.content = content;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quote quote = (Quote) o;
        return Objects.equals(bookId, quote.bookId) &&
                Objects.equals(book, quote.book) &&
                Objects.equals(content, quote.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, book, content);
    }

    @Override
    public String toString() {
        return "Quote{" +
                "bookId='" + bookId + '\'' +
                ", book='" + book + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
