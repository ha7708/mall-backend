package org.zerock.mallapi.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RootConfig {

    @Bean
    public ModelMapper getMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true) //getter/setter 없어도 필드 직접 매칭, private 필드도 매핑 가능
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE) // private 필드까지 접근 허용
                .setMatchingStrategy(MatchingStrategies.LOOSE); // 느슨한 매칭. 필드 이름이 완전히 같지 않아도 매핑

        return modelMapper;
    }

}
