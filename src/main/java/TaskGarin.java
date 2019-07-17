import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;

public class TaskGarin {

    static String url = "https://speller.yandex.net/services/spellservice";
    static String reqForm = "/checkText?text=%s";
    static String req = "граза+дожть";

    public static void main(String[] args) {
        CloseableHttpResponse resp = null;
        try{
            CloseableHttpClient client = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url + String.format(reqForm, req));
            resp = client.execute(httpGet);
            String text = EntityUtils.toString(resp.getEntity());
            System.out.println(text+"\n");

            Document doc = Jsoup.parse(text);
            Elements elements = doc.getElementsByTag("error");
            for(Element wrongWord : elements){
                System.out.println("Слово с ошибками: ");
                System.out.println(wrongWord.getElementsByTag("word").text());
                System.out.println("Варианты: ");
                wrongWord.getElementsByTag("s").stream().forEach((word)-> System.out.println(word.text()));
            }

        }
        catch (IOException e){

        }
        finally {
            try {
                resp.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
