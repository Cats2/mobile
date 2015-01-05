package visite.visite;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by utilisateur on 16/12/2014.
 */
public class UploadService extends IntentService {

    public UploadService(String name)
    {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String datastring = intent.getDataString();
    }
}
