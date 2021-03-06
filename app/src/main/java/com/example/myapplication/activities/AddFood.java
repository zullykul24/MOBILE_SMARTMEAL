package com.example.myapplication.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.api.ApiClient;
import com.example.myapplication.api.ApiInterface;
import com.example.myapplication.models.MenuFoodItem;
import com.example.myapplication.models.UploadFoodItem;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;
import com.microsoft.signalr.HubConnectionState;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFood extends AppCompatActivity {
    Button btnAdd;
    ImageButton btnDele;
    ImageView image;
    EditText name, price;
    ImageButton camera, folder;
    int RE_CAMERA = 123;
    int RE_FOLDER = 456;
    int addNewFood = 0;
    private int dishType = 0;
    RadioButton type_food, type_drink;
    RadioGroup radioGroup;
    private UploadFoodItem item;
    HubConnection hubConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        AnhXa();
        hubConnection = HubConnectionBuilder.create(ApiClient.BASE_URL +"addFoodHub").build();
        hubConnection.start();

        // bat cam
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mo cam ne
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, RE_CAMERA);
            }
        });

        ///ch???n type
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId){
                        case R.id.type_food:
                            dishType = 1;
                            break;
                        case R.id.type_drink:
                            dishType = 0;
                            break;
                    }
                }
            });
        // mo folder
        folder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, RE_FOLDER);
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadNewFood();
            }
        });



        btnDele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }



    private void uploadNewFood() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BitmapDrawable bitmapDrawable = (BitmapDrawable) image.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap(); // chuyen ve sang kieu bitmap
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArray); // chuyen du lieu
        byte[] hinhanh = byteArray.toByteArray(); // chuyen ve kieu mang byte;
        if(name.getText().toString().equals("")==true||price.getText().toString().equals("")==true)
        {
            Toast.makeText(AddFood.this, "H??y ??i???n ????? th??ng tin", Toast.LENGTH_SHORT).show();
        } else
        {
            String foodName = name.getText().toString().trim();
            String foodPrice = price.getText().toString().trim();
            String foodBase64Image = Base64.encodeToString(hinhanh,0);

            item = new UploadFoodItem(foodName, Integer.parseInt(foodPrice), dishType, foodBase64Image);
            API_AddNewFood(item);



        }
    }

    private void API_AddNewFood(UploadFoodItem item) {
        ApiClient.getApiClient().create(ApiInterface.class).addNewFood(item).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("Add food response", response+"");
                if(response.code() == 200) {
                    addNewFood = 1;
                    Toast.makeText(AddFood.this, "Th??m m??n th??nh c??ng", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(AddFood.this, "???? c?? l???i x???y ra", Toast.LENGTH_SHORT).show();

                }
                if(addNewFood == 1){
                    try{
                        hubConnection.send("SendNewFood", 1);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Add food failed", t+"");
                Toast.makeText(AddFood.this, "???? c?? l???i x???y ra", Toast.LENGTH_SHORT).show();


            }
        });
        new HubConnectionTask().execute(hubConnection);
    }


    // lay du lieu trong camera hoawcj folder
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        // chon luu cai hinh do ne
        if(requestCode == RE_CAMERA && resultCode == RESULT_OK && data!=null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            image.setImageBitmap(bitmap);
        }
        // chon luu tam hinh trong folder
        if(requestCode==RE_FOLDER && resultCode == RESULT_OK && data!=null ){
            // lay dg dan trong bo nho
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                image.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    public  void AnhXa(){
        btnAdd = (Button) findViewById(R.id.buttonThem);
        btnDele = (ImageButton) findViewById(R.id.image_btn_huy);
        image = (ImageView) findViewById(R.id.imageFood);
        name = (EditText) findViewById(R.id.nameFood);
        price = (EditText) findViewById(R.id.priceFood);
        camera = (ImageButton) findViewById(R.id.camera);
        folder = (ImageButton) findViewById(R.id.folder);
        type_food = (RadioButton)findViewById(R.id.type_food);
        type_drink = (RadioButton)findViewById(R.id.type_drink);
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);

    }


    class HubConnectionTask extends AsyncTask<HubConnection, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(HubConnection... hubConnections) {
            HubConnection hubConnection = hubConnections[0];
            hubConnection.start().blockingAwait();
            return null;
        }
    }
}