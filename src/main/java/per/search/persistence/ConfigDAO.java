package per.search.persistence;

public interface ConfigDAO {

	boolean put(String key, String value);

	String get(String key);
}