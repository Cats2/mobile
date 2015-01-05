package visite.visite;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class Region extends Activity {

    ListView listview = null;
    ArrayAdapter<String> adapter;
    ArrayList<String> listItems=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.region);

        listview = (ListView) findViewById(R.id.listView);

        new DLTask().execute("http://192.168.1.13:3000/regions.json");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,android.R.id.text1, listItems);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView <?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                String  itemValue    = (String) listview.getItemAtPosition(position);
                if (position == 0)
                {
                    Intent intent = new Intent(Region.this, Categorie.class);
                    intent.putExtra("region", itemValue);
                    startActivity(intent);
                }


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
            System.out.println(result);
            JSONArray arr = null;
            try {
                arr = new JSONArray(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println(arr);
            for (int i = 0; i < arr.length(); i++)
            {
                try {
                    String nom = arr.getJSONObject(i).getString("nom");
                    listItems.add(nom);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            adapter.notifyDataSetChanged();

        }
    }
}
