package per.search.persistence.impl.file;

import java.util.ArrayList;
import java.util.Collection;

import lombok.extern.log4j.Log4j;
import per.search.model.History;
import per.search.persistence.SynchronizeHistoryDAO;

@Log4j
public class SynchronizeHistoryDAOImpl extends BaseDAO implements SynchronizeHistoryDAO {

	@Override
	public boolean put(String key, History history) {
		return super.put(key, history);
	}

	@Override
	public History get(String key) {
		return (History) super.get(key);
	}

	@Override
	public Collection<History> getAll() {
		log.info("getAll");
		Collection<History> returnValue = new ArrayList<History>();
		int index = 1;
		History history = get("" + index);
		log.info("index=" + index +"  history=" + history);
		while (history != null) {
			if (history != null) {
				returnValue.add(history);
			}
			index++;
			history = get("" + index);
			log.info("index=" + index + "  history=" + history);
		}
		return returnValue;
	}
}