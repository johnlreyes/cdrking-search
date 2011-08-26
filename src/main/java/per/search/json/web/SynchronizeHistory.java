package per.search.json.web;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import per.search.json.util.JSON_UTIL;
import per.search.model.History;
import per.search.persistence.SynchronizeHistoryDAO;

public class SynchronizeHistory extends AbstractJsonServlet {

	private static final long serialVersionUID = 7580346993611842977L;

	public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {

		Collection<History> list = SynchronizeHistoryDAO.getAll();
		String output = JSON_UTIL.convert(list);
		jsonOutput(response, output);
	}
}