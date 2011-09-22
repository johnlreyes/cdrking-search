package per.search.action;

import java.util.Map;

public interface ActionInterface<T> {

	T process(Map paramMap);
}
