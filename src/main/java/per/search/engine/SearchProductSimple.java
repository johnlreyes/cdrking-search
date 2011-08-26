package per.search.engine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import per.search.model.Product;
import per.search.persistence.ProductsDAO;

public class SearchProductSimple implements SearchProduct {

	@Override
	public List<Product> search(String input) {
		List<Product> productHits = new ArrayList<Product>();
		if (input != null && input.trim().length()>0) {
			String[] arr = input.split("\\s+");
			StringBuilder targetKey = new StringBuilder();
			for (String a : arr) {
				targetKey.append(a+"* ");
			}
			try {
				Directory indexDir = FSDirectory.open(new File("lucene_index"));
				IndexSearcher searcher = new IndexSearcher(indexDir);

				StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_33);

				QueryParser queryParser = new MultiFieldQueryParser(Version.LUCENE_33, new String[] { "name", "code", "category" }, analyzer);
				Query query = queryParser.parse(targetKey.toString());

				Sort sort = new Sort(new SortField("sid", SortField.STRING, true));
				TopDocs hits = searcher.search(query, 10, sort);

				System.out.println("Found " + hits.totalHits + " hits.");
				for (ScoreDoc scoreDoc : hits.scoreDocs) {
					Document d = searcher.doc(scoreDoc.doc);
					String sid = d.get("sid");
					String data = ProductsDAO.get(sid);
					Product product = ProductsDAO.parseProduct(data);
					productHits.add(product);
					System.out.println(product.getSid() + " : " + product.getName() + " : " + product.getCode() + " : " + product.getPrice() + " : "
							+ product.getStatus());
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return productHits;
	}
}
