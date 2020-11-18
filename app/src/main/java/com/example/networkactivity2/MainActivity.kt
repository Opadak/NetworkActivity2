package com.example.networkactivity2


import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.itemview.view.*
import java.io.BufferedReader
import java.io.InputStreamReader

import java.net.HttpURLConnection
import java.net.URL


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        NetworkTask(
            recycler_person,
            LayoutInflater.from(this@MainActivity)
        ).execute()
    }
}

class NetworkTask(
    val recyclerView: RecyclerView,
    val inflater: LayoutInflater

) : AsyncTask<Any?, Any?, Array<Person>>() {
// 3 type => first = Params. second = Progress. third = Result
//생성자를 만들어주자 ! => 데이터를 받을 수 있게끔!

    // 메인쓰레드로 넘어가는 오버라이드(메인스레드에서 실행이된다 즉, 다른 데이터를 쓸 수 있다.
    // 원하는 결과 값은 >> 네트워크 서버에서 가지고 온 것을 RecyclerView에 집어넣는 것!
    // 근데 내가 못 풀었던 것이 Recycler View 에 어떻게 가지고 온 데이터를 넣냐라는 것이었다.
    // 쓰레드는 보통 메인 쓰레드가 사용이 되는데 네트워크 서버를 불러오는 건 메인 쓰레드에서
    // 하면 안된다.(왜냐면 네트워크를 불러올 때 메인 쓰레드도 함께 멈추기 때문이다.*메인 쓰레드는 멈추면 안된다.
    // 이러한 상황에서 다른 쓰레드에서 네트워크를 불러오는데 이것을 메인 쓰레드로 넘겨주기 위해서는
    // onPostExcute에서 해줘야 한다.
    //*onPostExcute => 우편을 실행하다? => 데이터를 보내다? 이런식으로 해석해도 될듯!

    override fun onPostExecute(result: Array<Person>?) { //뷰를 그릴 때 여기서 사용!
        //실행이 되는 뷰를 불러오는 것!
         val adapter = PersonAdapter(result!!, inflater)
        recyclerView.adapter = adapter
//        recyclerView.layoutManager = LinearLayoutManager(context)
        super.onPostExecute(result)
    }

    override fun doInBackground(vararg params: Any?): Array<Person> {
        //요청을 보내는 것!
        val urlString: String = "http://mellowcode.org/json/students/"
        val url: URL = URL(urlString)
        val connection: HttpURLConnection = url.openConnection() as HttpURLConnection

        connection.requestMethod = "GET"
        connection.setRequestProperty("Content-Type", "application/json")

        var buffer = ""
        //InputStream
        //①일괄 처리를 위해 입력 장치로부터 운영 체제로 입력되는 제어문과 제어 데이터의 순서적인 집합.
        // 특히, 관리자가 어떤 작업 목적에 의거하여 처리하는 입력을 말한다.
        // ②연속 질의에 입력되어 처리될 데이터 스트림을 저장하는 메모리상의 저장 공간.
        // 인간이 읽지 못하는 컴퓨터 전용 언어들로 나열되어있다.
        if (connection.responseCode == HttpURLConnection.HTTP_OK) {

            //connection.responseCode는 연결된 네트워크 상태를 말하고
            //HttpURLConnection.HTTP_OK는 네트워크 상태가 200일 때를 말한다.
            //즉, 조건문에서 connection(연결된 주소)가 200상태일 때 이 구문을 실행한다라는 뜻이다.
            val reader = BufferedReader(
                //BufferedReader는 Scanner와 같은 입력 클래스 이다.
                //BufferedReader는 무조건 String으로 받는다.
                //다른 형태의 값을 원하면 형변환이 필수적!
                InputStreamReader(
                    connection.inputStream,
                    "UTF-8"

                    //charsetName => 문자 인코딩영어:
                    // character encoding), 줄여서 인코딩은
                    //사용자가 입력한 문자나 기호들을 컴퓨터가
                    //이용할 수 있는 신호로 만드는 것을 말한다.
                    //즉, 변수 connection이라는 곳에서 있는 컴퓨터만 읽을 수 있는 것들을, inputStream으로
                    //배열을 해서 다음 InputStreamReader로 읽어 UTF-8 로 변형해 사람이 읽을 수 있게 해준다.

                )
            )
            buffer = reader.readLine()
        }
        val data = Gson().fromJson(buffer, Array<Person>::class.java)
        //data라는 변수에


        return data
        //결국 return을 하면 onPostExecute에 들어간다!
    }

}


class PersonAdapter(
    val PersonList: Array<Person>,  //Personlist 는  Person의 배열 타입이다!
    val inflater: LayoutInflater
) : RecyclerView.Adapter<PersonAdapter.ViewHolder>() {
    inner class ViewHolder(itemVIew: View) : RecyclerView.ViewHolder(itemVIew) {
        val name: TextView
        val age: TextView
        val id :TextView
        val intro : TextView

        init {
            name = itemVIew.findViewById(R.id.user_name)
            age = itemVIew.findViewById(R.id.user_age)
            id = itemVIew.findViewById(R.id.user_id)
            intro = itemVIew.findViewById(R.id.user_intro)
        }
    }


    override fun getItemCount(): Int {
        return PersonList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.itemview, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.setText(PersonList.get(position).name ?: "")
        holder.age.setText(PersonList.get(position).age.toString() ?: "")
        holder.intro.setText(PersonList.get(position).intro ?: "")
        holder.id.setText(PersonList.get(position).id ?: "")
        }
}

