package com.engineering.myoa.watchtower.crawler.scheduler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * HtmlData
 * @author Shin Woo-jin (lp12254@linecorp.com)
 * @since 2020-04-07
 *
 */
public class HtmlData {

    public static String getData() throws IOException {
        return new String(Files.readAllBytes(Paths.get(Paths.get("").toAbsolutePath().toString(),
                                                       "/src/test/resources/html.txt")));
    }
}
