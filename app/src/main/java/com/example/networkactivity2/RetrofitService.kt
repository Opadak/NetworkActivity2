package com.example.networkactivity2

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RetrofitService {
    //서버를 관리하다 보면 주소를 여러개 사용하게 되는데!
    //http://mellowcode.org/json/students/
    // http://mellowcode.org > 베이스 url
    // 그 외 > api
    // api 변하는 부분을 관리해주면 된다!!!!

    // *어노테이션
    // Request Type 를 적어준다. ( Get = > 정보 요청 )
    @GET("json/students/")
    fun getStudentsList(): Call<ArrayList<Person>>


    //해당 주소에 Get ( 정보 요청 ) 을 하는데 그 리스판스가 <ArrayList<Person>> 타입이다.
    // 정보요청한 데이터를 <ArrayList<Person>> 이 타입으로 변환해서 불러오겠다(call)라는 뜻!

    //리스판스 = 정보요청한 데이터
    //POST
    @POST("json/students/")
    fun createstudent(
        @Body params : HashMap<String, Any> // KEY 같은 경우 전부  String, value같은 경우는 타입이 고정이 되어있지않다!

    ): Call<Person>

    @POST("json/students/")
    fun createstudentEasy(
        @Body person: Person
    ): Call<Person>
//둘의 차이점 해쉬 맵의 유무.
}