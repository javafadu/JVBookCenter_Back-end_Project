package com.library.exception.message;

public class ErrorMessage {


    public final static String RESOURCE_NOT_FOUND_MESSAGE="Resource with id %d not found";

    public final static String PUBLISHER_NOT_FOUND_MESSAGE ="Publisher with id %d not found";

    public final static String CATEGORY_NOT_FOUND_MESSAGE="Category with id %d not found";

    public final static String USER_NOT_FOUND_MESSAGE="User with email %s not found";

    public final static String EMAIL_ALREADY_EXIST="Email already exist:%s";

    public final static String ROLE_NOT_FOUND_MESSAGE="Role with name %s not found";

    public final static String THERE_ARE_EXPIRED_BOOKS_FOR_THIS_USER="There are %d book/books by %s that are not returned in time";

    public final static String CATEGORY_HAS_RELATED_RECORDS_MESSAGE="Category is can not be deleted. The Category has related records in books.";
    public final static String HAS_NOT_RIGHT_TO_LOAN_BOOK="%s user has not right to loan a new book ";

    public final static String NOT_PERMITTED_METHOD_MESSAGE="You dont have any permission to change this value";

    public final static String PASSWORD_NOT_MATCHED="Your password are not matched";

    public final static String IMAGE_NOT_FOUND_MESSAGE="ImageFile with id %s not found";

    public final static String LOAN_TIME_INCORRECT_MESSAGE="Loan pick up time or drop off time not correct";

    public final static String BOOK_NOT_AVAILABLE_MESSAGE="BooK is not available for selected time";

    public final static String EXCEL_REPORT_CREATION_ERROR_MESSAGE="Error occurred while generation excel report";

    public final static String BOOK_USED_BY_RESERVATION_MESSAGE="Book couldn't be deleted. Book is used by a loan";

    public final static String USER_USED_BY_LOAN_MESSAGE="User couldn't be deleted. User is used by a loan";
    public static final String AUTHOR_NOT_FOUND_MESSAGE ="Author with id %d not found" ;
    public static final String AUTHOR_HAS_RELATED_RECORDS_MESSAGE = "Author is can not be deleted. The Author has related records in books.";
    public static final String LOAN_NOT_FOUND_MESSAGE = "Selected Loan Is Not Found";
    public static final String UNRETURNED_BOOK_NOT_FOUND_MESSAGE = "There are no unreturned book";
    public static final String EXPIRED_BOOK_NOT_FOUND_MESSAGE = "There are no expired book";
    public static final String BUILTIN_DELETE_ERROR_MESSAGE = "The item to be deleted (with id %d) has a built-in feature, it cannot be deleted.";


}
