package com.engineering.myoa.watchtower.crawler.service.ppomppu;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.engineering.myoa.watchtower.crawler.doamin.ppomppu.OverseaArticle;
import com.engineering.myoa.watchtower.crawler.doamin.ppomppu.OverseaCategory;
import com.engineering.myoa.watchtower.crawler.properties.ApiProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * OverseaArticleCrawler
 * @author Shin Woo-jin (lp12254@linecorp.com)
 * @since 2020-04-07
 *
 */
@Slf4j
@Component
public class OverseaArticleCrawler {

    private static final int REASONABLE_CHILD_NODE_SIZE = 10;
    private static final String HEADER_ROW_JUDGEMENT = "글쓴이";

    private final ApiProperties apiProperties;
    private final RestTemplate restTemplate;
    private final OverseaArticleQueryAdaptor overseaArticleQueryAdaptor;

    public OverseaArticleCrawler(
            ApiProperties apiProperties,
            @Qualifier("verboseRestTemplate") RestTemplate restTemplate,
            OverseaArticleQueryAdaptor overseaArticleQueryAdaptor) {
        this.apiProperties = apiProperties;
        this.restTemplate = restTemplate;
        this.overseaArticleQueryAdaptor = overseaArticleQueryAdaptor;
    }

    @Async("asyncThreadPoolTaskExecutor")
    @Transactional(propagation = Propagation.REQUIRED)
    public void execute(OverseaCategory category) {
        CrawlingResult crawl = this.crawl(category);
        OverseaArticle latestArticle = overseaArticleQueryAdaptor.getLatestArticleByCategory(category)
                                                                 .orElseGet(OverseaArticle::ofNull);

        List<OverseaArticle> overseaArticles = this.filterNotPersisted(this.extractArticle(crawl),
                                                                       latestArticle);

        log.info("[OverseaArticleCrawler.execute] crawled count : {}", overseaArticles.size());
        overseaArticleQueryAdaptor.save(overseaArticles);
    }

    public CrawlingResult crawl(OverseaCategory category) {
        String url = String.format(apiProperties.findByApiName(category.getApiUnitName()).getApiPath(),
                                   category.getPage());
        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        return CrawlingResult.of(category, exchange.getBody());
    }

    public List<OverseaArticle> extractArticle(CrawlingResult crawlingResult) {
        Document document = Jsoup.parse(crawlingResult.getHtml());
        List<Element> articles =
                document.getElementById("revolution_main_table").getElementsByTag("tr").stream()
                        .filter(e -> isNotHeaderRow(e) && e.childNodeSize() > REASONABLE_CHILD_NODE_SIZE)
                        .collect(Collectors.toList());

        return articles.stream()
                       .map(e -> OverseaArticle.of(e, crawlingResult.getCategory()))
                       .filter(Objects::nonNull)
                       .sorted(Comparator.comparing(OverseaArticle::getArticleId))
                       .collect(Collectors.toList());
    }

    public List<OverseaArticle> filterNotPersisted(List<OverseaArticle> overseaArticles,
                                                   OverseaArticle latestArticle) {
        return overseaArticles.stream()
                              .filter(e -> e.getArticleId().compareTo(latestArticle.getArticleId()) > 0)
                              .collect(Collectors.toList());
    }

    private boolean isNotHeaderRow(Element tr) {
        return tr.text().indexOf(HEADER_ROW_JUDGEMENT) < 0;
    }

    @Getter
    @AllArgsConstructor
    public static class CrawlingResult {
        private OverseaCategory category;
        private String html;

        public static CrawlingResult of(OverseaCategory category, String html) {
            return new CrawlingResult(category, html.replaceAll("&amp;", "&"));
        }
    }
}
