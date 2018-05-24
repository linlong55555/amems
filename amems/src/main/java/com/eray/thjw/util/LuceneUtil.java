package com.eray.thjw.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.eray.thjw.common.GlobalConstants;

import enu.LuceneEnum;

/**
 * @author liub
 * @description lucene工具类
 * @develop date 2017.01.06
 */
public class LuceneUtil {
	
	private static Analyzer analyzer = null;
	
	private static Version version = null;
	
	private static Map<Integer, DirectoryReader> directoryReaderMap = new HashMap<Integer, DirectoryReader>();
	
	private static Map<Integer, IndexWriter> indexWriterMap = new HashMap<Integer, IndexWriter>();
	
	private static Map<Integer, IndexSearcher> IndexSearcherMap = new HashMap<Integer, IndexSearcher>();
	
	private static Formatter formatter = null;
	
	/**
	 * @author liub
	 * @description 类加载初始化,关闭的时候销毁
	 */
	static {
		try {
			version = Version.LUCENE_45;
			analyzer = new StandardAnalyzer(version);
			formatter = new SimpleHTMLFormatter("<span style='color:red;'>", "</span>");//定制高亮标签
			// 获取当前的项目, 并且给项目注册关闭的钩子(事件)
			Runtime.getRuntime().addShutdownHook(new Thread() {
				public void run() {
					closeAllWriter();
				}
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * @author liub
	 * @description 关闭所有的IndexWriter
	 */
	public static void closeAllWriter(){
		try {
			for (Map.Entry<Integer, IndexWriter> entry : indexWriterMap.entrySet()) {
				entry.getValue().close();
			}
		} catch (IOException e) {
			throw new RuntimeException("关闭IndexWriter失败",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 调用此方法则说明要使用IndexSearcher, 如果没有则创建一个新的Search
	 * @param IndexSearcher
	 */
	public static IndexSearcher getIndexSearcher(LuceneEnum luceneEnum) {
		// 主要是为了提高性能
		if (IndexSearcherMap.get(luceneEnum.getId()) == null) {
			synchronized (LuceneUtil.class) {
				// 主要是为了防止重复创建
				if (IndexSearcherMap.get(luceneEnum.getId()) == null) {
					try {
						DirectoryReader directoryReader = DirectoryReader.open(getDirectory(luceneEnum.getName()));
						IndexSearcher indexSearcher = new IndexSearcher(directoryReader);
						directoryReaderMap.put(luceneEnum.getId(), directoryReader);
						IndexSearcherMap.put(luceneEnum.getId(), indexSearcher);
					} catch (Exception e) {
						return null;
					}
				}
			}
		}
		return IndexSearcherMap.get(luceneEnum.getId());
	}
	
	/**
	 * @author liub
	 * @description 获取IndexWriter,销毁以前的IndexSearch
	 * @param luceneEnum
	 */
	public static IndexWriter getIndexWriter(LuceneEnum luceneEnum) {
		// 主要是为了提高性能
		if (indexWriterMap.get(luceneEnum.getId()) == null) {
			synchronized (LuceneUtil.class) {
				// 主要是为了防止重复创建
				if (indexWriterMap.get(luceneEnum.getId()) == null) {
					try {
						IndexWriterConfig config = new IndexWriterConfig(version, analyzer);
						Directory dir = getDirectory(luceneEnum.getName());
						IndexWriter indexWriter = new IndexWriter(dir, config);
						indexWriterMap.put(luceneEnum.getId(), indexWriter);
					} catch (Exception e) {
						throw new RuntimeException("获取IndexWriter失败",e);
					}
				}
			}
		}
		if (IndexSearcherMap.get(luceneEnum.getId()) != null) {
			synchronized (LuceneUtil.class) {
				if (IndexSearcherMap.get(luceneEnum.getId()) != null) {
					try {
						DirectoryReader directoryReader = directoryReaderMap.get(luceneEnum.getId());
						directoryReader.close();
						directoryReaderMap.put(luceneEnum.getId(), null);
						IndexSearcherMap.put(luceneEnum.getId(), null);
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
			}
		}
		return indexWriterMap.get(luceneEnum.getId());
	}
	
	/**
	 * @author liub
	 * @description 采用IndexWriter把数据添加到索引库中
	 * @param document,luceneEnum
	 * @develop date 2017.01.06
	 */
	public static void addDocument(Document document,LuceneEnum luceneEnum){
		IndexWriter indexWriter = null;
		try {
			indexWriter = getIndexWriter(luceneEnum);
			indexWriter.addDocument(document);
			indexWriter.commit();
		} catch (Exception e) {
			if(indexWriter!=null){
				try {
					indexWriter.rollback();
				} catch (IOException e1) {
					throw new RuntimeException("indexWriter回滚失败!",e1);
				}
			}
			throw new RuntimeException("把数据添加到索引库失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 更新索引库数据
	 * @param document,luceneEnum
	 * @develop date 2017.01.06
	 */
	public static void updateDocument(Document document,LuceneEnum luceneEnum){
		IndexWriter indexWriter = null;
		try {
			indexWriter = getIndexWriter(luceneEnum);
			indexWriter.updateDocument(new Term("id", document.get("id")), document);
			indexWriter.commit();
		} catch (Exception e) {
			if(indexWriter!=null){
				try {
					indexWriter.rollback();
				} catch (IOException e1) {
					throw new RuntimeException("indexWriter回滚失败!",e1);
				}
			}
			throw new RuntimeException("更新索引库数据失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 删除索引库数据
	 * @param document
	 * @return
	 * @develop date 2017.01.06
	 */
	public static void deleteDocument(String id,LuceneEnum luceneEnum){
		IndexWriter indexWriter = null;
		try {
			indexWriter = getIndexWriter(luceneEnum);
			indexWriter.deleteDocuments(new Term("id", id));
			indexWriter.commit();
		} catch (Exception e) {
			if(indexWriter!=null){
				try {
					indexWriter.rollback();
				} catch (IOException e1) {
					throw new RuntimeException("回滚失败!",e1);
				}
			}
			throw new RuntimeException("删除索引库数据失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 根据关键字查询数据
	 * @param fields(字段名),keyword(关键字),currentPage(当前页),size(每页显示数）、sortType(排序)
	 * @return TopDocs
	 * @develop date2017.01.06
	 */
	public static TopDocs query(String[] fields,List<String> dprtList,Map<String, String> map,int currentPage,int size,boolean sequence,LuceneEnum luceneEnum) throws RuntimeException{
		Sort sort = null;
		TopDocs topDocs = null;
		IndexSearcher indexSearcher=null;
		BooleanQuery booleanQuery = null;
		try {
			booleanQuery = new BooleanQuery();
			indexSearcher = getIndexSearcher(luceneEnum);
			if(null == indexSearcher){
				return null;
			}
			booleanQuery.add(getKeyworkQuery(fields, map.get("keyword")), BooleanClause.Occur.MUST);
			booleanQuery.add(getCondition(map,dprtList), BooleanClause.Occur.MUST);
			sort = new Sort(new SortField(map.get("sortType"), SortField.Type.STRING,sequence));
			topDocs = indexSearcher.search(booleanQuery,null,currentPage * size,sort);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return topDocs;
	}
	
	/**
	 * @author liub
	 * @description 生成关键字分词查询条件Query
	 * @param fields(字段名),keyword(关键字)
	 * @return Query
	 * @develop date2017.01.09
	 */
	public static Query getKeyworkQuery(String[] fields,String keywork) throws RuntimeException{
		String[] values = null;
		Query query = null;
		BooleanClause.Occur[] flags = null;
		try {
			if(null == keywork || "".equals(keywork)){
				query = new MatchAllDocsQuery();
			}else{
				values = new String[fields.length];  
				flags = new BooleanClause.Occur[fields.length];
				keywork = QueryParser.escape(keywork);
				for(int i = 0 ; i < fields.length ; i++){
					values[i] = keywork;
					flags[i] = BooleanClause.Occur.SHOULD;
				}
				query = MultiFieldQueryParser.parse(version, values, fields, flags , analyzer); 
			}
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
		return query;
	}
	
	/**
	 * @author liub
	 * @description 生成关键字分词查询条件
	 * @param query(查询条件),size(高亮后的长度),text(内容)
	 * @return String
	 * @develop date2017.01.09
	 */
	public static String setHighlighter(Query query ,int size, String text){
		Scorer scorer = null;
		String fragment = null;
		try {
			scorer = new QueryScorer(query);
			Highlighter highlighter=new Highlighter(formatter,scorer);  
			highlighter.setTextFragmenter(new SimpleFragmenter(size));
			if(text == null){text = "";}
			fragment = highlighter.getBestFragment(analyzer, null, text);
			if(fragment == null){
				if(null != text && text.length() > size){
					fragment = text.substring(0 , size) + "...";
				}else{
					fragment = text;
				}
			}
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
		return fragment;
	}
	
	/**
	 * @author liub
	 * @description 调用此方法获取分词器
	 * @return Analyzer
	 */
	public static Analyzer getAnalyzer(){
		return analyzer;
	}
	
	/**
	 * @author liub
	 * @description 调用此方法获取版本
	 * @return Directory
	 */
	public static Version getVersion(){
		return version;
	}
	
	/**
	 * @author liub
	 * @description 根据文件夹获取全文检索存放的路径
	 * @param folder
	 * @return Directory
	 */
	public static Directory getDirectory(String folder){
		Directory directory = null;
		try {
			String rootPath = GlobalConstants.getString(GlobalConstants.ATT_ROOT_PATH_KEY);
			String relativePath = File.separator.concat(folder);
			String direcory = rootPath.concat(relativePath);
			directory = FSDirectory.open(new File(direcory));
		} catch (IOException e) {
			throw new RuntimeException("获取文件路径失败!",e);
		}
		return directory;
	}
	
	/**
	 * @author liub
	 * @description 获取查询条件BooleanQuery
	 * @param map,dprtList
	 * @return BooleanQuery
	 * @develop date2017.01.09
	 */
	private static BooleanQuery getCondition(Map<String, String> map,List<String> dprtList) throws RuntimeException{
		BooleanQuery booleanQuery = null;
		BooleanQuery publicBooleanQuery = null;
		BooleanQuery dprtBooleanQuery = null;
		BooleanQuery zdrBooleanQuery = null;
		try {
			booleanQuery = new BooleanQuery();
			if(null != dprtList && dprtList.size() > 0){
				for (String dprtId : dprtList) {
					publicBooleanQuery = new BooleanQuery();
					publicBooleanQuery.add(new TermQuery(new Term("kjfw", "1")), BooleanClause.Occur.MUST);
					publicBooleanQuery.add(new TermQuery(new Term("dprtcode", dprtId)), BooleanClause.Occur.MUST);
					booleanQuery.add(publicBooleanQuery, BooleanClause.Occur.SHOULD);
				}
			}
			if(null != map.get("dprtcode") && !"".equals(map.get("dprtcode"))){
				dprtBooleanQuery = new BooleanQuery();
				dprtBooleanQuery.add(new TermQuery(new Term("kjfw", "2")), BooleanClause.Occur.MUST);
				dprtBooleanQuery.add(new TermQuery(new Term("dprtcode", map.get("dprtcode"))), BooleanClause.Occur.MUST);
				booleanQuery.add(dprtBooleanQuery, BooleanClause.Occur.SHOULD);
			}
			if(null != map.get("zdrid") && !"".equals(map.get("zdrid"))){
				zdrBooleanQuery = new BooleanQuery();
				zdrBooleanQuery.add(new TermQuery(new Term("zdrid", map.get("zdrid"))), BooleanClause.Occur.MUST);
				zdrBooleanQuery.add(new TermQuery(new Term("kjfw", "3")), BooleanClause.Occur.MUST);
				booleanQuery.add(zdrBooleanQuery, BooleanClause.Occur.SHOULD);
			}
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
		return booleanQuery;
	}
	
}
