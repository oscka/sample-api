package com.example.sampleapi.board.query.controller;

import java.util.List;

import com.example.sampleapi.board.query.dto.BoardDto;
import com.example.sampleapi.board.query.service.QueryBoardService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/sample-api/v1/boards")
@RequiredArgsConstructor
@Slf4j
public class QueryBoardController {
    private final QueryBoardService queryService;

    @GetMapping("/totalCount")
	public int totalCount() {
		return  (int) queryService.totalCount();
	}

    @GetMapping("/list")
	public  List<BoardDto>  boardList(@RequestParam(value="size", defaultValue = "10")  int size , @RequestParam(value="page", defaultValue = "1") int page) {
		List<BoardDto> boardList = queryService.boardList(size, page);
		return boardList;
	}

	@GetMapping("/search")
	public  List<BoardDto>  boardSearch(@RequestParam("search") String search) {
		return queryService.boardSearch(search+"%");
	}

	@GetMapping("/{num}")
	public BoardDto board(@PathVariable("num") int num) {		
		BoardDto boardDto = queryService.board(num);
		return boardDto;

	}

    
}
