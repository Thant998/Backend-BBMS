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
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card  implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
//    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private String id;
	private String name;
	private String type;
	@Column(columnDefinition = "tinyint(1) default 0")
    private boolean deleteStatus ;


	
	@ManyToOne
    @JoinColumn(name="board_id")
	@JsonBackReference
	@ToString.Exclude
    private Board board;
	
	@OneToMany(mappedBy = "card")
    private List<Task> task = new ArrayList<>();

	

	
}
