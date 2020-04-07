package com.engineering.myoa.watchtower.crawler.util;

import org.jsoup.nodes.Element;

/**
 * PpomppuArticleExtractor
 * @author Shin Woo-jin (lp12254@linecorp.com)
 * @since 2020-04-07
 *
 */
public class PpomppuArticleExtractor {

    public static String getTitle(Element element) {
        return element.getElementsByTag("td")
                      .get(5)
                      .getElementsByTag("font")
                      .text();
    }

    public static String getThumbnail(Element element) {
        return element.getElementsByTag("td")
                      .get(3)
                      .getElementsByTag("img")
                      .attr("src");
    }

    public static String getArticleId(Element element) {
        return element.getElementsByTag("td")
                      .get(0)
                      .text();
    }
}
