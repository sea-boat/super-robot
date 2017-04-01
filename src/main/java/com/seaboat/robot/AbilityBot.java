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
import com.seaboat.robot.ability.MnistAbility;
import com.seaboat.robot.ability.ServerStatusAbility;
import com.seaboat.robot.ability.ServiceStatusAbility;
import com.seaboat.robot.ability.index.IndexUtil;

/**
 * 
 * <pre><b>AbilityBot has all kinds of abilities. According to the input,AbilityBot will match the most similar ability. </b></pre>
 * @author 
 * <pre>seaboat</pre>
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * @version 0.1
 */
public class AbilityBot {

	public Map<String, Ability> abilityMap = new HashMap<String, Ability>();

	public AbilityBot() {
		abilityMap.put("DateAbility", new DateAbility());
		abilityMap.put("ServerStatusAbility", new ServerStatusAbility());
		abilityMap.put("MnistAbility", new MnistAbility());
		abilityMap.put("ServiceStatusAbility", new ServiceStatusAbility());
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
			String[] pattern2 = { "帮我查下服务器状态","帮我查查服务器状态", "查查服务器状态", "告诉我服务器状态" };
			for (String pattern : pattern2) {
				Document doc = new Document();
				doc.add(new Field("pattern", pattern, Field.Store.YES,
						Field.Index.ANALYZED));
				doc.add(new Field("template", "ServerStatusAbility",
						Field.Store.YES, Field.Index.ANALYZED));
				IndexUtil.getIndexWriter().addDocument(doc);
			}
			String[] pattern3 = { "SUPER-ROBOT-MNIST" };
			for (String pattern : pattern3) {
				Document doc = new Document();
				doc.add(new Field("pattern", pattern, Field.Store.YES,
						Field.Index.ANALYZED));
				doc.add(new Field("template", "MnistAbility",
						Field.Store.YES, Field.Index.ANALYZED));
				doc.add(new Field("template", "MnistAbility", Field.Store.YES,
						Field.Index.ANALYZED));
				IndexUtil.getIndexWriter().addDocument(doc);
			}
			String[] pattern4 = { "帮我查下支付服务状态","帮我查查支付服务状态", "查查支付服务状态", "告诉我支付服务状态" };
			for (String pattern : pattern4) {
				Document doc = new Document();
				doc.add(new Field("pattern", pattern, Field.Store.YES,
						Field.Index.ANALYZED));
				doc.add(new Field("template", "ServiceStatusAbility",
						Field.Store.YES, Field.Index.ANALYZED));
				IndexUtil.getIndexWriter().addDocument(doc);
			}
			IndexUtil.getIndexWriter().commit();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Ability searchAbility(String input) {
		List<Document> docs = new ArrayList<Document>();
		try {
			IndexSearcher isearcher = IndexUtil.getIndexSearcher();
			isearcher.setSimilarity(new IKSimilarity());
			Query query = IKQueryParser.parse("pattern", input);
			TopDocs topDocs = isearcher.search(query, 2);
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
			return abilityMap.get(ability);
		}
		return null;
	}
}
