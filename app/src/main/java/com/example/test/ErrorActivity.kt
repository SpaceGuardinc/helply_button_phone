package com.example.test

import android.content.pm.PackageManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class ErrorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val builder = AlertDialog.Builder(this)
            .setTitle("Произошла ошибка")
            .setMessage("Вы должны позвонить по номеру +3123123132")
            .setPositiveButton("Позвонить") { dialog, which ->
                // Проверяем разрешение на совершение звонков
                if (checkSelfPermission(android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    val callIntent = Intent(Intent.ACTION_CALL)
                    callIntent.data = Uri.parse("tel:+3123123132")
                    startActivity(callIntent)
                    dialog.dismiss()
                    finish()
                } else {
                    requestPermissions(arrayOf(android.Manifest.permission.CALL_PHONE), CALL_PHONE_PERMISSION_REQUEST_CODE)
                }
            }
        // Убираем кнопку "Отмена"
        //.setNegativeButton("Отмена") { dialog, which ->
        //}

        val dialog = builder.create()
        dialog.setCancelable(false)
        dialog.show()
    }

    companion object {
        private const val CALL_PHONE_PERMISSION_REQUEST_CODE = 123
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == CALL_PHONE_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val callIntent = Intent(Intent.ACTION_CALL)
                callIntent.data = Uri.parse("tel:+3123123132")
                startActivity(callIntent)
            } else {
                // Здесь можно добавить обработку, если разрешение не предоставлено
            }
        }
    }
}
