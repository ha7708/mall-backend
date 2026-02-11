package org.zerock.mallapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.mallapi.domain.Todo;
import org.zerock.mallapi.dto.TodoDTO;
import org.zerock.mallapi.repository.TodoRepository;

import java.util.Optional;

@Service
@Transactional // 서비스객체 구성시 필수
@Log4j2
@RequiredArgsConstructor // 생성자 자동 주입
public class TodoServiceImpl implements TodoService {
    // 자동주입 대상은 final 로
    private final ModelMapper modelMapper;
    private final TodoRepository todoRepository;

    // 등록
    @Override
    public Long register(TodoDTO todoDTO) {
        log.info(".........");

        // modelMapper 이용하여 DTO -> Entity 변환
        Todo todo = modelMapper.map(todoDTO, Todo.class);

        // DB 작업 수행후 엔티티 반환
        Todo savedTodo = todoRepository.save(todo);

        // 결과로 엔티티의 PK 리턴
        return savedTodo.getTno();
    }

    // 조회
    @Override
    public TodoDTO get(Long tno) {
        Optional<Todo> result = todoRepository.findById(tno);
        Todo todo = result.orElseThrow();
        TodoDTO dto = modelMapper.map(todo, TodoDTO.class);
        return dto;
    }

    // 수정
    @Override
    public void modify(TodoDTO todoDTO) {
        Optional<Todo> result = todoRepository.findById(todoDTO.getTno());
        Todo todo = result.orElseThrow();
        todo.changeTitle(todoDTO.getTitle());
        todo.changeDueDate(todoDTO.getDueDate());
        todo.changeComplete(todoDTO.isComplete());

        todoRepository.save(todo);
    }

    // 삭제
    @Override
    public void remove(Long tno) {
        todoRepository.deleteById(tno);
    }

}
