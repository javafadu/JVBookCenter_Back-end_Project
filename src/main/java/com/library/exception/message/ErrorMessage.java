package com.library.exception.message;

public class ErrorMessage {


    public final static String RESOURCE_NOT_FOUND_MESSAGE="Resource with id %d not found";

    public final static String USER_NOT_FOUND_MESSAGE="User with email %s not found";

    public final static String EMAIL_ALREADY_EXIST="Email already exist:%s";

    public final static String ROLE_NOT_FOUND_MESSAGE="Role with name %s not found";

    public final static String NOT_PERMITTED_METHOD_MESSAGE="You dont have any permission to change this value";

    public final static String PASSWORD_NOT_MATCHED="Your password are not matched";

    public final static String IMAGE_NOT_FOUND_MESSAGE="ImageFile with id %s not found";

    public final static String LOAN_TIME_INCORRECT_MESSAGE="Loan pick up time or drop off time not correct";

    public final static String BOOK_NOT_AVAILABLE_MESSAGE="BooK is not available for selected time";

    public final static String EXCEL_REPORT_CREATION_ERROR_MESSAGE="Error occurred while generation excel report";

    public final static String BOOK_USED_BY_RESERVATION_MESSAGE="Book couldn't be deleted. Book is used by a loan";

    public final static String USER_USED_BY_LOAN_MESSAGE="User couldn't be deleted. User is used by a loan";
}
