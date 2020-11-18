package com.example.networkactivity2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.Key

class RetrofitActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit)
//사용준비
        //http://mellowcode.org/json/students/
        //http://mellowcode.org > 서버 도메인 주소
        val retrofit = Retrofit.Builder()
            .baseUrl("http://mellowcode.org")
            .addConverterFactory(GsonConverterFactory.create()) //직접 서버로 부터 데이터를 받아와서 원하는 데이터 타입으로 가지고 오는 것을 Gson으로 했는데 이것이 그 역할을 하여야!!!
            //GsonConverterFactory.create() => 객체 생성
            .build() //마무리 작업

        //RetofitService.kt에서 요청하고 변환된 데이터를 가지고 오는 작업
        val service = retrofit.create(RetrofitService::class.java)

//사용방법 (GET)
        service.getStudentsList()
            .enqueue(object : Callback<ArrayList<Person>> { //queue = 대기줄 enqueue = 대기줄에 올려준다.
                override fun onFailure(call: Call<ArrayList<Person>>, t: Throwable) { //통신이 실패했을 때
                    call.isCanceled //요청이 왜 실패된건지
                    call.isExecuted //실행이 되었는지
                    call.cancel() // 한번 실수했을 때 리트라이 하지마!1
                    // 그런데 보통은 이런것들 보다는 실패했을 때 에러 메세지가 뜨게 한다.
                }

                override fun onResponse(
                    call: Call<ArrayList<Person>>,
                    response: Response<ArrayList<Person>>
                ) {
                    if (response.isSuccessful) { //통신이 잘 된 경우에 (200 번대)
                        val personList = response.body()
                        Log.d("retrofitt", "res : " + personList?.get(0)?.age)

                        val code = response.code()
                        Log.d("retrofitt", "code : " + code)

                        val error =
                            response.errorBody() // 에러바디를 받아서 사용자한테 보여줘야 할 때 사용 but 사용은 잘 안함@!!!
                        val header = response.headers()
                        Log.d("retrofitt", "code : " + header)

                    }
                }



            })
 //사용방법(POST) (1)
//        val params = HashMap<String,Any> ()
//        params.put("name", "오파닥")
//        params.put("age",20)
//        params.put("intro","안녕하세요")
//        service.createstudent(params).enqueue(object : Callback<Person>{
//            override fun onFailure(call: Call<Person>, t: Throwable) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onResponse(
//                call: Call<Person>,
//                response: Response<Person>) {
//                if (response.isSuccessful) {
//                    val person = response.body()
//                    Log.d("retrofitt", "name : " + person?.name)
//
//                }
//            }
//        })

        val person = Person(name = "오파닥", age = 20, intro = "반갑습니다!")
        service.createstudentEasy(person).enqueue(object : Callback<Person>{
            override fun onFailure(call: Call<Person>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(
                call: Call<Person>,
                response: Response<Person>) {
                if (response.isSuccessful) {
                    val person = response.body()
                    Log.d("retrofitt", "name : " + person?.name)

                }
            }
        })



        }


}