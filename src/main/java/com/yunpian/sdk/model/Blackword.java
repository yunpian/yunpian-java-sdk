/**
 * 
 */
package com.yunpian.sdk.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 屏蔽词
 * 
 * @author dzh
 * @date Dec 5, 2016 4:41:01 PM
 * @since 1.2.0
 */
public class Blackword {

    /**
     * 
     */
    private String black_word;

    public String getBlack_word() {
        return black_word;
    }

    public void setBlack_word(String black_word) {
        this.black_word = black_word;
    }

    public List<String> toList() {
        return black_word == null || black_word.equals("") ? Collections.<String>emptyList()
                : Arrays.asList(black_word.split(","));
    }

}
