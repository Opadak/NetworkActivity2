package com.example.networkactivity2

import java.io.Serializable

class Person (
    var id: String? = null,
    var name: String? = null,
    var age: Int? = null,
    var intro: String? = null

) :Serializable
//같은 변수명이 있는 곳을 찾아서 알려주는 것을 직렬화라고 한다....