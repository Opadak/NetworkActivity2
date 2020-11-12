package com.example.networkactivity2


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

        val adapter = RecyclerViewAdapter(NetworkTask(), LayoutInflater.from(this))
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this)

    }
}
class NetworkTask():AsyncTask<Any?,Any?,Any?>(){


    override fun doInBackground(vararg params: Any?): Any? {
      val urlString: String = "http://mellowcode.org/json/students/"
        val url : URL = URL(urlString)
        val connection:HttpURLConnection = url.openConnection() as HttpURLConnection

        connection.requestMethod = "GET"
        connection.setRequestProperty("Content-Type","application/json")

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
        }
        val data = Gson().fromJson(buffer, Array<Person>::class.java)
        //data라는 변수에
        val datalist  = ArrayList<PersonList>()


        return data
    }

}
class PersonList () {
    val buffer = ""
    val data = Gson().fromJson(buffer,Array<Person>::class.java)


}

class RecyclerViewAdapter(
    var itemList: NetworkTask,
    val inflater: LayoutInflater
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>(){

    inner class  ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val all: TextView


        init {
            all = itemView.findViewById(R.id.user_id)
            val position: Int = adapterPosition
            val allmost:String = itemList.get(position).all
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewAdapter.ViewHolder {
       val view = inflater.inflate(R.layout.itemview, parent,false)
        return  ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 7
    }

    override fun onBindViewHolder(holder: RecyclerViewAdapter.ViewHolder, position: Int) {
            holder.all.setText(itemList.get(position).all)
    }
}

