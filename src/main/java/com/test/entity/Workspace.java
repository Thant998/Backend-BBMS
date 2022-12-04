package com.test.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "workspace")
public class Workspace implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "wid")
	private int id;
	private String name;
	@Column(length = 900000)
	private String description;
	
	@Column(columnDefinition = "tinyint(1) default 0")
    private boolean deleteStatus ;
	
//	@OneToMany(mappedBy = "workspaces",cascade = CascadeType.ALL)
//	private List<Board> boards;
	
	@OneToMany(mappedBy = "workspace",targetEntity = Board.class)
	@JsonBackReference(value="board")
    private List<Board> board = new ArrayList<>();
	
	
	@OneToMany(mappedBy = "workspace",targetEntity = UserWorkSpace.class,cascade = CascadeType.ALL)
	@JsonBackReference
	private List<UserWorkSpace> workspaceUser;

	@Transient
	private int userId;
	
	@Transient
	private String[] inviteEmail;
		
	
}
