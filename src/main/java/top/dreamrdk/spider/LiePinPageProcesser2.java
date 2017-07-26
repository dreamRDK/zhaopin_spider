package top.dreamrdk.spider;

import top.dreamrdk.dao.LiePinDao;
import top.dreamrdk.domain.LiePinInfo;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;
// 爬取详情页电影信息
public class LiePinPageProcesser2 implements PageProcessor{
	
	private int a = 1;
	private Site site = Site.me().setRetryTimes(3).setDomain("liepin.com").setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.109 Safari/537.36");

	public Site getSite() {
		return site ;
	}

	public void process(Page page) {
		if(page.getUrl().toString().contains("curPage")) {
			for(int i = 1; i<41; i++) {
				page.addTargetRequest(page.getHtml().xpath("ul[@class='sojob-list']/li["+i+"]/div/div[1]/h3/a/@href")+"?"+page.getHtml().xpath("ul[@class='sojob-list']/li["+i+"]/div/div[1]/h3/a/@data-promid"));
			}
			if(a<101) {
				System.out.println("****************************************** "+a+" ****************************************************");
				page.addTargetRequest("https://www.liepin.com/zhaopin/?pubTime=&ckid=8e9788b616c8bc37&fromSearchBtn=2&compkind=&isAnalysis=&init=-1&searchType=1&dqs=010&industryType=&jobKind=&sortFlag=15&degradeFlag=0&industries=&salary=&compscale=&key=java&clean_condition=&headckid=86937daa89068876&curPage="+a);
				a++;
			}
//			page.putField("url", page.getHtml().xpath("ul[@class='sojob-list']/li[1]/div/div[1]/h3/a/@href"));
//			page.putField("param", page.getHtml().xpath("ul[@class='sojob-list']/li[1]/div/div[1]/h3/a/@data-promid"));
		} else {
			LiePinInfo lpi = new LiePinInfo();
			lpi.setJobName(page.getHtml().xpath("h1/text()").toString());
			lpi.setCompanyName(page.getHtml().xpath("h3/a/text()").toString());
			lpi.setSalary(page.getHtml().xpath("p[@class='job-item-title']/text()").toString());
			lpi.setArea(page.getHtml().xpath("p[@class='basic-infor']/span[1]/a/text()").toString());
			lpi.setDemand(page.getHtml().xpath("div[@class='job-qualifications']/allText()").toString());
			lpi.setJobDescription(page.getHtml().xpath("div[@class='content content-word']/allText()").toString());
			lpi.setCompanyintroduce(page.getHtml().xpath("div[@class='job-item main-message noborder']/div/allText()").toString());
			new LiePinDao().save(lpi);
//			page.putField("职位名", page.getHtml().xpath("h1/text()").toString());
//			page.putField("公司名", page.getHtml().xpath("h3/a/text()").toString());
//			page.putField("工资", page.getHtml().xpath("p[@class='job-item-title']/text()").toString());
//			page.putField("区域", page.getHtml().xpath("p[@class='basic-infor']/span[1]/a/text()").toString());
//			page.putField("简略要求", page.getHtml().xpath("div[@class='job-qualifications']/allText()").toString());
//			page.putField("职位描述", page.getHtml().xpath("div[@class='content content-word']/allText()").toString());
//			page.putField("企业介绍", page.getHtml().xpath("div[@class='job-item main-message noborder']/div/allText()").toString());
		}
		

		
		
	}
	
	public static void main(String[] args) {
		Spider.create(new LiePinPageProcesser2()).addUrl("https://www.liepin.com/zhaopin/?pubTime=&ckid=8e9788b616c8bc37&fromSearchBtn=2&compkind=&isAnalysis=&init=-1&searchType=1&dqs=010&industryType=&jobKind=&sortFlag=15&degradeFlag=0&industries=&salary=&compscale=&key=java&clean_condition=&headckid=86937daa89068876&curPage=0").addPipeline(new ConsolePipeline()).thread(5).run();
	}
}
