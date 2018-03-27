package io.rollout.example;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * POJO for an XKCD comic
 */
@Data
@NoArgsConstructor
public class XKCDComic {

    private String month;
    private int num;
    private String year;
    private String news;
    private String safe_title;
    private String transcript;
    private String alt;
    private String img;
    private String title;
    private String day;
    private String link;

}


