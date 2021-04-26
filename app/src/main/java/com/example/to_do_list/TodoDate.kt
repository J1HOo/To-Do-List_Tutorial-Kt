package com.example.to_do_list

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

@Entity //Entity (항목) 데이터베이스 내의 테이블
data class TodoDate(var text: String?, var isDone: Boolean = false) : Serializable {


    @PrimaryKey(autoGenerate = true)    //DB에 저장할 고유 Key값 (autoGenerate = true)로 자동 생성

    var registerTime : Long = System.currentTimeMillis()    // 할 일을 등록하는 순간 시스템의 시간을 받아 '등록일 기준' 정렬에 사용할 기준으로 만듦.
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("ko", "KR"))

    // '날짜 기준' 정렬의 기준
    @ColumnInfo
    var time: String? = null
    @ColumnInfo
    var date: String? = null
    @ColumnInfo
    var dateLong: Long? = null
    @ColumnInfo
    var year: Int? = null
    @ColumnInfo
    var month: Int? = null
    @ColumnInfo
    var day: Int? = null
    @ColumnInfo
    var hour: Int? = null
    @ColumnInfo
    var minute: Int? = null



}
