package com.example.to_do_list

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room

class TodoViewModel (context: Context) : ViewModel() {

        private val todoDatabase = Room.databaseBuilder(context, TodoDB::class.java, "todo")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()

        private val todoDao = todoDatabase.todoDao()
        private val todos = todoDao.getAll()
        val mutableLiveData = MutableLiveData<MutableList<TodoDate>>()
        var isTimeOrder: Boolean = false

        fun getList(isTimeOrder: Boolean): MutableList<TodoDate> {
            return if (isTimeOrder) getAllTimeOrder() else getAll()
        }

        fun getAll() : MutableList<TodoDate> {
            return todoDao.getAll()
        }

        //전체 얻기. 날짜순으로 얻기.
        fun getAllTimeOrder() : MutableList<TodoDate> {
            return todoDao.getAllTimeOrder()
        }

        //할일 키워드로 얻기
        fun getTodosByText(text: String) : MutableList<TodoDate> {
            return todoDao.getTodosByText(text)
        }

        fun insert(todo: TodoDate) {
            todoDao.insert(todo)
        }

        fun update(todo: TodoDate) {
            todoDao.update(todo)
        }

        fun delete(todo: TodoDate) {
            todoDao.delete(todo)
        }

        fun deleteAll(todo: Array<TodoDate>){
            todoDao.deleteAll(*todo)
        }

    }
