package com.example.planmanagement

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


class change_plan_activity:AppCompatActivity() {
    var title_plan=""
    var content=""
    var data1=""
    var data2=""
    var fujian=""
    var catewenzi=""
    var finishedwenzi=""
    var zhongyaochengdu=""
    var TAG = "test"
    private lateinit var search: EditText
    private lateinit var icon: ImageButton
    private lateinit var change_plan: Button
    private lateinit var delete_bu: Button
    private lateinit var radiogroup1: RadioGroup
    private lateinit var radiogroup2: RadioGroup
    private lateinit var radiogroup3: RadioGroup
    private lateinit var ca1: RadioButton
    private lateinit var ca2: RadioButton
    private lateinit var ca3: RadioButton
    private lateinit var ca4: RadioButton
    private lateinit var feichangzhongyao: RadioButton
    private lateinit var yibanzhongyao: RadioButton
    private lateinit var finish: RadioButton
    private lateinit var unfinish: RadioButton
    private lateinit var image1:ImageButton
    private lateinit var image2:ImageButton
    val dbHelper=database(this,"planta.db",1)
    @SuppressLint("UseCompatLoadingForDrawables", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.change_plan)
        icon = findViewById(R.id.icon)
        icon.setImageDrawable(getResources().getDrawable(R.drawable.exit))
        icon.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            shujucz(zhaungtai)
            Toast.makeText(this, zhaungtai, Toast.LENGTH_SHORT).show()
            finish()
            Log.d(TAG, "onClick:click")
        }
        var edittitle=findViewById(R.id.edittitle) as EditText
        var editcontent=findViewById(R.id.editcontent) as EditText
        var data1edit=findViewById(R.id.data1edit) as EditText
        var data2edit=findViewById(R.id.data2edit) as EditText
        shujuchazhao("all",xuhao)
        println(idcontent)
            edittitle.setText(idcontent[0])
            data1edit.setText(idcontent[1])
            data2edit.setText(idcontent[2])
            editcontent.setText(idcontent[3])

            if(idcontent[4]!=""){
                image2=findViewById(R.id.image2)
                image2.setImageDrawable(getResources().getDrawable(R.drawable.added))
                photo_String= idcontent[4]
            }
            if(idcontent[5]!=""){
                image1=findViewById(R.id.image1)
                image1.setImageDrawable(getResources().getDrawable(R.drawable.added))
                photo_String1= idcontent[5]
            }
            if(idcontent[6]=="??????"){
                catewenzi="??????"
                ca1=findViewById(R.id.ca1)
                ca1.isChecked=true
            }
            else if(idcontent[6]=="??????"){
                catewenzi="??????"
                ca2=findViewById(R.id.ca2)
                ca2.isChecked=true
            }
            else if(idcontent[6]=="??????"){
                catewenzi="??????"
                ca3=findViewById(R.id.ca3)
                ca3.isChecked=true
            }
            else if(idcontent[6]=="??????"){
                catewenzi="??????"
                ca4=findViewById(R.id.ca4)
                ca4.isChecked=true
            }
            if(idcontent[7]=="????????????"){
                zhongyaochengdu="????????????"
                feichangzhongyao=findViewById(R.id.feichangzhongyao)
                feichangzhongyao.isChecked=true
            }
            else if(idcontent[7]=="????????????"){
                zhongyaochengdu="????????????"
                yibanzhongyao=findViewById(R.id.yibanzhongyao)
                yibanzhongyao.isChecked=true
            }
            if(idcontent[8]=="??????"){
                finishedwenzi="??????"
                finish=findViewById(R.id.finish)
                finish.isChecked=true
            }
            else if(idcontent[8]=="?????????"){
                finishedwenzi="?????????"
                unfinish=findViewById(R.id.unfinish)
                unfinish.isChecked=true
            }
        idcontent.clear()
        radiogroup1=findViewById(R.id.radiogroup1)
        radiogroup1.setOnCheckedChangeListener{radiogroup1,checkid->
            when(checkid){
                R.id.ca1->{catewenzi="??????"}
                R.id.ca2->{catewenzi="??????"}
                R.id.ca3->{catewenzi="??????"}
                R.id.ca4->{catewenzi="??????"}
            }
        }
        radiogroup2=findViewById(R.id.radiogroup2)
        radiogroup2.setOnCheckedChangeListener{radiogroup2,checkid->
            when(checkid){
                R.id.finish->{finishedwenzi="??????"}
                R.id.unfinish->{finishedwenzi="?????????"}
            }
        }
        radiogroup3=findViewById(R.id.radiogroup3)
        radiogroup3.setOnCheckedChangeListener{radiogroup3,checkid->
            when(checkid){
                R.id.feichangzhongyao->{zhongyaochengdu="????????????"}
                R.id.yibanzhongyao->{zhongyaochengdu="????????????"}
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

        change_plan=findViewById(R.id.change_plan)
        change_plan.setOnClickListener{
            title_plan=edittitle.text.toString()
            content=editcontent.text.toString()
            data1=data1edit.text.toString()
            data2=data2edit.text.toString()
            println(title_plan)
            //Toast.makeText(this,title_plan, Toast.LENGTH_SHORT).show()
            //Toast.makeText(this,content, Toast.LENGTH_SHORT).show()
            val dbHelper=database(this,"planta.db",1)
            val db=dbHelper.writableDatabase
            val values1= ContentValues()
            values1.put("title",title_plan)
            values1.put("date1",data1)
            values1.put("date2",data2)
            values1.put("content",content)
            values1.put("fujian", photo_String)
            values1.put("fujian1", photo_String1)
            values1.put("category",catewenzi)
            values1.put("important",zhongyaochengdu)
            values1.put("factfinish",finishedwenzi)
            //db.execSQL("update planta set ")
            db.update("planta",values1,"id=?",arrayOf(xuhao.toString()))
            finish()
            photo_String=""
            photo_String1=""
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            shujucz(zhaungtai)
            Toast.makeText(this,"????????????", Toast.LENGTH_SHORT).show()
            Toast.makeText(this, zhaungtai, Toast.LENGTH_SHORT).show()

            //xuhao=0

        }

        val builder = AlertDialog.Builder(this)
        delete_bu=findViewById(R.id.delete)
        delete_bu.setOnClickListener{
            var intent= Intent(this,MainActivity::class.java)
            builder.apply {
                builder.create()
                builder.setTitle("??????")
                builder.setMessage("?????????????????????")
                builder.setCancelable(true)
                builder.setPositiveButton(Html.fromHtml("<font color='#000000'>??????</font>")) { dialog, which ->
                    val db=dbHelper.writableDatabase
                    db.delete("planta","id=?", arrayOf(xuhao.toString()))
                    Toast.makeText(this@change_plan_activity,"????????????", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this@change_plan_activity, zhaungtai, Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                    shujucz(zhaungtai)
                    finish()

                    //xuhao=0
                }
                builder.setNegativeButton(Html.fromHtml("<font color='#000000'>??????</font>")) { dialog, which -> }
                builder.show()
            }
        }
    }

    @SuppressLint("Range")
    private fun shujuchazhao(zhuangtai:String, idd: Int =0){
        val db=dbHelper.writableDatabase
        val cursor=db.query("planta",null,null,null,null,null,null)
        if(cursor.moveToFirst()) {
            planList = ArrayList<Plan>()
            do {
                val id_num = cursor.getInt(cursor.getColumnIndex("id"))
                val title_show = cursor.getString(cursor.getColumnIndex("title"))
                val date1_show = cursor.getString(cursor.getColumnIndex("date1"))
                val date2_show = cursor.getString(cursor.getColumnIndex("date2"))
                val content_show = cursor.getString(cursor.getColumnIndex("content"))
                val fujian_show = cursor.getString(cursor.getColumnIndex("fujian"))
                val fujian1_show = cursor.getString(cursor.getColumnIndex("fujian1"))
                val category_show = cursor.getString(cursor.getColumnIndex("category"))
                val important_show = cursor.getString(cursor.getColumnIndex("important"))
                val factfinish_show = cursor.getString(cursor.getColumnIndex("factfinish"))
                photo_String=fujian_show
                photo_String1=fujian1_show
                if((factfinish_show==zhuangtai||zhuangtai=="all")&&idd==0){
                    planList.add(Plan(title_show,content_show,id_num))
                }
                if(id_num==idd){
                    idcontent.add(title_show)
                    idcontent.add(date1_show)
                    idcontent.add(date2_show)
                    idcontent.add(content_show)
                    idcontent.add(fujian_show)
                    idcontent.add(fujian1_show)
                    idcontent.add(category_show)
                    idcontent.add(important_show)
                    idcontent.add(factfinish_show)
                    //println(fujian_show)
                }
            } while (cursor.moveToNext())
        }
    }

    @SuppressLint("Range")
    private fun shujucz(zhuangtai:String=""){
        val db=dbHelper.writableDatabase
        val cursor=db.query("planta",null,null,null,null,null,null)
        if(cursor.moveToFirst()) {
            planList = ArrayList<Plan>()
            do {
                val id_num = cursor.getInt(cursor.getColumnIndex("id"))
                val title_show = cursor.getString(cursor.getColumnIndex("title"))
                val date1_show = cursor.getString(cursor.getColumnIndex("date1"))
                val date2_show = cursor.getString(cursor.getColumnIndex("date2"))
                val content_show = cursor.getString(cursor.getColumnIndex("content"))
                val fujian_show = cursor.getString(cursor.getColumnIndex("fujian"))
                val fujian1_show = cursor.getString(cursor.getColumnIndex("fujian"))
                val category_show = cursor.getString(cursor.getColumnIndex("category"))
                val important_show = cursor.getString(cursor.getColumnIndex("important"))
                val factfinish_show = cursor.getString(cursor.getColumnIndex("factfinish"))
                photo_String=fujian_show
                photo_String1=fujian1_show
                if(category_show==zhuangtai||important_show==zhuangtai){
                    planList.add(Plan(title_show,content_show,id_num))
                }
                if(zhuangtai=="??????"){
                    planList.add(Plan(title_show,content_show,id_num))
                }
            } while (cursor.moveToNext())
//            val adapter = FruitAdapter(this, R.layout.fruit_item, fruitList)
//            listView.adapter = adapter
        }
    }
}