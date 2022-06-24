package com.hanghae.justpotluck.domain.process.controller;

import com.hanghae.justpotluck.domain.board.service.BoardService;
import com.hanghae.justpotluck.domain.process.dto.request.ProcessSaveRequest;
import com.hanghae.justpotluck.domain.process.dto.request.ProcessUpdateRequestDto;
import com.hanghae.justpotluck.domain.process.dto.response.ProcessResponseDto;
import com.hanghae.justpotluck.domain.process.service.RecipeProcessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api/board")
@RequiredArgsConstructor
@RestController
public class ProcessController {

    private final RecipeProcessService processService;
    private final BoardService boardService;

    @PostMapping("/{boardId}/process")
    public ProcessResponseDto saveProcess(@ModelAttribute ProcessSaveRequest requestDto, @PathVariable Long boardId) {
        return processService.saveProcess(requestDto, boardId);
    }

    @PatchMapping("/process/{processId}")
    public ResponseEntity<ProcessResponseDto> updateProcess(@ModelAttribute ProcessUpdateRequestDto requestDto,
                                                               @PathVariable Long processId) {
        return ResponseEntity.ok(processService.updateProcess(processId, requestDto));
    }

    @DeleteMapping("/process/{processId}")
    public ResponseEntity<Void> deleteProcess(@PathVariable Long processId) {
        processService.deleteProcess(processId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
