package per.search.persistence.impl;

import per.search.persistence.ConfigDAO;

public class ConfigDAOImpl extends BaseDAO implements ConfigDAO {

	@Override
	public boolean put(String key, String value) {
		return super.put(key, value);
	}

	@Override
	public String get(String key) {
		return (String) super.get(key);
	}
}