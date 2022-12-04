package com.test.entity;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
	
	@Column(length=8129)
	private String name;
	//private String date;
	@Column(columnDefinition = "tinyint(1) default 0")
    private boolean deleteStatus ;
	
	@OneToMany(mappedBy = "board")
    private List<Card> card = new ArrayList<>();
	
	@ManyToOne
	@JoinColumn(name="workspace_id")
	@JsonBackReference
	private Workspace workspace;
	
	@Column()
    private String recentView ;
	

	
}
