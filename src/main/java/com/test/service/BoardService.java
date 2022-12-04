package com.test.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.test.DTO.BoardDTO;
import com.test.DTO.CardDTO;
import com.test.entity.Board;
import com.test.entity.Card;
import com.test.entity.Workspace;
import com.test.repo.BoardRepository;
import com.test.repo.CardRepository;
import com.test.repo.WorkspaceRepo;

@Service
public class BoardService 
{
	@Autowired
	BoardRepository boardRepository;
	
	@Autowired
	CardRepository cardRepository;
	
	@Autowired
	WorkspaceRepo  wpR;	
	
	
	//insert
	 public void  createBoard(BoardDTO boardDto)
	 {
		 	Board board=new Board();
		 	Workspace wp=wpR.findByIdAndDeleteStatus1(boardDto.getWorkspaceId(),false);
		 	board.setName(boardDto.getBoardName());
		 	Date date = new Date();
			long msec = date.getTime();
			String s = String.valueOf(msec);
			System.out.println("test mili second"+s);
		 	board.setWorkspace(wp);
		 	boardRepository.save(board);
	 }
	 
	 //update
	 public void updateBoard(BoardDTO dto)
	 {
		 Board board=boardRepository.findByIdAndDeleteStatus(dto.getBoardId(), false);
		 board.setName(dto.getBoardName());
		 boardRepository.save(board);
	 }
	 
	 //delete
	 public void deleteBoard(int boardId)
	 {
		 Board board=boardRepository.findByIdAndDeleteStatus(boardId, false);	
		 board.setDeleteStatus(true);
		 boardRepository.save(board);
	 }
	 
	 //card generate
	 public void createCardAfterBoard()
	 {
		 int boardId=boardRepository.selectLastRow(); 
		 Board board=new Board();
		 Date date = new Date();
		 long msec = date.getTime();
		 String s = String.valueOf(msec);	
		 board.setId(boardId);	
		 
		 Card card=new Card();
		 card.setId(s);
		 card.setName("TO DO");
		 card.setType("default");
		 card.setBoard(board);
		 cardRepository.save(card);	
		 
		 Card card1=new Card();
		 card1.setId(s+"1");
		 card1.setName("Doing");
		 card1.setType("default");
		 card1.setBoard(board);
		 cardRepository.save(card1);
		 
		 Card card2=new Card();
		 card2.setId(s+"2");
		 card2.setName("Done");
		 card2.setType("default");
		 card2.setBoard(board);
		 cardRepository.save(card2);		 
	 }
	 
	 //cardshow
	 public List<CardDTO> showCard (int  boardId)
	 {
		 
		 List <CardDTO>cardDTOList=new ArrayList <CardDTO>();
		 List<Card> cardList=cardRepository.findAllByBoardIdAndDeleteStatus(boardId, false);
		 for (Card card : cardList) 
		 {
	           CardDTO cardDto = new CardDTO();
	           cardDto.setCardId(card.getId());
	           cardDto.setCardName(card.getName());
	           cardDto.setType(card.getType());
	           cardDTOList.add(cardDto);
	          
		 }
		 return cardDTOList;
	 }
	 
	 //show Board
	 public List<BoardDTO> allBoard (int workspaceId)
	 {
		 List<Board> board=boardRepository.allBoard(workspaceId);
		 List<BoardDTO> list=new ArrayList<>();
		 for(Board b:board)
		 {
			 BoardDTO dto=new BoardDTO();
			 dto.setBoardId(b.getId());
			 dto.setBoardName(b.getName());
			 dto.setWorkspaceId(b.getWorkspace().getId());
			 list.add(dto);
		 }
		 return list;
	 }
	 
	 public List<BoardDTO> search (String boardName,int workspaceId)
	 {
		 List<BoardDTO> list =new ArrayList<>();
		 List<Board> board=boardRepository.search(boardName,workspaceId);
		 for(Board b:board)
		 {
			BoardDTO dto=new BoardDTO();
			dto.setBoardId(b.getId());
			System.out.println("boardId"+b.getId());
			dto.setBoardName(b.getName());
			dto.setWorkspaceId(b.getWorkspace().getId());
			list.add(dto);
		}
		 return list;	 
	 }
	 
	 //recent view
	 public void recentView(int boardId)
	 {
		 Board board=boardRepository.findByIdAndDeleteStatus(boardId, false);
		 board.setId(boardId);
		 board.setRecentView("yes");
		 boardRepository.save(board);
	 }
	 
	 public List<BoardDTO> recendViewBoard ()
	 {
		 List<Board> board=boardRepository.recentView( "yes" , false);
		 List<BoardDTO> list=new ArrayList<>();
		 for(Board b:board)
		 {
			 BoardDTO dto=new BoardDTO();
			 dto.setBoardId(b.getId());
			 dto.setBoardName(b.getName());
			 list.add(dto);
		 }
		 return list;
	 }
	 
	//show one board
	public BoardDTO showOne(int boardId)
	{
		Board board =boardRepository.findByIdAndDeleteStatus(boardId, false);
		BoardDTO dto=new BoardDTO();
		dto.setBoardId(board.getId());
		dto.setBoardName(board.getName());
		return dto;
	}
	 
}
