package com.staleyhighschool.fbla.library;

import com.staleyhighschool.fbla.users.User;
import com.staleyhighschool.fbla.util.Enums;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;

/**
 * Per Book object with access and generation methods
 */
public class Book {

    private String TAG = (this.getClass().getName() + ": ");

    private String bookTitle;
    private String bookAuthor;
    private String bookID;
    private Enums.IsOut isOut;
    private Enums.IsLate isLate;
    private Date dateOut;

    /**
     * Generates a new book
     * @param bookTitle {@link String} holding the title of the {@link Book}
     * @param bookAuthor {@link String} holding the author of the {@link Book}
     * @param bookID {@link String} holding the generated ID of the {@link Book}
     * @param isLate {@link com.staleyhighschool.fbla.util.Enums.IsLate} if the {@link Book} is overdue or not
     * @param isOut {@link com.staleyhighschool.fbla.util.Enums.IsOut} if the {@link Book} is out of the library or not
     */
    public Book(String bookTitle, String bookAuthor, String bookID, Enums.IsLate isLate, Enums.IsOut isOut, Date dateOut) {
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookID = bookID;
        this.isLate = isLate;
        this.isOut = isOut;
        this.dateOut = dateOut;
    }

    /**
     * Gets the title of the {@link Book}
     * @return {@link String} holding the title of the {@link Book}
     */
    public String getBookTitle() {
        return bookTitle;
    }

    /**
     * Gets the author of the {@link Book}
     * @return {@link String} holding the author of the {@link Book}
     */
    public String getBookAuthor() {
        return bookAuthor;
    }

    /**
     * Gets the ID of the {@link Book}
     * @return {@link String} holding the ID of the {@link Book}
     */
    public String getBookID() {
        return bookID;
    }

    /**
     * Returns boolean stating if book is late or not
     * @return {@link Boolean} stating if the book is late
     */
    public boolean isLate() {
        boolean late = false;

        if (isLate == Enums.IsLate.LATE) {
            late = true;
        } else if (isLate == Enums.IsLate.SAFE){
            late = false;
        }
        return late;
    }

    public void markLate(User user) {
        Date due;

        due = DateUtils.addDays(dateOut, Library.connection.getMaxDays(user));
        if (dateOut.after(due)) {
            isLate = Enums.IsLate.LATE;
        } else {
            isLate = Enums.IsLate.SAFE;
        }
    }

    public boolean isOut() {
        boolean out = false;

        if (isOut == Enums.IsOut.OUT) {
            out = true;
        } else if (isOut == Enums.IsOut.IN) {
            out = false;
        }
        return out;
    }
}
