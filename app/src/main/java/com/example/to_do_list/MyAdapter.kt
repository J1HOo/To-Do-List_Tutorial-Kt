package com.example.to_do_list

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.todo_item.view.*

class MyAdapter (val context: Context,
    var itemList: MutableList<TodoDate>,
    val viewModel: TodoViewModel,
    val goToDetailListener : (TodoDate, Int) -> Unit,
    val setList: () -> Unit
    ) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.todo_item,parent,false)

            return MyViewHolder(view)

        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            // position에 해당하는 Todo객체를 얻음
            val todo = itemList[position]

            holder.todoText.text = todo.text


            // todo객체의 isDone을 CheckBox의 isChecked에 set해줌
            holder.todoIsDone.isChecked = todo.isDone

            // CheckBox인 todoIsDone이 클릭되었을 때
            holder.todoIsDone.apply {
                setOnClickListener {
                    todo.isDone = this.isChecked
                    viewModel.update(todo)
                    //변경이 완료되었으므로 다시 todoList를 받아온 후
                    // liveData.value에 넣어주는 setList메서드 실행
                    setList()
                }
            }

            //할일이 적히는 todo_text를 감싸고있는 todoInfo를 클릭하면 DetailActivity 실행
            holder.todoInfo.setOnClickListener {
                goToDetailListener(todo, position)
            }


            //시간을 설정한적이 있는 경우 todo의 날짜와 시간이 표시됨.
            if (todo.time != null && todo.date != null) {
                holder.todoTime.apply {
                    text = "${todo.date} ${todo.time}"
                    visibility = View.VISIBLE
                }
            } else {
                holder.todoTime.apply {
                    text = ""
                    visibility = View.GONE
                }
            }

            //아이템 내의 x버튼을 누를 경우 삭제여부 확인.
            holder.todoDelete.setOnClickListener {
                val alertDialog = AlertDialog.Builder(context)
                        .setMessage("정말 삭제하시겠습니까?")
                        .setPositiveButton("삭제") {str, dialogInterface ->
                            val todo = itemList[position]
                            viewModel.delete(todo)
                            setList()
                        }
                        .setNegativeButton("취소",null)
                alertDialog.show()
            }
        }

        override fun getItemCount(): Int {
            return itemList.size
        }

        inner class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

            val todoInfo = itemView.todo_info
            val todoText = itemView.todo_text
            val todoTime = itemView.todo_time
            val todoDelete = itemView.todo_delete
            val todoIsDone: CheckBox = itemView.todo_done
        }
}