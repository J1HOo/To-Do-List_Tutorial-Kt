package com.example.to_do_list

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.detail_information.*
import java.util.*

class DetailActivity : AppCompatActivity() {
    lateinit var todo: TodoDate

    lateinit var time: String
    lateinit var date: String

    lateinit var bundle: Bundle

    var timeFlag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_information)

        bundle = intent.getBundleExtra("data")

        bundle?.apply {
            todo = getSerializable("todo") as TodoDate
            detail_input.setText(todo.text)
        }

        // 오늘 연,월,일을 get
        val calendar = Calendar.getInstance()
        val year = todo?.year ?: calendar.get(Calendar.YEAR)
        val month = todo?.month ?: calendar.get(Calendar.MONTH)
        val day = todo?.day ?: calendar.get(Calendar.DAY_OF_MONTH)
        val hour = todo?.hour ?: 9
        val minute = todo?.minute ?: 0
        date = "${year}.${month + 1}.$day"
        time = String.format("%02d:%02d", hour, minute)

        detail_todo_date.text = date
        detail_todo_time.text = time


        //날짜 지정
        val datePickerListener =
            DatePickerDialog.OnDateSetListener { view, _year, _month, dayOfMonth ->
                date = "${_year}.${_month + 1}.$dayOfMonth"
                detail_todo_date.text = date
                timeFlag = true
                todo.year = _year
                todo.month = _month
                todo.day = dayOfMonth
                todo.date = date
            }

        val datePickerDialog = DatePickerDialog(this, datePickerListener, year, month, day)
        val datePicker = datePickerDialog.datePicker
        datePicker.minDate = System.currentTimeMillis()

        detail_todo_date.setOnClickListener {
            datePickerDialog.show()
        }

        //시간 지정
        val timePickerListener =
            TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                val strHour = String.format("%02d", hourOfDay)
                val strMin = String.format("%02d", minute)

                time = "$strHour:$strMin"
                detail_todo_time.text = time
                timeFlag = true
                todo.hour = hourOfDay
                todo.minute = minute
                todo.time = time
            }


        val timePickerDialog = TimePickerDialog(
            this,
            android.R.style.Theme_Holo_Light_Dialog,
            timePickerListener,
            9,
            0,
            true
        )

        detail_todo_time.setOnClickListener {
            timePickerDialog.show()
        }


        // 확인버튼을 눌렀을때
        detail_confirm_btn.setOnClickListener(detailBtnListener)
        // 취소 버튼 눌렀을 때
        detail_cancel_btn.setOnClickListener(detailBtnListener)

    }

    val detailBtnListener = View.OnClickListener { view ->

        when (view) {
            //눌린 버튼이 확인 버튼일 때
            detail_confirm_btn -> {
                // 날짜, 시간을 변경한적이 있다면
                if (timeFlag) {
                    todo.time = time
                    todo.date = date
                }
                // 할일
                todo.text = detail_input.text.toString()
                bundle.putSerializable("todo", todo)
                intent.putExtra("data", bundle)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
            //눌린 버튼이 취소버튼일 때
            detail_cancel_btn -> {
                setResult(Activity.RESULT_CANCELED, intent)
                finish()
            }
        }

    }
}
