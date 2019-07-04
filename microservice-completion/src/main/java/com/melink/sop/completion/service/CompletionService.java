package com.melink.sop.completion.service;

import java.util.List;

/**
 * @ Author     ：liyang.
 * @ Date       ：Created in 15:38 2018/12/20
 * @ Description：
 * @ Modified By：
 */
public interface CompletionService {
    List<String> completion(String word);
}
