package com.library.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import javax.persistence.*;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@JsonIgnoreProperties(value= {"handler","hibernateLazyInitializer","FieldHandler"})
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	@Column(nullable = true)
	private LocalDate birthDate;

	@Column(length = 80, nullable = false, unique = true)
	private String email;

	@Column(nullable=false)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

	@Column(nullable=false)
	private LocalDateTime createDate;

	@Column
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String resetPasswordCode;

	@Column(nullable=false)
	private Boolean builtIn=false;

	@JsonIgnore
	@OneToMany(mappedBy = "userLoan")
	private List<Loan> userBooks=new ArrayList<>();


	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name="tbl_userRoles",
			joinColumns = @JoinColumn(name="userId"),
			inverseJoinColumns = @JoinColumn(name="roleId"))
	private Set<Role> roles=new HashSet<>();


}
