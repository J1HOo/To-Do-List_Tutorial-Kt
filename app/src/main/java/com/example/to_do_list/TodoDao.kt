package com.example.to_do_list

import androidx.room.*

@Dao
interface TodoDao {

    //전체 얻기. 기본값. 등록순서로 보이기
    @Query("select * from TodoDate order by registerTime desc")
    fun getAll() : MutableList<TodoDate>

    //전체 얻기. 날짜순으로 얻기.
    @Query("select * from tododate order by date, time desc")
    fun getAllTimeOrder() : MutableList<TodoDate>

    //할일 키워드로 얻기
    @Query("select * from tododate where text like '%' ||:text || '%' order by date,time desc")
    fun getTodosByText(text: String) : MutableList<TodoDate>


    @Insert
    fun insert(todo: TodoDate)

    @Update
    fun update(todo: TodoDate)

    @Delete
    fun delete(todo: TodoDate)

    @Delete
    fun deleteAll(vararg todo: TodoDate)

}