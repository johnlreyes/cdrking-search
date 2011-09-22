package per.search.thread;

import java.io.File;

import lombok.extern.log4j.Log4j;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import per.search.model.Product;
import per.search.persistence.ProductsDAO;

@Log4j
@Component
public class IndexThread  implements Runnable {

	@Autowired
	private ProductsDAO productsDAO;
	
	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		IndexWriter w = null;
		synchronized (this) {
			long start = System.currentTimeMillis();
			int processed = 0;
			
			try {
				String index_folder = "lucene_index";
				Directory indexDir = FSDirectory.open(new File(index_folder));
				Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_33);
		        w = new IndexWriter(indexDir, analyzer, true, IndexWriter.MaxFieldLength.UNLIMITED);
				
		        int count = 0;
		        int id = 14;
		        Product product = productsDAO.get(""+id);
		        
		        while (product != null || (product==null&&count < 10)) {
		        	if (product != null) {
		        		Document doc = new Document();
			            
			            String name = product.getName();
			            String code = product.getCode();
			            String category = product.getCategory();
			            
			            log.info("sid=" + product.getSid());
			            log.info("   name=" + name);
			            log.info("   code=" + code);
			            log.info("   category=" + category);
			            
			            doc.add(new Field("sid", ""+product.getSid(), Field.Store.YES, Field.Index.NOT_ANALYZED));
			            if (name != null && name.trim().length() > 0) {
			            	doc.add(new Field("name"	, name		, Field.Store.YES, Field.Index.ANALYZED));
			            }
			            if (code != null && code.trim().length() > 0) {
			            	doc.add(new Field("code"	, code		, Field.Store.YES, Field.Index.ANALYZED));
			            }
			            if (category != null && category.trim().length() > 0) {
			            	doc.add(new Field("category", category	, Field.Store.YES, Field.Index.ANALYZED));
			            }
			            
			            w.addDocument(doc);
			            processed ++;
			            count = 0;
		        	} else {
		        	    count++;
		            }
		        	id++;
		        	product = productsDAO.get(""+id);
		        }
		    } catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				try {
					w.close();
				} catch (Exception dontCare) {					
				}
			}
	        
		    long end = System.currentTimeMillis();

		    log.info("  processed "+processed+"  took " + (end - start) + " milliseconds");
		}
	}
	
}
