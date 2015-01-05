package visite.visite;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by utilisateur on 09/12/2014.
 */
public class Liste_Site extends ListActivity {

    ListView lv;
    AdaptateurListe adaptateur;
    ArrayList<String> listItems=new ArrayList<String>();
    String categ= "";
    String region="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_site);

        Bundle i = getIntent().getExtras();
        if (i != null) {
            categ= i.getString("categ");
            region= i.getString("region");
        }
        String url = "http://192.168.1.13:3000/sites.json?categorie=" + categ+"&region_nom=" + region;
        new DLTask().execute(url);
       //values = new String[] {"Device", "Musee Granet"};
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);
        adaptateur = new AdaptateurListe(this, listItems);
        this.setListAdapter(adaptateur);

        lv = getListView();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView <?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                String  itemValue    = (String) lv.getItemAtPosition(position);

                Intent intent = new Intent(Liste_Site.this, Site.class);
                intent.putExtra("nom", itemValue);
                startActivity(intent);

            }

        });
    }

    public class DLTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... urls) {
            try {
                DLUrl dlurl = new DLUrl();
                return dlurl.downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page";
            }
        }

        @Override
        protected void onPostExecute(String result)
        {

            JSONArray arr = null;
            try {
                arr = new JSONArray(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < arr.length(); i++)
            {
                try {
                    String nom = arr.getJSONObject(i).getString("nom");
                    listItems.add(nom);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            adaptateur.notifyDataSetChanged();

        }
    }
}
