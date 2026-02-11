package org.zerock.mallapi.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// PageResponseDTO :: 페이징 계산법 정의
@Data
public class PageResponseDTO<E> {
    private List<E> dtoList;

    private List<Integer> pageNumList;

    private PageRequestDTO pageRequestDTO;

    private boolean prev, next;

    private int totalCount, prevPage, nextPage, totalPage, current;

    @Builder(builderMethodName = "withAll")
    public PageResponseDTO(List<E> dtoList, PageRequestDTO pageRequestDTO, long totalCount) {
        this.dtoList = dtoList; // 현재 페이지 데이터
        // ★ 이때의 pageRequestDTO 는 사용자가 요청한 페이지번호 & 페이지 크기 값을 가지고 있음
        this.pageRequestDTO = pageRequestDTO;
        this.totalCount = (int) totalCount; // 전체 데이터 개수

        // end : 페이지를 10개 단위 블록으로 묶기
        // 예를 들어 page가 13일 때..... 13 / 10 = 1.3 → ceil = 2
        // → 2 * 10 = 20 → end = 20
        int end = (int) (Math.ceil(pageRequestDTO.getPage() / 10.0)) * 10;

        // start : 현재 블록 시작
        // int start = end - 9;
        // 20 - 9 = 11
        int start = end - 9;

        // last : 전체 페이지 개수
        // 235 / 10 = 23.5 → ceil = 24  → 전체 페이지 = 24
        int last = (int) (Math.ceil((totalCount / (double) pageRequestDTO.getSize())));

        // end 보정 : 끝 페이지 초과 방지. 작은 쪽의 값 따름
        end = end > last ? last : end;

        // 현재 블록 전 or 후에도 데이터가 남아있으면 true
        this.prev = start > 1;
        this.next = totalCount > end * pageRequestDTO.getSize();
        this.pageNumList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());

        if (prev) this.prevPage = start - 1;
        if (next) this.nextPage = end + 1;

        this.totalPage = this.pageNumList.size(); // 현재 블록 페이지 개수
        this.current = pageRequestDTO.getPage();
    }


}
