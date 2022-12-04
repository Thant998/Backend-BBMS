package com.test.DTO;


import lombok.Data;

@Data
public class WorkSpaceBoardDTO 
{
	
	private int boardId;
	private String boardName;
	
	
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
	public String getBoardName() {
		return boardName;
	}
	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}
	
	
}
