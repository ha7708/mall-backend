package org.zerock.mallapi.service;

import org.zerock.mallapi.dto.TodoDTO;

public interface TodoService {
    Long register(TodoDTO todoDTO);

    // 조회용 메서드 추가 : TodoDTO 반환
    TodoDTO get(Long tno);

    // 수정
    void modify(TodoDTO todoDTO);

    // 삭제
    void remove(Long tno);


}

