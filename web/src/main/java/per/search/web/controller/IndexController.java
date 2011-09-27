package per.search.web.controller;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import per.search.thread.IndexThread;

@Controller
@RequestMapping(value = "/index")
public class IndexController {

	@Autowired
	private IndexThread indexThread;
	
	@RequestMapping(value = "all")
	@ResponseBody
	public void all(String key) {
		ScheduledExecutorService oneTime = Executors.newScheduledThreadPool(1);
		oneTime.schedule(indexThread, 1, TimeUnit.SECONDS);
	}
}