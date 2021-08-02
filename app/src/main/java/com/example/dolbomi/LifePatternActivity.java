package com.example.dolbomi;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LifePatternActivity extends AppCompatActivity {

    private SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd");
    private DatabaseReference mDatabase;
    Calendar date = Calendar.getInstance();
    int year_ = date.get(Calendar.YEAR);
    int month_ = date.get(Calendar.MONTH) + 1;
    int day_ = date.get(Calendar.DAY_OF_MONTH);
    int hour_ = date.get(Calendar.HOUR_OF_DAY) + 9;
    String month_s;
    String day_s;

    String todayDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_pattern);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        if (hour_ >= 24) {
            hour_ -= 24;
            day_ += 1;
        }

        if(month_ < 10) {
            month_s = "0" + month_;
        }
        if(day_ < 10) {
            day_s = "0" + day_;
        }

        todayDate = year_ + "-" + month_s + "-" + day_s;

        getLifePatternValue();


    }

    private void getLifePatternValue() {
        setContentView(R.layout.activity_life_pattern);

        TextView dateTextView = (TextView) findViewById(R.id.dataTextView);
        // 오늘 날짜 보이기
        dateTextView.setText(todayDate);

        final DatabaseReference frontDoorValue = mDatabase.child("Home1").child(todayDate).child("Frontdoor"); // 현관문 센서 값
        final DatabaseReference bathRoomValue = mDatabase.child("Home1").child(todayDate).child("Bathroom"); // 욕실 문 센서 값
        final DatabaseReference bedRoomValue = mDatabase.child("Home1").child(todayDate).child("Bedroom"); // 침실 문 센서 값
        final DatabaseReference refrigeratorValue = mDatabase.child("Home1").child(todayDate).child("Refrigerator"); // 냉장고 센서 값


        ProgressBar progressBar1 = (ProgressBar) findViewById(R.id.progressBar1_bedroom);
        ProgressBar progressBar2 = (ProgressBar) findViewById(R.id.progressBar2_bathroom);
        ProgressBar progressBar3 = (ProgressBar) findViewById(R.id.progressBar3_drug);
        ProgressBar progressBar4 = (ProgressBar) findViewById(R.id.progressBar4_frontdoor);
        ProgressBar progressBar5 = (ProgressBar) findViewById(R.id.progressBar5_refrigerator);

        TextView textView1 = (TextView) findViewById(R.id.textView1);
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        TextView textView3 = (TextView) findViewById(R.id.textView3);
        TextView textView4 = (TextView) findViewById(R.id.textView4);
        TextView textView5 = (TextView) findViewById(R.id.textView5);

        bedRoomValue.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {

                    Long bedRoomCount = snapshot.getChildrenCount();

                    textView1.setText(bedRoomCount.toString());
                    progressBar1.setProgress(bedRoomCount.intValue());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        bathRoomValue.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {

                    Long bathroomCount = snapshot.getChildrenCount();

                    textView2.setText(bathroomCount.toString());
                    progressBar2.setProgress(bathroomCount.intValue());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        frontDoorValue.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {

                    Long frontDoorCount = snapshot.getChildrenCount();

                    textView4.setText(frontDoorCount.toString());
                    progressBar4.setProgress(frontDoorCount.intValue());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        refrigeratorValue.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {

                    Long refrigeratorCount = snapshot.getChildrenCount();

                    textView5.setText(refrigeratorCount.toString());
                    progressBar5.setProgress(refrigeratorCount.intValue());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
}