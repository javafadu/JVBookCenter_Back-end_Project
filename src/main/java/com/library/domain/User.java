package com.library.domain;

import java.time.LocalDateTime;
import java.util.*;

import javax.persistence.*;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
@Entity
@Table(name = "tbl_users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(length = 30,nullable=false)
	private String firstName;

	@Column(length = 30,nullable=false)
	private String lastName;

	@Column(nullable=false)
	private Integer score=0;

	@Column(length = 100,nullable=false)
	private String address;

	@Column(nullable=false)
	private String phone;
	
	@Column
	private Date birthDate;

	@Column(length = 80, nullable = false, unique = true)
	private String email;
	
	@Column(nullable=false)
	private String password;
	
	@Column(nullable=false)
	private LocalDateTime createDate;
	
	@Column
	private String resetPasswordCode;
	
	@Column(nullable=false)
	private Boolean builtIn=false;

	@OneToMany(mappedBy = "userLoan")
	private List<Loan> userBooks=new ArrayList<>();


	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name="tbl_userRoles",
			joinColumns = @JoinColumn(name="userId"),
			inverseJoinColumns = @JoinColumn(name="roleId"))
	private Set<Role> roles=new HashSet<>();

/*
	public User(String firstName, String lastName, Integer score, String address, String phone, Date birthDate, String email, String password, LocalDateTime createDate, String resetPasswordCode, Boolean builtIn, List<Loan> userBooks, Set<Role> roles) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.score = score;
		this.address = address;
		this.phone = phone;
		this.birthDate = birthDate;
		this.email = email;
		this.password = password;
		this.createDate = createDate;
		this.resetPasswordCode = resetPasswordCode;
		this.builtIn = builtIn;
		this.userBooks = userBooks;
		this.roles = roles;



	}*/
}
