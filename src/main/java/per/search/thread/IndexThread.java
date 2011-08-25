package per.search.thread;

import java.io.File;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import per.search.model.Product;
import per.search.persistence.ProductsDAO;

public class IndexThread  implements Runnable {

	@Override
	public void run() {
		IndexWriter w = null;
		synchronized (this) {
			long start = System.currentTimeMillis();
			
			try {
				File file = new File("lucene_index");
				if (!(file.exists() && file.isDirectory())) {
					file.mkdir();
				}
				
				Directory index = FSDirectory.getDirectory("lucene_index");
		        StandardAnalyzer analyzer = new StandardAnalyzer();
		        w = new IndexWriter(index, analyzer, true, IndexWriter.MaxFieldLength.UNLIMITED);
				
		        int count = 0;
		        int id = 14;
		        String data = ProductsDAO.get(""+id);
		        
		        while (data != null || (data==null&&count < 10)) {
		        	if (data != null) {
		        		System.out.println("indexing " + id);
		        		Product product = ProductsDAO.parseProduct(data);
			            Document doc = new Document();
			            
			            String name = product.getName();
			            System.out.println("name=" + name);
			            String code = product.getCode();
			            System.out.println("code=" + code);
			            String category = product.getCategory();
			            System.out.println("category=" + category);
						
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
			            count = 0;
		        	} else {
		        	    count++;
		            }
		        	id++;
		        	data = ProductsDAO.get(""+id);
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

		    System.out.println("took " + (end - start) + " milliseconds");
		}
	}
	
}
