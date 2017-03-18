package com.seaboat.robot.ability.index;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class IndexUtil {

	protected static final int MAX = 10;

	private static IndexReader indexReader;

	private static IndexWriter indexWriter;

	private static IndexSearcher indexSearcher;

	private static Directory directory;

	protected static Map<String, Object> dataBase = new HashMap<String, Object>();

	static {

		try {
			Properties properties = new Properties();
			properties.load(IndexUtil.class.getClassLoader()
					.getResourceAsStream("lucence.properties"));

			directory = FSDirectory.open(new File((String) properties
					.get("path")));

			Runtime.getRuntime().addShutdownHook(new Thread() {
				public void run() {
					try {
						if (null != indexWriter) {
							indexWriter.close();
						}

						if (null != indexReader) {
							indexReader.close();
						}
						if (null != directory) {
							directory.close();
						}
						if (null != dataBase) {
							dataBase.clear();
						}
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			});

		} catch (Exception e) {
		}
	}

	/**
	 * 获取reader
	 *
	 * @return
	 * @throws IOException
	 */
	public static IndexReader getIndexReader() throws IOException {
		if (null != indexReader)
			return indexReader;
		return IndexReader.open(directory);
	}

	/**
	 *
	 * @return
	 * @throws IOException
	 */
	public static IndexWriter getIndexWriter() throws IOException {

		if (null != indexWriter) {
			return indexWriter;
		} else {
			synchronized (IndexUtil.class) {
				IndexWriterConfig config = new IndexWriterConfig(
						Version.LUCENE_35, new IKAnalyzer());
				config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
				indexWriter = new IndexWriter(directory, config);
			}
			return indexWriter;
		}
	}

	public static IndexSearcher getIndexSearcher() throws IOException {
		if (null != indexSearcher) {
			return indexSearcher;
		} else {
			synchronized (IndexUtil.class) {
				indexSearcher = new IndexSearcher(getIndexReader());
			}
			return indexSearcher;
		}
	}

}
