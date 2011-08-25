package per.search.web.json;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.log4j.Log4j;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.json.JSONObject;

@Log4j
public class SearchKey extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {
		log.info("SearchKey invoked");

		String key = request.getParameter("key");
		System.out.println("key="+key);
		if (key != null) {
			try {
				Directory index = FSDirectory.getDirectory("lucene_index");
				StandardAnalyzer analyzer = new StandardAnalyzer();

				Query q = new MultiFieldQueryParser(new String[] { "name",
						"code", "category" }, analyzer).parse(key + "*");

				// searching ...
				int hitsPerPage = 10;
				IndexSearcher searcher = new IndexSearcher(index);
				TopDocCollector collector = new TopDocCollector(hitsPerPage);
				searcher.search(q, collector);
				ScoreDoc[] hits = collector.topDocs().scoreDocs;

				// output results
				System.out.println("Found " + hits.length + " hits.");
				for (int i = 0; i < hits.length; ++i) {
					int docId = hits[i].doc;
					Document d = searcher.doc(docId);
					System.out.println((i + 1) + ". " + d.get("name") + ": "
							+ d.get("code") + ": " + d.get("category"));
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		JSONObject jsonObject = new JSONObject();

		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(jsonObject);
		out.flush();
	}
}