package per.search.persistence;

import java.util.Collection;

import per.search.model.History;

public interface SynchronizeHistoryDAO {

	boolean put(String key, History history);

	History get(String key);

	Collection<History> getAll();
}