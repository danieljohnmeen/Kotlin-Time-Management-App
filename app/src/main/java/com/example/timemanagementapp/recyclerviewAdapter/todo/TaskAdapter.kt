package com.example.timemanagementapp.recyclerviewAdapter.todo


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.timemanagementapp.R
import com.example.timemanagementapp.databaseHandling.interfaces.OnTaskItemClick

class TaskAdapter( val onTaskItemClick: OnTaskItemClick,val context: Context, private val list: List<StructureTask>): RecyclerView.Adapter<TaskAdapter.TaskViewHolder>(){


    inner class TaskViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val taskSubject: TextView = itemView.findViewById(R.id.taskRecyclerItemSub)
        val taskDescription: TextView = itemView.findViewById(R.id.taskRecyclerItemDes)
//        val checkedTextView: CheckedTextView = itemView.findViewById(R.id.taskRecyclerItem3)

        fun bind(item: StructureTask) {
            itemView.findViewById<ImageView>(R.id.taskRecyclerEdit).setOnClickListener {
                onTaskItemClick.onTaskItemClickFunc(item)
            }
            itemView.findViewById<ImageView>(R.id.taskRecyclerDelete).setOnClickListener {
                onTaskItemClick.onTaskItemDeleted(item)
            }
            itemView.findViewById<ImageView>(R.id.taskRecyclerSetAlarm).setOnClickListener {
                onTaskItemClick.onTaskItemClickAlarm(item)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.task_recycler_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.taskSubject.text = list[position].taskSubject
        holder.taskDescription.text = list[position].taskDescription
        val item = list[position]

        holder.bind(item)
    }

}
