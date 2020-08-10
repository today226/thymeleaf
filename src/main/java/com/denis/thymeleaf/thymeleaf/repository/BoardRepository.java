package com.denis.thymeleaf.thymeleaf.repository;

import com.denis.thymeleaf.thymeleaf.model.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByTitle(String title);  //인터페이스만 정의 하면 알아서 구현이 된다
    List<Board> findByTitleOrContent(String title, String content); //or 검색
    Page<Board> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
}
