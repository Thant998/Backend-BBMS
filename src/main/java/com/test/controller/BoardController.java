package com.test.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.test.DTO.BoardDTO;
import com.test.service.BoardService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")

public class BoardController 
{
	@Autowired
	private BoardService boardService;
	
	
	//all board by workspaceId
	@GetMapping(value="/portal/boards/{workspaceId}")
	public List<BoardDTO> allBoard (@PathVariable int workspaceId)
	{
		System.out.print(boardService.allBoard(workspaceId));
		return boardService.allBoard(workspaceId);
	}
	
	
	//insert 
	@PostMapping(value = "/portal/boards/", produces = "application/json")
	public BoardDTO createBoard(@RequestBody BoardDTO boardDto) 
	{
		System.out.println("board name controller: "+boardDto.getWorkspaceId());
		boardService.createBoard(boardDto);
		boardService.createCardAfterBoard();
		return boardDto;
	}
	
	
//	update
	@PutMapping(value="portal/board/updateBoard")
	public ResponseEntity<BoardDTO> updateBoard(@RequestBody BoardDTO dto)
	{
		boardService.updateBoard(dto);
		return ResponseEntity.ok(dto);
	}
	
	
	//delete
	@GetMapping(value="portal/boards/delete/{boardId}")
	public void deleteBoard(@PathVariable int boardId)
	{
		boardService.deleteBoard(boardId);
	}
	
	
	//search
	@GetMapping(value="/portal/boards/search/{workspaceId}/")
	public List<BoardDTO> boardSearch(@RequestParam(value="name",required=false)String boardName,@PathVariable int workspaceId)
	{
		return boardService.search("%"+boardName+"%",workspaceId);
	}
	
	
	//save Recent View
//	@GetMapping(value="portal/boards/recentView/{boardId}")
//	public void recentView(@PathVariable int boardId)
//	{
//		System.out.println("haha"+ " "+ boardId);
//		boardService.recentView(boardId);
//		System.out.println(boardId);
//	}
	

	
	
	//show one board
	@GetMapping(value="/portal/todo/{boardId}")
	public ResponseEntity<BoardDTO> showOneBoard(@PathVariable int boardId)
	{
		BoardDTO dto=boardService.showOne(boardId);
		return ResponseEntity.ok(dto);
	}
	
}
