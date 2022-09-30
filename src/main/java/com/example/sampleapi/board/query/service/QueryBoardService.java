package com.example.sampleapi.board.query.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.example.sampleapi.board.command.constant.Constant;
import com.example.sampleapi.board.command.domain.Board;
import com.example.sampleapi.board.query.dto.BoardDto;
import com.example.sampleapi.board.query.repository.QueryBoardRepository;

import com.example.sampleapi.redis.RedisOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Slf4j
public class QueryBoardService {
	private final QueryBoardRepository queryBoardRepository;

	private final RedisOperator<BoardDto> redisOperator;

	public int totalCount() {
		return (int) queryBoardRepository.count();
	}

	public List<BoardDto> boardList(int size, int page) {

		PageRequest pageRequest = PageRequest.of((page - 1), size);
		List<BoardDto> boardList = queryBoardRepository.boardList(pageRequest.getPageSize(), pageRequest.getOffset());
		return boardList;
	}

	public List<BoardDto> boardSearch(String search) {

		return queryBoardRepository.boardSearch(search);
	}

	public BoardDto board(int num) {

		// 1. 글번호로 Redis KEY를 만든다.
		String key = Constant.BOARD_KEY + num;

		// 2. 먼저 Redis Cache를 조회 한다. Redis Cache를 조회 중 에러가 발행해도 Exception이 발생하지 않고 null로 리턴된다.
		BoardDto boardDto = redisOperator.getValue(key);
		log.info("캐시 조회 boardDto : {}", boardDto);

		// 3. Cache에 데이터가 없는 경우 DB에서 조회 한다.
		if(boardDto == null) {
			log.info("DB 조회");
			boardDto = queryBoardRepository.board(num);

			// 3-1. DB에서 조회한 데이터 객체를 Redis Cache에 저장한다. 생명 주기는 각 업무마다 다를 수 있다 아래 예시는 10초 예시이다.
			redisOperator.set(key, boardDto, 10, TimeUnit.SECONDS);
		}

		// 4. BoardDTO return
		return boardDto;

	}

}
