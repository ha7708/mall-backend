package org.zerock.mallapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.mallapi.domain.Todo;
import org.zerock.mallapi.dto.PageRequestDTO;
import org.zerock.mallapi.dto.PageResponseDTO;
import org.zerock.mallapi.dto.TodoDTO;
import org.zerock.mallapi.repository.TodoRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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


    // 페이징 수행
    @Override
    public PageResponseDTO<TodoDTO> list(PageRequestDTO pageRequestDTO) {
        // pageRequestDTO 는 사용자가 요청한 페이지번호, 페이지 크기를 가지고 있음 (파라미터로 넘어옴)

        Pageable pageable =
                PageRequest.of(
                        pageRequestDTO.getPage() - 1, // 1 페이지가 0 이므로 주의
                        pageRequestDTO.getSize(),
                        Sort.by("tno").descending()
                );  // 사용자가 요청한 페이지와 페이지 크기 정보를 이용하여 Pageable 객체를 만듬
        Page<Todo> result = todoRepository.findAll(pageable); // 앞서 만든 Pageable 객체를 사용하여 조회

        List<TodoDTO> dtoList = result.getContent().stream()
                .map(todo -> modelMapper.map(todo, TodoDTO.class))
                .collect(Collectors.toList());

        long totalCount = result.getTotalElements();

        // PageResponseDTO 객체를 Builder로 생성
        // 이 Builder가 호출하는 건 DTO 생성자 임 -> 페이지 버튼 구조 계산 용도
        PageResponseDTO<TodoDTO> responseDTO =
                PageResponseDTO.<TodoDTO>withAll() // @Builder(builderMethodName = "withAll") = builder() 대신 withAll() 쓰기로 함
                        .dtoList(dtoList) // 현재 페이지 데이터
                        .pageRequestDTO(pageRequestDTO) // 요청 정보(page,size)
                        .totalCount(totalCount) // 전체 데이터 개수
                        .build();

        return responseDTO;
    }

}
