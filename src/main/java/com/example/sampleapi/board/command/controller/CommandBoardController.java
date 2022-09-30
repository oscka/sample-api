package com.example.sampleapi.board.command.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sampleapi.board.command.domain.Board;
import com.example.sampleapi.board.command.service.CommandBoardService;
import com.example.sampleapi.common.dto.ResultMessage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/sample-api/v1/boards")
@RequiredArgsConstructor
@Tag(name = "게시판", description = "게시판")
@Slf4j
public class CommandBoardController {
    private final CommandBoardService commandService;

	@Operation(summary = "게시판 입력", description = "게시판 입력 기능입니다.")
	@PostMapping()
	public ResponseEntity<ResultMessage> insert( @RequestBody  Board paramBoard) {		
		Board board  = commandService.insert(paramBoard);
        if(board.getNum() > 0){
            return getResponseEntity(1);
        }
		return getResponseEntity(0);
		
	}

	@Operation(summary = "게시판 수정", description = "게시판 수정 기능입니다.")
	@PutMapping()
	public ResponseEntity<ResultMessage> updateBoard(@RequestBody Board paramBoard) {
		int result = commandService.updateBoard(paramBoard);
		return getResponseEntity(result);
		
	}

	@Operation(summary = "게시판 삭제", description = "게시판 삭제 기능입니다.")
	@DeleteMapping("/{num}")
	public ResponseEntity<ResultMessage> delete(@PathVariable("num") int num) {
		int result = commandService.delete(num);
		return getResponseEntity(result);

	}

    private ResponseEntity<ResultMessage> getResponseEntity(int result) {
		if(result > 0) {		
			ResultMessage resultMessage= ResultMessage.builder().successYn("Y").message("정상").build();		 
			return ResponseEntity.ok(resultMessage);
		}	
		ResultMessage resultMessage= ResultMessage.builder().successYn("N").message("오류").build();			
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMessage);	
	}
	
    
}
