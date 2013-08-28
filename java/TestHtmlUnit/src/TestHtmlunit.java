import java.io.IOException;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class TestHtmlunit {

	
	public static void main(String[] args) {
		BrowserVersion browserVersion = BrowserVersion.FIREFOX_17;
		final WebClient webClient = new WebClient(browserVersion); 
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
	    try {
			final HtmlPage page = webClient.getPage("http://item.taobao.com/item.htm?spm=a2106.m895.1000384.19.y61SBs&id=18057942582&scm=1029.newlist-0.1.50040965&ppath=&sku=");
			final HtmlDivision div =page.getHtmlElementById("J_DivItemDesc");
			System.out.println(div.asXml());
	    } catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  

	}

}
