package visite.visite;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;


public class MainActivity extends Activity {

    private Button bregion = null;
    private Button bgeo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bregion = (Button) findViewById(R.id.region);
        bgeo = (Button) findViewById(R.id.geo);

        bregion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Le premier paramètre est le nom de l'activité actuelle
                // Le second est le nom de l'activité de destination
                new DLTask().execute("http://192.168.1.13:3000");
                Intent intent = new Intent(MainActivity.this, Region.class);
                // On rajoute un extra
                //intent.putExtra(AGE, 31);
                // Puis on lance l'intent !
                startActivity(intent);
            }
        });

        bgeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Le premier paramètre est le nom de l'activité actuelle
                // Le second est le nom de l'activité de destination
                new DLTask().execute("http://192.168.1.13:3000");
                Intent intent = new Intent(MainActivity.this, Localisation.class);
                // On rajoute un extra
                //intent.putExtra(AGE, 31);
                // Puis on lance l'intent !
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

    }
}
