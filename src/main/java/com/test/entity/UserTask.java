package com.test.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserTask  implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
	
	@ManyToOne(targetEntity = User.class,cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private User user;
	
	@ManyToOne(targetEntity = Task.class,cascade = CascadeType.ALL)
    @JoinColumn(name="task_id")
    private Task task;
	
	@Column(columnDefinition = "tinyint(1) default 0")
    private boolean deleteStatus ;
	
}
