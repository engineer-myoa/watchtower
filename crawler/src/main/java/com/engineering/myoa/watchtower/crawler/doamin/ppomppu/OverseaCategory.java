package com.engineering.myoa.watchtower.crawler.doamin.ppomppu;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * OverseaCategory
 * @author Shin Woo-jin (lp12254@linecorp.com)
 * @since 2020-04-07
 *
 */
@Getter
@AllArgsConstructor
public enum OverseaCategory {
    ETC("기타", "1"),
    HOME_APPLIANCE("가전", "7"),
    VIDEO("TV/영상", "8"),
    COMPUTER("컴퓨터", "3"),
    DIGITAL("디지털", "4"),
    MOBILE_ACCESSORY("폰액세서리", "9"),
    DRESS("의류/잡화", "5"),
    WATCH("시계", "2"),
    SHOES("신발", "11"),
    FOOD("식품/건강", "10"),
    PARENTING("육아", "6"),
    ;

    private String description;
    private String page;
}
