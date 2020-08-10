package com.denis.thymeleaf.thymeleaf.controller;

import com.denis.thymeleaf.thymeleaf.model.Board;
import com.denis.thymeleaf.thymeleaf.repository.BoardRepository;
import com.denis.thymeleaf.thymeleaf.validator.BoardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardValidator boardValidator;

    @GetMapping("/list")
    public String list(Model model, @PageableDefault(size = 5) Pageable pageable){

//        List<Board> boardList = boardRepository.findAll();
//        Page<Board> boardList = boardRepository.findAll(PageRequest.of(0,20));
        Page<Board> boardList = boardRepository.findAll(pageable);
        int startPage = Math.max(1, boardList.getPageable().getPageNumber() -4);
        int endPage   = Math.min(boardList.getTotalPages(), boardList.getPageable().getPageNumber() + 4);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("boardList", boardList);
        return "board/list";
    }

    @GetMapping("/form")
    public String greetingForm(Model model, @RequestParam(required = false) Long id) {

        if(id == null){

            model.addAttribute("board", new Board());
        }else{

            Board board = boardRepository.findById(id).orElse(null); //orElse: 조회 결과가 없을경우 null을 넣어준다
            model.addAttribute("board", board);
        }

        return "board/form";
    }

    @PostMapping("/form")
    public String greetingSubmit(@Valid Board board, BindingResult bindingResult) {

        boardValidator.validate(board, bindingResult);
        if (bindingResult.hasErrors()){//DTO에서 선언안 not null 및 size 옵션을 체크한다

            return "board/form";
        }

        boardRepository.save(board);
        return "redirect:/board/list"; //board/listf를 호출하면 값을 뿌려주지 않고 리스트 페이지로 이동 그래서 redirect로 이동해서 해당 위에 list controller 호출
    }
}
