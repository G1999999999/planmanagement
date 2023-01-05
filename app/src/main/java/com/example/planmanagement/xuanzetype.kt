package com.example.planmanagement

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class xuanzetype : AppCompatActivity() {
    private lateinit var alll: LinearLayout
    private lateinit var workl: LinearLayout
    private lateinit var livel: LinearLayout
    private lateinit var peoplel: LinearLayout
    private lateinit var tripl: LinearLayout
    private lateinit var importantl: LinearLayout
    private lateinit var unimportantl: LinearLayout
    private lateinit var closel: LinearLayout
    private lateinit var listView: ListView
    val dbHelper=database(this,"planta.db",1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fenleimenu)
        alll=findViewById(R.id.alll)
        workl=findViewById(R.id.workl)
        livel=findViewById(R.id.livel)
        peoplel=findViewById(R.id.peoplel)
        tripl=findViewById(R.id.tripl)
        importantl=findViewById(R.id.importantl)
        unimportantl=findViewById(R.id.unimportantl)
        closel=findViewById(R.id.closel)
        alll.setOnClickListener{
            flag=0
            shujuchazhao("",1)
            Toast.makeText(this, "全选", Toast.LENGTH_SHORT).show()
            var intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
            zhaungtai="全选"

        }
        workl.setOnClickListener{
            flag=1
            shujuchazhao("工作")
            Toast.makeText(this, "工作", Toast.LENGTH_SHORT).show()
            var intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
            zhaungtai="工作"
        }
        livel.setOnClickListener{
            flag=1
            shujuchazhao("生活")
            Toast.makeText(this, "生活", Toast.LENGTH_SHORT).show()
            var intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
            zhaungtai="生活"
        }
        peoplel.setOnClickListener{
            flag=1
            shujuchazhao("个人")
            Toast.makeText(this, "个人", Toast.LENGTH_SHORT).show()
            var intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
            zhaungtai="个人"
        }
        tripl.setOnClickListener{
            flag=1
            shujuchazhao("旅游")
            Toast.makeText(this, "旅游", Toast.LENGTH_SHORT).show()
            var intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
            zhaungtai="旅游"
        }
        importantl.setOnClickListener{
            flag=1
            shujuchazhao("非常重要")
            Toast.makeText(this, "非常重要", Toast.LENGTH_SHORT).show()
            var intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
            zhaungtai="非常重要"
        }
        unimportantl.setOnClickListener{
            flag=1
            shujuchazhao("一般重要")
            Toast.makeText(this, "一般重要", Toast.LENGTH_SHORT).show()
            println(planList)
            var intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
            zhaungtai="一般重要"

        }
        closel.setOnClickListener{
            var intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
    @SuppressLint("Range")
    private fun shujuchazhao(zhuangtai:String="",idd:Int=0){
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
                if((category_show==zhuangtai||important_show==zhuangtai)&&idd==0){
                    planList.add(Plan(title_show,content_show,id_num))
                }
                if(idd!=0){
                    planList.add(Plan(title_show,content_show,id_num))
                }
            } while (cursor.moveToNext())
        }
    }
}