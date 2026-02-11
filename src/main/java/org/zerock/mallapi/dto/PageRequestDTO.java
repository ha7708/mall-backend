package org.zerock.mallapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

// PageRequestDTO :: 페이징 객체
@Data // Getter + Setter + toString + equals + hashCode + RequiredArgsConstructor 자동 생성
@SuperBuilder // 상속 구조에서도 Builder 패턴을 쓰게 해주는 Lombok
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {
    @Builder.Default // Builder로 객체 생성할 때 “기본값 유지” 하도록 함 : Builder는 기본적으로는 초기값을 무시하므로
    private int page = 1; // 기본값이 0이므로 1로 고정시키기 위함임

    @Builder.Default // 외부에서 Builder 패턴을 써서 size를 변경하도록 시도하더라도 변경되지 않도록 함
    private int size = 10;
}
