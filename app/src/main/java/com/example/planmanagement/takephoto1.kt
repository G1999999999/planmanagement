package com.example.planmanagement


import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.File

class takephoto1: AppCompatActivity() {
    val takePhoto = 1
    val fromAlbum = 2
    lateinit var icon: ImageButton
    lateinit var takePhotoBtn: Button
    lateinit var fromAlbumBtn: Button
    lateinit var clearimage: Button
    lateinit var imageView: ImageView
    lateinit var imageUri: Uri
    lateinit var outputImage: File
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.photo)
        imageView = findViewById(R.id.imageView)
        if (photo_String1 != "") {
            imageView.setImageBitmap(base64ToBitmap(photo_String1))
        }

        icon = findViewById(R.id.icon)
        icon.setImageDrawable(getResources().getDrawable(R.drawable.exit))
        icon.setOnClickListener {
            finish()
        }
        takePhotoBtn = findViewById(R.id.takePhotoBtn)
        takePhotoBtn.setOnClickListener {
            // 创建File对象，用于存储拍照后的图片
            outputImage = File(externalCacheDir, "output_image.jpg")
            if (outputImage.exists()) {
                outputImage.delete()
            }
            outputImage.createNewFile()
            imageUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                FileProvider.getUriForFile(
                    this,
                    "com.example.cameraalbumtest.fileprovider",
                    outputImage
                );
            } else {
                Uri.fromFile(outputImage);
            }
            // 启动相机程序
            val intent = Intent("android.media.action.IMAGE_CAPTURE")
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            startActivityForResult(intent, takePhoto)
        }
        fromAlbumBtn = findViewById(R.id.fromAlbumBtn)
        fromAlbumBtn.setOnClickListener {
            // 打开文件选择器
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            // 指定只显示照片
            intent.type = "image/*"
            startActivityForResult(intent, fromAlbum)
        }

        imageView = findViewById(R.id.imageView)
        imageView.setOnClickListener {
            println("nengdayin")
        }
        clearimage = findViewById(R.id.clearimage)
        clearimage.setOnClickListener {
            photo_String1 = ""
            imageView.setImageBitmap(null)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        imageView = findViewById(R.id.imageView)
        when (requestCode) {
            takePhoto -> {
                if (resultCode == Activity.RESULT_OK) {
                    val bitmap =
                        BitmapFactory.decodeStream(contentResolver.openInputStream(imageUri))
                    val a = Bitmap2StrByBase64(bitmap)
                    val b = base64ToBitmap(a)
                    photo_String1 = a.toString()
                    imageView.setImageBitmap(b)
                }
            }
            fromAlbum -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    data.data?.let { uri ->
                        // 将选择的照片显示
                        val bitmap =
                            BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
                        val a = Bitmap2StrByBase64(bitmap)
                        val b = base64ToBitmap(a)
                        photo_String1 = a.toString()
                        imageView.setImageBitmap(b)
                    }

                }
            }
        }
    }
}