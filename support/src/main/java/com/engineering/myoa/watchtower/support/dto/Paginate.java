package com.engineering.myoa.watchtower.support.dto;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * Paginate
 * @author Shin Woo-jin (lp12254@linecorp.com)
 * @since 2020-04-07
 *
 */
@Builder
@AllArgsConstructor
@Getter
public class Paginate {
    public static Paginate of(Page page) {
        return builder()
                .number(page.getNumber())
                .pageSize(page.getSize())
                .totalCount(page.getTotalElements())
                .build();
    }

    private int number;
    private int pageSize;
    private long totalCount;

    public int getPageCount() {
        if (pageSize == 0) { return 0; }

        int count = (int) totalCount / pageSize;
        if (totalCount % pageSize > 0) {
            count++;
        }
        return count;

    }
}
