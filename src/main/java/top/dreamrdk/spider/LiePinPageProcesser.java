package top.dreamrdk.spider;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;
// 爬取详情页电影信息
public class LiePinPageProcesser implements PageProcessor{
	
	private Site site = Site.me().setRetryTimes(3).setDomain("liepin.com").setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.109 Safari/537.36");

	public Site getSite() {
		return site ;
	}

	public void process(Page page) {
		
		page.putField("职位名", page.getHtml().xpath("h1/text()").toString());
		page.putField("公司名", page.getHtml().xpath("h3/a/text()").toString());
		page.putField("工资", page.getHtml().xpath("p[@class='job-item-title']/text()").toString());
		page.putField("区域", page.getHtml().xpath("p[@class='basic-infor']/span[1]/a/text()").toString());
		page.putField("简略要求", page.getHtml().xpath("div[@class='job-qualifications']/allText()").toString());
		page.putField("职位描述", page.getHtml().xpath("div[@class='content content-word']/allText()").toString());
		page.putField("企业介绍", page.getHtml().xpath("div[@class='job-item main-message noborder']/div/allText()").toString());
		
		
	}
	
	public static void main(String[] args) {
		Spider.create(new LiePinPageProcesser()).addUrl("https://www.liepin.com/job/198551411.shtml?imscid=R000000075&ckid=d706cc47eb888527&headckid=d706cc47eb888527&pageNo=0&pageIdx3&totalIdx=3&sup=1").addPipeline(new ConsolePipeline()).run();
	}
}
