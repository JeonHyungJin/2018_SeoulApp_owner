package com.example.qpdjg.a2018_seoulapp_owner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class NewGallery extends AppCompatActivity {

    Spinner spinner;
    String[] Gallery_locations_list;
    EditText Gallery_name;
    EditText Gallery_explain;
    EditText Owner_explain;
    EditText Owner_insta;
    EditText Gallery_location;
    EditText Gallery_time;
    EditText Gallery_fee;
    Button submit_button;
    String[] G_location_from_list;
    String G_name;
    String G_explain;
    String O_explain;
    String O_insta;
    String G_location;
    String G_time;
    String G_fee;

    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mReference = mDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_gallery);


        Gallery_name = (EditText)findViewById(R.id.Gallery_name);
        Gallery_explain= (EditText)findViewById(R.id.Gallery_explain);
        Owner_explain= (EditText)findViewById(R.id.Owner_explain);
        Owner_insta= (EditText)findViewById(R.id.Owner_insta);
        Gallery_location= (EditText)findViewById(R.id.Gallery_location);
        Gallery_time= (EditText)findViewById(R.id.Gallery_time);
        Gallery_fee= (EditText)findViewById(R.id.Gallery_fee);
        submit_button = (Button)findViewById(R.id.submit_button);


        G_location_from_list = new String[1];

        Gallery_locations_list = new String[]{"강서구", "마포구", "영등포구", "양천구", "구로구", "금천구", "관악구", "동작구", "용산구", "서초구", "강남구", "송파구", "강동구", "광진구", "성동구", "중구", "용산구", "서대문구", "은평구", "종로구", "성북수", "동대문구", "중랑구", "강북구", "노원구", "도봉구"};

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.locations,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                G_location_from_list[0] = (String) Gallery_locations_list[position];
                //Toast.makeText(getApplicationContext(), Gallery_locations_list[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

    }

    public void submit_new_Gallery(View view) {
        G_name = Gallery_name.getText().toString();
        G_explain = Gallery_explain.getText().toString();
        O_explain = Owner_explain.getText().toString();
        O_insta = Owner_insta.getText().toString();
        G_location = Gallery_location.getText().toString();
        G_time = Gallery_time.getText().toString();
        G_fee = Gallery_fee.getText().toString();

        mReference = mDatabase.getReference("Gallerys/"+G_location_from_list[0]);
        Gallery_Data gallery_data = new Gallery_Data();
        gallery_data.Gallery_name = G_name;
        gallery_data.Gallery_explain = G_explain;
        gallery_data.Owner_explain = O_explain;
        gallery_data.Owner_insta = O_insta;
        gallery_data.Gallery_location_from_list=G_location_from_list[0];
        gallery_data.Gallery_location = G_location;
        gallery_data.Gallery_time = G_time;
        gallery_data.Gallery_fee = G_fee;

        mReference.child(G_name).setValue(gallery_data);

        Gallery_name.setText("");
        Gallery_explain.setText("");
        Owner_explain.setText("");
        Owner_insta.setText("");
        Gallery_location.setText("");
        Gallery_time.setText("");
        Gallery_fee.setText("");

        String tokenID = FirebaseInstanceId.getInstance().getToken();
        mReference = mDatabase.getReference("OwnerProfile/"+tokenID+"/MyGallerys/"+G_location_from_list[0]);
        mReference.child(G_name).setValue(G_name);

        Toast.makeText(getApplicationContext(), "갤러리 등록이 완료 되었습니다. 사용자 어플에서 확인해보세요.", Toast.LENGTH_LONG).show();

    }

}
