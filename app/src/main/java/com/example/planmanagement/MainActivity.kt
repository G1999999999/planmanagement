package com.example.planmanagement

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.NotificationCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var search:EditText
    private lateinit var thebuttom:CardView
    private lateinit var icon: ImageView
    private lateinit var listView: ListView
    private lateinit var icon_button1:ImageButton
    private lateinit var icon_button2:ImageButton
    private lateinit var icon_button3:ImageButton
    var TAG="test"
    private lateinit var increplan_btn:FloatingActionButton
    val dbHelper=database(this,"planta.db",1)
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView=findViewById(R.id.listView)
        thebuttom=findViewById(R.id.thebuttom)
        thebuttom.visibility= View.VISIBLE
        icon_button1=findViewById(R.id.icon_button1)
        icon_button1.setOnClickListener{
            icon_button1.setImageDrawable(getResources().getDrawable(R.drawable.note_blue))
            icon_button2.setImageDrawable(getResources().getDrawable(R.drawable.wait_finish))
            icon_button3.setImageDrawable(getResources().getDrawable(R.drawable.finished))
            shujucz(zhaungtai,"",1)
        }
        icon_button2=findViewById(R.id.icon_button2)
        icon_button2.setOnClickListener{
            icon_button1.setImageDrawable(getResources().getDrawable(R.drawable.note))
            icon_button2.setImageDrawable(getResources().getDrawable(R.drawable.wait_finish_blue))
            icon_button3.setImageDrawable(getResources().getDrawable(R.drawable.finished))
            shujucz(zhaungtai,"未完成")
        }
        icon_button3=findViewById(R.id.icon_button3)
        icon_button3.setOnClickListener{
            icon_button1.setImageDrawable(getResources().getDrawable(R.drawable.note))
            icon_button2.setImageDrawable(getResources().getDrawable(R.drawable.wait_finish))
            icon_button3.setImageDrawable(getResources().getDrawable(R.drawable.finished_blue))
            shujucz(zhaungtai,"完成")
        }

        icon=findViewById(R.id.icon)
        icon.setImageDrawable(getResources().getDrawable(R.drawable.menu))
        icon.setOnClickListener{
            var intent= Intent(this,xuanzetype::class.java)
            startActivity(intent)
            finish()
        }
        initPlans() // 初始化数据
        if(message_flag==1) {
            shujuchazhao()
            message_flag=0
        }
        listView=findViewById(R.id.listView)
        val adapter = PlanAdapter(this, R.layout.plan_item, planList)
        listView.adapter = adapter
        listView.setOnItemClickListener { parent, view, position, id ->
        }
        listView.setOnItemClickListener { _, _, position, _ ->
            var intent= Intent(this,change_plan_activity::class.java)
            dbHelper.writableDatabase
            startActivity(intent)
            val fruit = planList[position]
            Toast.makeText(this, fruit.id.toString(), Toast.LENGTH_SHORT).show()
            xuhao=fruit.id
            finish()
        }
        increplan_btn=findViewById(R.id.increplan_btn)
        increplan_btn.setOnClickListener{
            var intent= Intent(this,create_plan_activity::class.java)
            dbHelper.writableDatabase
            startActivity(intent)
            finish()
            Log.d(TAG,"onClick:click")
        }
        search=findViewById(R.id.search) as EditText
        search.setOnClickListener{
            var search_content=search.text.toString()
            println("123")
            while (true) {
                search_content = search.text.toString()
                search_find_content(search_content)
                    break
                }
            }
    }

    @SuppressLint("Range")
    private fun initPlans() {
        if(flag==0) {
            val db = dbHelper.writableDatabase
            val cursor = db.query("planta", null, null, null, null, null, null)
            if (cursor.moveToFirst()) {
                planList.clear()
                do {
                    val id_num = cursor.getInt(cursor.getColumnIndex("id"))
                    val title_show = cursor.getString(cursor.getColumnIndex("title"))
                    val content_show = cursor.getString(cursor.getColumnIndex("content"))
                    planList.add(Plan(title_show, content_show, id_num))
                } while (cursor.moveToNext())
                val adapter = PlanAdapter(this, R.layout.plan_item, planList)
                listView.adapter = adapter
            }
        }
        else{
            val adapter = PlanAdapter(this, R.layout.plan_item, planList)
            listView.adapter = adapter
        }
    }
    @SuppressLint("Range")
    private fun shujucz(zhuangtai:String,wc:String,id:Int=0){
        val db=dbHelper.writableDatabase
        val cursor=db.query("planta",null,null,null,null,null,null)
        if(cursor.moveToFirst()) {
            planList = ArrayList<Plan>()
            do {
                val id_num = cursor.getInt(cursor.getColumnIndex("id"))
                val title_show = cursor.getString(cursor.getColumnIndex("title"))
                val content_show = cursor.getString(cursor.getColumnIndex("content"))
                val category_show = cursor.getString(cursor.getColumnIndex("category"))
                val important_show = cursor.getString(cursor.getColumnIndex("important"))
                val factfinish_show = cursor.getString(cursor.getColumnIndex("factfinish"))
                if((category_show==zhuangtai||important_show==zhuangtai)&&factfinish_show==wc){
                    planList.add(Plan(title_show,content_show,id_num))
                }
                if((category_show==zhuangtai||important_show==zhuangtai)&&id==1){
                    planList.add(Plan(title_show,content_show,id_num))
                }
                if(zhuangtai=="全选"&&id==1)
                {
                    planList.add(Plan(title_show,content_show,id_num))
                }
                if(zhuangtai=="全选"&&factfinish_show==wc)
                {
                    planList.add(Plan(title_show,content_show,id_num))
                }
            } while (cursor.moveToNext())
            val adapter = PlanAdapter(this, R.layout.plan_item, planList)
            listView.adapter = adapter
        }
    }
    private fun sendmessage1(sn:String,id:Int){
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        //发送通知
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("normal", "Normal", NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
            val channel2 = NotificationChannel("important", "Important", NotificationManager.IMPORTANCE_HIGH)
            manager.createNotificationChannel(channel2)
        }
        xuhao=id
        val intent = Intent(this, openmessage::class.java)
        val pi = PendingIntent.getActivity(this, 0, intent, 0)
        val notification = NotificationCompat.Builder(this, "important")
            .setContentTitle("通知")
            .setContentText("你有一个计划今天"+sn)
            .setSmallIcon(R.drawable.smallmessage)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.bigmessage))
            .setContentIntent(pi)
            .setAutoCancel(true)
            .build()
        manager.notify(id, notification)
    }
    @SuppressLint("Range")
    private fun shujuchazhao(){
        val db=dbHelper.writableDatabase
        val cursor=db.query("planta",null,null,null,null,null,null)
        if(cursor.moveToFirst()) {
            do {
                val id_num = cursor.getInt(cursor.getColumnIndex("id"))
                val date1_show = cursor.getString(cursor.getColumnIndex("date1"))
                val date2_show = cursor.getString(cursor.getColumnIndex("date2"))
                val factfinish_show = cursor.getString(cursor.getColumnIndex("factfinish"))
                if(date1_show== date()&&factfinish_show=="未完成"){
                    message_id.add(id_num)
                    sendmessage1("开始",id_num.toInt())
                }
                else if(date2_show== date()&&factfinish_show=="未完成"){
                    message_id.add(id_num)
                    sendmessage1("截止",id_num.toInt())
                }
            } while (cursor.moveToNext())
        }
    }
    @SuppressLint("Range")
    private fun search_find_content(key:String){
        val db=dbHelper.writableDatabase
        val cursor=db.query("planta",null,null,null,null,null,null)
        if(cursor.moveToFirst()) {
            planList.clear()
            do {
                val id_num = cursor.getInt(cursor.getColumnIndex("id"))
                val title_show = cursor.getString(cursor.getColumnIndex("title"))
                val date1_show = cursor.getString(cursor.getColumnIndex("date1"))
                val date2_show = cursor.getString(cursor.getColumnIndex("date2"))
                val content_show = cursor.getString(cursor.getColumnIndex("content"))
                val category_show = cursor.getString(cursor.getColumnIndex("category"))
                val important_show = cursor.getString(cursor.getColumnIndex("important"))
                val factfinish_show = cursor.getString(cursor.getColumnIndex("factfinish"))
                if(title_show.contains(key)||content_show.contains(key)||
                    category_show.contains(key)||important_show.contains(key)||factfinish_show.contains(key)||
                        date1_show.contains(key)||date2_show.contains(key)){
                    planList.add(Plan(title_show,content_show,id_num))
                }
            } while (cursor.moveToNext())
        }
        val adapter = PlanAdapter(this, R.layout.plan_item, planList)
        listView.adapter = adapter
    }
}
