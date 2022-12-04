package com.test.entity;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
//    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private String id;
	private String name;
	private String startDate;
	private String endDate;
	private String startTime;
	private String endTime;
	private int status;
	
	@Column(length=8129)
	private String description;
	
	@Column(columnDefinition = "tinyint(1) default 0")
    private boolean deleteStatus ;
	
	@ManyToOne
    @JoinColumn(name="card_id")
	@JsonBackReference
    private Card card;
	 
	 @OneToMany(mappedBy = "task")
	 private List<Activity> activity= new ArrayList<>();

	 @OneToMany(mappedBy = "task")
	 private List<Comment> comment= new ArrayList<>();
	 
	 @OneToMany(mappedBy = "task")
	 private List<UserTask> userTask= new ArrayList<>();
	 	
		
}
