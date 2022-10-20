package com.library.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanUpdateRequest {


        @NotNull(message = "Please provide notes")
        @Size(max = 300)
        private String notes;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern =
                "MM/dd/yyyy HH:mm:ss", timezone = "Turkey")
        @NotNull(message="Please provide the pick up time of the reservation")
        private LocalDateTime expireDate;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern =
                "MM/dd/yyyy HH:mm:ss", timezone = "Turkey")
        @NotNull(message="Please provide the pick up time of the reservation")
        private LocalDateTime returnDate;








}
