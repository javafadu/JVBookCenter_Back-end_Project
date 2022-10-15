package com.library.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanSaveRequest {


        @NotNull(message = "Please provide userId")
        private Long userId;

        @NotNull(message = "Please provide bookId")
        private Long bookId;

        @Size(max = 300)
        private String notes;








}
