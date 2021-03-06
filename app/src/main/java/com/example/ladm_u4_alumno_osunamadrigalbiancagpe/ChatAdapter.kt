package com.example.ladm_u4_alumno_osunamadrigalbiancagpe

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by ramankit on 25/7/17.
 */

class ChatAdapter(val chatData: List<Message>, val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val SENT = 0
    val RECEIVED = 1
    var df: SimpleDateFormat = SimpleDateFormat("hh:mm a",Locale.getDefault())

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder?.itemViewType){

            SENT -> {
                val holder: SentHolder = holder as SentHolder
                holder.sentTV.text = chatData[position].message
                val timeMilliSeconds = chatData[position].time
                val resultdate = Date(timeMilliSeconds)

                holder.timeStamp.text = df.format(resultdate)

                //Insertar asistencia en bd
                val msgA = chatData[position].message.split("¬")
                val nc = msgA[0]
                val nombre = msgA[1]
                val fecha = msgA[2]
                val hora = msgA[3]
                val min = msgA[4]

                val asist = Registros(context)
                asist.noctrl = nc
                asist.fecha = fecha
                asist.hora = hora
                asist.minutos = min
                asist.nombre = nombre
                if(asist.insertarAsistencia()) {
                    AlertDialog.Builder(context).setMessage(
                        "ASISTENCIA " +
                                "REGISTRADA A LAS ${hora}:${min}"
                    ).show()
                }

            }
            RECEIVED -> {
                val holder: ReceivedHolder = holder as ReceivedHolder
                holder.receivedTV.text = chatData[position].message
                val timeMilliSeconds = chatData[position].time
                val resultdate = Date(timeMilliSeconds)
                holder.timeStamp.text = df.format(resultdate)
            }

        }
    }

    override fun getItemViewType(position: Int): Int {

        when(chatData[position].type){
            Constants.MESSAGE_TYPE_SENT -> return SENT
            Constants.MESSAGE_TYPE_RECEIVED -> return RECEIVED
        }

        return -1
    }

    override fun getItemCount(): Int {
        return chatData.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType){
            SENT -> {
                val view = LayoutInflater.from(context).inflate(R.layout.sent_layout,parent,false)
                return SentHolder(view)
            }
            RECEIVED -> {
                val view = LayoutInflater.from(context).inflate(R.layout.received_layout,parent,false)
                return ReceivedHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(context).inflate(R.layout.sent_layout,parent,false)
                return SentHolder(view)
            }
        }
    }

    inner class SentHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var sentTV = itemView.findViewById<TextView>(R.id.sentMessage)
        var timeStamp = itemView.findViewById<TextView>(R.id.timeStamp)
    }

    inner class ReceivedHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var receivedTV = itemView.findViewById<TextView>(R.id.receivedMessage)
        var timeStamp = itemView.findViewById<TextView>(R.id.timeStamp)
    }

}