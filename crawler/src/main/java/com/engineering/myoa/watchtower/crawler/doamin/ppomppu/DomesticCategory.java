package com.engineering.myoa.watchtower.crawler.doamin.ppomppu;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DomesticCategory
 * @author Shin Woo-jin (lp12254@linecorp.com)
 * @since 2020-04-07
 *
 */
@Getter
@AllArgsConstructor
public enum DomesticCategory implements PpomppuCategory {
    ETC("기타", "1"),
    COMPUTER("컴퓨터", "4"),
    DIGITAL("디지털", "5"),
    FOOD("식품/건강", "6"),
    BOOK("서적", "8"),
    HOME_APPLIANCE("가전/가구", "9"),
    PARENTING("육아", "10"),
    GIFT_CARD("상품권", "11"),
    DRESS("의류/잡화", "12"),
    COSMETIC("화장품", "13"),
    CAMPING("등산/캠핑", "15"),
    ;

    private String description;
    private String page;
    private static final String API_NAME = "ppomppu-domestic";
    private static final String MESSAGE_PREFIX = "국내 게시판 - ";

    @Override
    public String getApiUnitName() {
        return this.API_NAME;
    }

    @Override
    public String getMessagePrefix() {
        return this.MESSAGE_PREFIX;
    }

    @Override
    public String getCode() {
        return this.name();
    }
}