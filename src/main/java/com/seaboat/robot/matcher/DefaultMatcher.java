package com.seaboat.robot.matcher;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.seaboat.robot.util.DataReader;
import com.seaboat.text.analyzer.util.Segment;
import com.seaboat.text.analyzer.word2vec.Word2Vec;

/**
 * 
 * @author seaboat
 * @date 2017-12-04
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>the default matcher which is match by google word2vec model.</p>
 */
public class DefaultMatcher implements Matcher {

  public Map<String, List<QA>> qasMap = new HashMap<String, List<QA>>();

  public Word2Vec vec;

  public String defaultWord2vecPath = "data/Google_word2vec_zhwiki1710_300d.bin";

  // public Map<String, List<QA>> segMap = new HashMap<String, List<QA>>();

  public DefaultMatcher(String word2vecPath) {
    vec = new Word2Vec();
    try {
      if (word2vecPath != null)
        vec.loadGoogleModel(word2vecPath);
      else
        vec.loadGoogleModel(defaultWord2vecPath);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String match(String intent, String question) {
    if (!qasMap.containsKey(intent)) return "#";
    List<QA> qas = qasMap.get(intent);
    List<String> wordList1 = Segment.getWords(question);
    QA finalQA = null;
    float score = 0.8f;
    for (QA qa : qas) {
      List<String> wordList2 = Segment.getWords(qa.getQuestion());
      float score1 = vec.sentenceSimilarity(wordList1, wordList2);
      if (score1 > score) {
        System.out.println(score1);
        score = score1;
        finalQA = qa;
      }
    }
    if (finalQA == null) return "#";
    return finalQA.getAnswer();
  }

  public void initMatcher(List<String> files) {
    for (String file : files) {
      String json = DataReader.readContent(file);
      String intent = file.substring(file.lastIndexOf("/") + 1, file.lastIndexOf("."));
      if (!qasMap.containsKey(intent)) qasMap.put(intent, new LinkedList<QA>());
      List<QA> qas = JSON.parseArray(json, QA.class);
      qasMap.put(intent, qas);
    }
  }

}
