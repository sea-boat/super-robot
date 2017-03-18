package com.seaboat.robot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.wltea.analyzer.lucene.IKQueryParser;
import org.wltea.analyzer.lucene.IKSimilarity;

import com.seaboat.robot.ability.Ability;
import com.seaboat.robot.ability.DateAbility;
import com.seaboat.robot.ability.index.IndexUtil;

public class AbilityBot {

	public Map<String, Ability> abilityMap = new HashMap<String, Ability>();

	public AbilityBot() {
		abilityMap.put("DateAbility", new DateAbility());
		pushToLucene();
	}

	public void pushToLucene() {
		try {
			String[] patterns = { "帮我查查时间", "查时间", "告诉我几点了", "几点了" };
			for (String pattern : patterns) {
				Document doc = new Document();
				doc.add(new Field("pattern", pattern, Field.Store.YES,
						Field.Index.ANALYZED));
				doc.add(new Field("template", "DateAbility", Field.Store.YES,
						Field.Index.ANALYZED));
				IndexUtil.getIndexWriter().addDocument(doc);
			}
			IndexUtil.getIndexWriter().commit();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String searchAbility(String input) {
		List<Document> docs = new ArrayList<Document>();
		try {
			IndexSearcher isearcher = IndexUtil.getIndexSearcher();
			isearcher.setSimilarity(new IKSimilarity());
			Query query = IKQueryParser.parse("pattern", input);
			TopDocs topDocs = isearcher.search(query, 5);
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			for (int i = 0; i < scoreDocs.length; i++) {
				Document targetDoc = isearcher.doc(scoreDocs[i].doc);
				docs.add(targetDoc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (docs.size() >= 1) {
			String ability = docs.get(0).get("template");
			return abilityMap.get(ability).process();
		}
		return "不好意思，不懂你的意思，可以让我的主人先教我！";

	}
}
