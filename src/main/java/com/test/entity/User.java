package com.test.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "uid")
	private int id;
	private String name;
	private String email;
	private String password;
	@Transient
	private String confirm;
	private String role;
	private boolean  enable;
	@Lob
	private byte[] pic;
	private String picname;
	
//	@ManyToMany(mappedBy = "users")
//	private List<Board> boards;
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	@JsonBackReference(value= "userwork")
    private List<UserWorkSpace> userWorkSpace = new ArrayList<>();	
	 
	 @OneToMany(mappedBy = "user")
	 @JsonBackReference
	 private List<UserTask> userTask= new ArrayList<>();
	 
	 @OneToMany(mappedBy = "user")
	 private List<Comment> comment= new ArrayList<>();

	
}
