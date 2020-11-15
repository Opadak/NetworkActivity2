package com.example.networkactivity2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit)

        //http://mellowcode.org/json/students/
        //http://mellowcode.org > 서버 도메인 주소
        val retrofit =Retrofit.Builder()
            .baseUrl("http://mellowcode.org")
            .addConverterFactory(GsonConverterFactory.create()) //직접 서버로 부터 데이터를 받아와서 원하는 데이터 타입으로 가지고 오는 것을 Gson으로 했는데 이것이 그 역할을 하여야!!!
            //GsonConverterFactory.create() => 객체 생성
            .build() //마무리 작업

        //RetofitService.kt에서 요청하고 변환된 데이터를 가지고 오는 작업
        val service = retrofit.create(RetrofitService::class.java)



    }
}