package com.uop.cadueus;

import android.os.Bundle;
import android.app.Activity;
import android.text.Html;
import android.view.Menu;
import android.widget.TextView;

public class InformationActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        
        TextView tv = (TextView)this.findViewById(R.id.informationText);
        tv.setText(Html.fromHtml(getString(R.string.information_html)));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_information, menu);
        return true;
    }
}
