package com.engineering.myoa.watchtower.support.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class ListResult<T> {

    public static <T> ListResult<T> of(Page<T> page) {
        return new ListResult<>(page.get().collect(Collectors.toList()), Paginate.of(page));
    }

    List<T> list;
    Paginate paginate;

}
