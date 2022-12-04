package com.test.entity;

import java.io.Serializable;
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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserWorkSpace implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
	
	@ManyToOne(targetEntity = User.class,cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private User user;
	
	@ManyToOne(targetEntity = Workspace.class,cascade = CascadeType.ALL)
    @JoinColumn(name="workspace_id")
    private Workspace workspace;
	
	@Column(columnDefinition = "tinyint(1) default 0")
    private boolean deleteStatus ;
	

	private String status; 
	private String token;
	


	
}
