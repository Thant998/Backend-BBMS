package com.test.service;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.test.DTO.CardDTO;
import com.test.DTO.CardListDTO;
import com.test.entity.Card;
import com.test.repo.BoardRepository;
import com.test.repo.CardRepository;

@Service
public class CardService 
{

	@Autowired
	BoardRepository boardRepository;
	
	@Autowired
	CardRepository cardRepository;
	
	
	//CreateCustomCard
	public void createCustomCard(CardListDTO cardListDto,int boardId)
	{
//		System.out.println("Get Card Id : "+cardDto.getCardId());
//		System.out.println("Get Board Id"+boardId);
		for (CardDTO cardDto : cardListDto.getList()) {
		Card card2 = cardRepository.selectSameId(cardDto.getCardId());
		if (card2==null)
		{
			System.out.println("insert"+cardDto.getCardId());
			Card cardObj2 = new Card();
			Date date = new Date();
			long msec = date.getTime();
			cardObj2.setId(String.valueOf(msec));
			System.out.println("reach here llllllll"+cardDto.getCardId());
			cardObj2.setName(cardDto.getCardName());
			cardObj2.setType("custom");
			cardObj2.setBoard(boardRepository.findByIdAndDeleteStatus(boardId, false));
			cardRepository.save(cardObj2);		
		}
		else
		{
			Card cardObj2 = new Card();
			//Date date = new Date();
			//long msec = date.getTime();
			System.out.println("update"+cardDto.getCardId());
			cardObj2.setId(cardDto.getCardId());
			cardObj2.setName(cardDto.getCardName());
			cardObj2.setType(cardDto.getType());
			cardObj2.setBoard(boardRepository.findByIdAndDeleteStatus(boardId, false));
			cardRepository.save(cardObj2);
		}
		}
	}
//		cardObj.setName(cardDto.getCardName());
//		cardObj.setDeleteStatus(false);
//		cardObj.setBoard(boardList.get(0));
//		cardRepository.save(cardObj);
//		for (Card c  : card2)
//			
//		{
//			System.out.println("aaa");
////		if (!cardDto.getCardName().equals("TO DO") && !cardDto.getCardName().equals("Doing") && !cardDto.getCardName().equals("Done") && !cardDto.getCardName().equals(c.getName())) {
//			System.out.println("##########"+cardDto.getCardId());
//			if ( !cardDto.getCardId().equals(c.getId()))
//			{
//
//				System.out.println("insert"+cardDto.getCardId());
//				Card cardObj2 = new Card();
//				Date date = new Date();
//				long msec = date.getTime();
//				cardObj2.setId(String.valueOf(msec));
//				System.out.println("reach here llllllll"+cardDto.getCardId());
//				cardObj2.setName(cardDto.getCardName());
//				cardObj2.setBoard(boardRepository.findByIdAndDeleteStatus(boardId, false));
//				cardRepository.save(cardObj2);
//				break ;
//				
////				Card cardObj = cardRepository.findByIdAndDeleteStatus(cardDto.getCardId(), false);
////				System.out.println("reach here"+cardDto.getCardId());
////				cardObj.setId(cardDto.getCardId());
////				cardObj.setName(cardDto.getCardName());
////				cardRepository.save(cardObj);
////				System.out.println("insert"+cardDto.getCardId());
////				Card cardObj2 = new Card();
////				Date date = new Date();
////				long msec = date.getTime();
////				cardObj2.setId(String.valueOf(msec));
////				System.out.println("reach here llllllll"+cardDto.getCardId());
////				cardObj2.setName(cardDto.getCardName());
////				cardObj2.setBoard(boardRepository.findByIdAndDeleteStatus(boardId, false));
////				cardRepository.save(cardObj2);
//				
//			}
//			else
//			{		
//				Card cardObj2 = new Card();
//				Date date = new Date();
//				long msec = date.getTime();
//				System.out.println("update"+cardDto.getCardId());
//				cardObj2.setId(cardDto.getCardId());
//				cardObj2.setName(cardDto.getCardName());
//				cardObj2.setBoard(boardRepository.findByIdAndDeleteStatus(boardId, false));
//				cardRepository.save(cardObj2);
//				
//				break ;
//				
//			}
//		
//	}		

	
	//update
	public void updateCustomCard(CardDTO cardDto,String cardId)
	{
		Card card=cardRepository.findByIdAndDeleteStatus(cardId,false);
		card.setName(cardDto.getCardName());
		cardRepository.save(card);
	}
	
	//delete 
	public void deleteCard(String cardId)
	{
		Card card=cardRepository.findByIdAndDeleteStatus(cardId,false);
		card.setDeleteStatus(true);
		cardRepository.save(card);
	}
	
}
