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
import org.springframework.web.bind.annotation.RestController;
import com.test.DTO.CardDTO;
import com.test.DTO.CardListDTO;
import com.test.service.BoardService;
import com.test.service.CardService;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class CardController 
{
	@Autowired
	private CardService cardService;
	
	@Autowired
	private BoardService boardService;
	
	
	//showAllCard
	@GetMapping(value="/ShowAllCard/{boardId}")
	public ResponseEntity<List<CardDTO>> showCard(@PathVariable int boardId) throws NumberFormatException
	{
		List<CardDTO> card=boardService.showCard(boardId);
		return ResponseEntity.ok(card);
	}
	
	
	//create Custom Card
	@PostMapping(value = "/Card/{boardId}", produces = "application/json")
	public void createCustomCard(@RequestBody CardListDTO cardDto,@PathVariable int boardId) 
	{
		 cardService.createCustomCard(cardDto,boardId);
//		 System.out.println("6666666"+cardDto.getCardId());
		 
	}
	
	
	//delete
	@GetMapping(value="/CardDelete/{cardId}")
	public void deleteCard(@PathVariable String cardId)
	{
		cardService.deleteCard(cardId);
	}
	
	
	//update
	@PutMapping(value="/Card/{cardId}")
	public void updateCustomCard(@RequestBody CardDTO cardDto,@PathVariable String cardId)
	{
		cardService.updateCustomCard(cardDto,cardId);
	}

}
