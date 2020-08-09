package com.denis.thymeleaf.thymeleaf.controller;
import java.util.List;

import com.denis.thymeleaf.thymeleaf.model.Board;
import com.denis.thymeleaf.thymeleaf.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
class BoardApiController {

    @Autowired
    private BoardRepository repository;
//    private final BoardRepository repository;
//
//    BoardApiController(BoardRepository repository) {
//        this.repository = repository;
//    }

    // Aggregate root

    @GetMapping("/boards")
    List<Board> all(@RequestParam(required = false, defaultValue = "") String title, @RequestParam(required = false,  defaultValue = "") String content) {

        if(StringUtils.isEmpty(title) && StringUtils.isEmpty(content)){

            return repository.findAll();
        }else{

            return repository.findByTitleOrContent(title, content);
//            return repository.findByTitle(title);
        }

    }

    @PostMapping("/boards")
    Board newBoard(@RequestBody Board newBoard) {
        return repository.save(newBoard);
    }

    // Single item

    @GetMapping("/boards/{id}")
    Board one(@PathVariable Long id) {

//        return repository.findById(id)
////                .orElseThrow(() -> new BoardNotFoundException(id));

        return repository.findById(id).orElse(null);
    }

    @PutMapping("/boards/{id}")
    Board replaceBoard(@RequestBody Board newBoard, @PathVariable Long id) {

        return repository.findById(id)
                .map(board -> {
                    board.setTitle(newBoard.getTitle());
                    board.setContent(newBoard.getContent());
                    return repository.save(board);
                })
                .orElseGet(() -> {
                    newBoard.setId(id);
                    return repository.save(newBoard);
                });
    }

    @DeleteMapping("/boards/{id}")
    void deleteBoard(@PathVariable Long id) {
        repository.deleteById(id);
    }
}