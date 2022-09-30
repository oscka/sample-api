package com.example.sampleapi.board.query.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.sampleapi.board.query.dto.BoardDto;
import com.example.sampleapi.board.query.repository.QueryBoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QueryBoardService {
	private final QueryBoardRepository queryBoardRepository;

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
		BoardDto board = queryBoardRepository.board(num);
		return board;

	}

}
