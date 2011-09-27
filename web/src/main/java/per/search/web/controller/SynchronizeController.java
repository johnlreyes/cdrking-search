package per.search.web.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import per.search.persistence.SynchronizeHistoryDAO;
import per.search.persistence.model.History;
import per.search.thread.CdrKingSyncThread;

@Controller
@RequestMapping(value = "/synchronize")
public class SynchronizeController {

	@Autowired
	private SynchronizeHistoryDAO synchronizeHistoryDAO;
	
	@Autowired
	private CdrKingSyncThread cdrKingSyncThread;

	@RequestMapping(value = "external")
	@ResponseBody
	public Map<String, String> external() {
		if (!CdrKingSyncThread.invoked) {
			ScheduledExecutorService oneTime = Executors.newScheduledThreadPool(1);
			oneTime.schedule(cdrKingSyncThread, 1, TimeUnit.SECONDS);
		}

		Map<String, String> statusMap = new HashMap<String, String>();
		while (CdrKingSyncThread.status == "standby") {
		}
		statusMap.put("status", CdrKingSyncThread.status);
		return statusMap;
	}

	@RequestMapping(value = "history")
	@ResponseBody
	public Collection<History> history() {
		return synchronizeHistoryDAO.getAll();
	}

	@RequestMapping(value = "status")
	@ResponseBody
	public Map<String, String> status() {
		Map<String, String> statusMap = new HashMap<String, String>();
		statusMap.put("status", CdrKingSyncThread.invoked ? "ongoing" : "standby");
		return statusMap;
	}

	@RequestMapping(value = "stop")
	@ResponseBody
	public void stop() {
		CdrKingSyncThread.stopOngoing = true;
	}
}