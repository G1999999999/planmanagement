package com.example.planmanagement

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.time.LocalDateTime
import kotlin.collections.ArrayList

var planList = ArrayList<Plan>()
var idcontent = ArrayList<String>()
var id=666
var xuhao=0
var bitmap=0
var flag=0
var message_flag=1
var zhaungtai:String="全选"
var message_id=ArrayList<Int>()
var photo_String:String=""
var photo_String1:String=""
fun date():String{
    var date:String=""
    var c= LocalDateTime.now()
    var current=c.toString()
    println(current)
    val year=current.subSequence(0,4)
    val month=current.subSequence(5,7)
    val day=current.subSequence(8,10)
    date=date.plus(year).plus(month).plus(day)
    //println(date)
    return date
}

fun Bitmap2StrByBase64(bit: Bitmap): String? {
    val bos = ByteArrayOutputStream()
    bit.compress(Bitmap.CompressFormat.JPEG, 40, bos) //参数100表示不压缩
    val bytes: ByteArray = bos.toByteArray()
    return Base64.encodeToString(bytes, Base64.DEFAULT)
}

fun base64ToBitmap(base64Data: String?): Bitmap? {
    val bytes = Base64.decode(base64Data, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
}
