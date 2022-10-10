package com.library.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_loans")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long bookId;

    @JsonFormat(/*shape= JsonFormat.Shape.STRING,*/pattern="yyyy/MM/dd HH:mm:ss", timezone= "Turkey")
    @Column(nullable = false)
    private LocalDateTime loanDate;

    @JsonFormat(/*shape= JsonFormat.Shape.STRING,*/pattern="yyyy/MM/dd HH:mm:ss", timezone= "Turkey")
    @Column(nullable = false)
    private LocalDateTime expireDate;

    @JsonFormat(/*shape= JsonFormat.Shape.STRING,*/pattern="yyyy/MM/dd HH:mm:ss", timezone= "Turkey")
    @Column(nullable = true)
    private LocalDateTime returnDate;

    @Column(nullable = true,length = 300)
    //TODO: Notes for employee or admin
    private String notes;
}
