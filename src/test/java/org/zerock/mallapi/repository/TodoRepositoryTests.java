package org.zerock.mallapi.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.mallapi.domain.Todo;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
// @Transactional 을 붙이게 되면 테스트 종료 시 자동 롤백 됨.
// 그러나 테스트용 DB를 따로 쓰는 것이 바람직함.
// 예: src/test/resources/application.properties에 테스트 DB 설정
@Log4j2
public class TodoRepositoryTests {

    @Autowired
    private TodoRepository todoRepository;

    @Test
    public void test1() {
        log.info("------------------");
        log.info(todoRepository);
    }

    // 데이터 추가 테스트
    @Test
    public void testInsert() {
        for (int i = 1; i <= 100; i++) {
            Todo todo = Todo.builder()
                    .title("Title..." + i)
                    .dueDate(LocalDate.of(2023, 12, 31))
                    .writer("user00")
                    .build();

            todoRepository.save(todo);

        }
    }

    // 데이터 조회 테스트
    @Test
    public void testRead() {
        Long tno = 33L; // DB에 존재하는 번호로 확인해야함
        java.util.Optional<Todo> result = todoRepository.findById(tno);
        Todo todo = result.orElseThrow();
        log.info(todo);
        System.out.println(todo);
    }

    // 데이터 수정 테스트
    @Test
    public void testModify() {
        Long tno = 33L;
        Optional<Todo> result = todoRepository.findById(tno); // java.util 패키지의 Optional

        Todo todo = result.orElseThrow();
        todo.changeTitle("Modified 33...");
        todo.changeComplete(true);
        todo.changeDueDate(LocalDate.of(2023, 10, 10));

        todoRepository.save(todo);
    }

    // 데이터 삭제 테스트
    @Test
    public void testDelete() {
        Long tno = 1L;
        todoRepository.deleteById(tno);
    }

    // 페이징 처리 테스트
    @Test
    public void testPaging() {
        // import org.springframework.data.domain.Pageable;
        // PageRequest.of( 페이지번호, 사이즈, Sort.by("컬럼명").descending() );
        // findAll() 결과 -> Page<엔티티> 타입 리턴
        Pageable pageable =
                PageRequest.of(0, 10, Sort.by("tno").descending());

        // 여기서 result 는 그냥 리스트가 아닌 페이지 메타데이터 + 데이터 묶음 객체
        // Page<T>는 “데이터 리스트 + 페이징 정보 + 이동 제어” 통합 객체
        // 실무에선 getContent()로 데이터 쓰고, getTotalPages()/hasNext()로 UI 페이징 만든다.
        Page<Todo> result = todoRepository.findAll(pageable);

        System.out.println("전체 페이지: " + result.getTotalPages());
        System.out.println("전체 개수: " + result.getTotalElements());
        System.out.println("현재 페이지: " + result.getNumber());

        result.getContent().forEach(todo -> {
            System.out.println(todo);
        });

        /*
        Pageable 객체의 다양한 활용법
        =========================================================

        // 활용 1 : 데이터 꺼내기 (단순)
        List<Todo> list = result.getContent();
        list.forEach(System.out::println);

        // 활용 2 : 페이지 정보 (UI 페이징에 필수)
        // → 페이징 버튼 / 페이지 번호 / “더보기” 버튼 만들 때 사용
        int totalPages = result.getTotalPages();      // 전체 페이지 수
        long totalCount = result.getTotalElements(); // 전체 데이터 개수
        int pageNumber = result.getNumber();          // 현재 페이지 (0부터)
        int pageSize = result.getSize();              // 페이지당 개수
        boolean hasNext = result.hasNext();           // 다음 페이지 존재?
        boolean hasPrev = result.hasPrevious();       // 이전 페이지 존재?

        // 활용 3 : 서비스 계층에서 DTO로 변환 (실무 패턴)
        Page<TodoDTO> dtoPage = result.map(
                todo -> new TodoDTO(
                        todo.getTno(),
                        todo.getTitle(),
                        todo.getWriter()
                ));

        // 활용 4 : Controller에서 프론트로 넘기는 구조 (Spring MVC)
        @GetMapping("/list")
        public String list(Pageable pageable, Model model) {

            Page<Todo> result = todoService.getList(pageable);

            model.addAttribute("list", result.getContent());
            model.addAttribute("totalPages", result.getTotalPages());
            model.addAttribute("page", pageable.getPageNumber());

            return "todo/list";
        }

        // 활용 5 : REST API에서 JSON으로 반환 (React / Vue용)
        @GetMapping("/list")
        public Page<TodoDTO> list(Pageable pageable) {
            return todoService.getList(pageable);
        }
        → 프론트에서:
        response.data.content        // 데이터
        response.data.totalPages     // 전체 페이지
        response.data.number         // 현재 페이지
        response.data.size           // 페이지 크기

        // 활용 6 : 다음 페이지 요청
        Pageable pageable = PageRequest.of(1, 10); // 2번째 페이지
        또는
        Pageable nextPage = result.nextPageable();

         */
    }

}
