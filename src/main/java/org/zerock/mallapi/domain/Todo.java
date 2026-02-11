package org.zerock.mallapi.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "tbl_todo")
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB가 알아서 PK(auto_Increment) 를 만들어라. (기본)
    // 이외에도 SEQUENCE (Oracle, PostgreSQL) 가 쓰인다. AUTO 는 JPA가
    private Long tno;

    private String title;
    private String writer;
    private boolean complete;
    private LocalDate dueDate;

    // 수정 테스트를 위해 메서드 추가
    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeComplete(boolean complete) {
        this.complete = complete;
    }

    public void changeDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
