package ifnoticias.com.br.ifnoticias.View;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.io.IOException;

import ifnoticias.com.br.ifnoticias.R;

public class visualizaNoticia extends Activity {

    TextView noticiaTitulo;
    TextView noticiaConteudo;
    String titulo = "";
    String conteudo = "";
    String url = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualiza_noticia);

        noticiaTitulo = (TextView) findViewById(R.id.noticiaTitulo);
        noticiaConteudo = (TextView) findViewById(R.id.noticiaConteudo);

        Bundle extra = getIntent().getExtras();

        if (extra != null) {
            titulo = extra.getString("titulo");
            conteudo = extra.getString("conteudo");
            url = extra.getString("url");
            noticiaConteudo.setText(conteudo);
            noticiaTitulo.setText(titulo);

            new ParsePage().execute();
        }
    }
        class ParsePage extends AsyncTask<String,Void,String> {

            Elements elements = new Elements();

            @Override
            protected String doInBackground(String... params) {
                Document doc;
                String connectUrl = "http://www.ifg.edu.br" + url;
                try{

                    doc = Jsoup.connect(connectUrl).get();
                    //Capturando principais noticias
                    elements = doc.getElementsByClass("item-page");



                }catch (IOException e){
                    e.printStackTrace();
                }
                return "rodou";
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                String connectUrl = "http://ifg.edu.br" + url;
                Log.d("Article content",elements.text().toString());
                conteudo = "";
                titulo = "";
                for(Element e : elements){
                    Elements h1 = e.getElementsByTag("h1");
                    Log.d("conteudospan ", String.valueOf(h1.size()));
                    titulo = h1.get(0).text().toString();
                    Log.d("titulo ",titulo);
                    Elements p = e.getElementsByTag("p");
                    for(int i = 1 ; i < (p.size() - 1) ; i++ ) {
                        conteudo += " " + p.get(i).text().toString();
                        Log.d("Conteudo ", conteudo);
                    }
                }
                noticiaConteudo.setText(conteudo);
                noticiaTitulo.setText(titulo);
                Log.d("POST EXECUTE: ", "onPostExecute: rodou no noticia");
            }


    }
}
