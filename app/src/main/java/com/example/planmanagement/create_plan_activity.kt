package com.example.planmanagement
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class create_plan_activity : AppCompatActivity() {
    var title_plan = ""
    var content = ""
    var data1 = ""
    var data2 = ""
    var catewenzi = ""
    var zhongyaochengdu=""
    var finishedwenzi = ""
    var TAG = "test"
    private lateinit var icon: ImageButton
    private lateinit var sure_create: Button
    private lateinit var radiogroup1: RadioGroup
    private lateinit var radiogroup2: RadioGroup
    private lateinit var radiogroup3: RadioGroup
    private lateinit var listView: ListView
    private lateinit var image2: ImageButton
    private lateinit var image1: ImageButton
    val dbHelper = database(this, "planta.db", 1)

    @SuppressLint("UseCompatLoadingForDrawables", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create1)
        icon = findViewById(R.id.icon)
        icon.setImageDrawable(getResources().getDrawable(R.drawable.exit))
        icon.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            dbHelper.writableDatabase
            startActivity(intent)
            finish()
            Log.d(TAG, "onClick:click")
        }
        var edittitle = findViewById(R.id.edittitle) as EditText
        var editcontent = findViewById(R.id.editcontent) as EditText
        var data1edit = findViewById(R.id.data1edit) as EditText
        var data2edit = findViewById(R.id.data2edit) as EditText

        radiogroup1 = findViewById(R.id.radiogroup1)
        radiogroup1.setOnCheckedChangeListener { radiogroup1, checkid ->
            when (checkid) {
                R.id.ca1 -> {
                    catewenzi = "工作"
                }
                R.id.ca2 -> {
                    catewenzi = "生活"
                }
                R.id.ca3 -> {
                    catewenzi = "个人"
                }
                R.id.ca4 -> {
                    catewenzi = "旅游"
                }
            }
        }
        radiogroup2 = findViewById(R.id.radiogroup2)
        radiogroup2.setOnCheckedChangeListener { radiogroup2, checkid ->
            when (checkid) {
                R.id.finish -> {
                    finishedwenzi = "完成"
                }
                R.id.unfinish -> {
                    finishedwenzi = "未完成"
                }
            }
        }
        radiogroup3 = findViewById(R.id.radiogroup3)
        radiogroup3.setOnCheckedChangeListener { radiogroup3, checkid ->
            when (checkid) {
                R.id.feichangzhongyao -> {
                    zhongyaochengdu= "非常重要"
                }
                R.id.yibanzhongyao -> {
                    zhongyaochengdu = "一般重要"
                }
            }
        }
        val dbHelper = database(this, "planta.db", 1)
        sure_create = findViewById(R.id.sure_create)
        sure_create.setOnClickListener {
            if(zhongyaochengdu==""||catewenzi==""||finishedwenzi==""){
                Toast.makeText(this@create_plan_activity, "请选择必选项", Toast.LENGTH_SHORT).show()
            }
            else {
                title_plan = edittitle.text.toString()
                content = editcontent.text.toString()
                data1 = data1edit.text.toString()
                data2 = data2edit.text.toString()
                println(title_plan)
                checkandselectid()
                val db = dbHelper.writableDatabase
                val values1 = ContentValues().apply {
                    put("id", id)
                    put("title", title_plan)
                    put("date1", data1)
                    put("date2", data2)
                    put("content", content)
                    put("fujian", photo_String)
                    put("fujian1", photo_String1)
                    put("category", catewenzi)
                    put("important", zhongyaochengdu)
                    put("factfinish", finishedwenzi)
                }
                db.insert("planta", null, values1)
                dbHelper.writableDatabase
                shujuchazhao("all")
                Toast.makeText(this@create_plan_activity, "添加成功", Toast.LENGTH_SHORT).show()
                Toast.makeText(this@create_plan_activity, "全选", Toast.LENGTH_SHORT).show()
                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
                id++
                photo_String=""
                photo_String1=""
            }
        }
        image2=findViewById(R.id.image2)
        image2.setOnClickListener{
            var intent = Intent(this, takephoto::class.java)
            startActivity(intent)
            //finish()
            Log.d(TAG, "onClick:click")
        }
        image1=findViewById(R.id.image1)
        image1.setOnClickListener{
            var intent = Intent(this, takephoto1::class.java)
            startActivity(intent)
            //finish()
            Log.d(TAG, "onClick:click")
        }
    }
    @SuppressLint("Range")
    private fun checkandselectid() {
        val db = dbHelper.writableDatabase
        val cursor = db.query("planta", null, null, null, null, null, null)
        if (cursor.moveToFirst()) {
            planList = ArrayList<Plan>()
            do {
                val id_num = cursor.getInt(cursor.getColumnIndex("id"))
                if (id == id_num) {
                    id = id + 1
                }
            } while (cursor.moveToNext())
        }
    }

    @SuppressLint("Range")
    private fun shujuchazhao(zhuangtai:String){
        val db=dbHelper.writableDatabase
        val cursor=db.query("planta",null,null,null,null,null,null)
        if(cursor.moveToFirst()) {
            planList = ArrayList<Plan>()
            do {
                val id_num = cursor.getInt(cursor.getColumnIndex("id"))
                val title_show = cursor.getString(cursor.getColumnIndex("title"))
                val content_show = cursor.getString(cursor.getColumnIndex("content"))
                val fujian_show = cursor.getString(cursor.getColumnIndex("fujian"))
                val fujian1_show = cursor.getString(cursor.getColumnIndex("fujian1"))
                val factfinish_show = cursor.getString(cursor.getColumnIndex("factfinish"))
                photo_String=fujian_show
                photo_String1=fujian1_show
                if(factfinish_show==zhuangtai||zhuangtai=="all"){
                    planList.add(Plan(title_show,content_show,id_num))
                }
            } while (cursor.moveToNext())
//            val adapter = PlanAdapter(this, R.layout.plan_item, planList)
//            listView.adapter = adapter
        }
    }


}
