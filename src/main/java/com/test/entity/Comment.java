package com.test.entity;

import java.util.List;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
public class Comment {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
	
	@Column(length=8129)
	private String name;
	
	//private String date;
	@Column(columnDefinition = "tinyint(1) default 0")
    private boolean deleteStatus ;
	
	@Column()
	private String userName;
	
	private String date;
	
	private String time;
	
	@ManyToOne
	@JoinColumn(name="task_id")
	@JsonBackReference(value = "task")
	private Task task;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	@JsonBackReference
	private User user;

}
