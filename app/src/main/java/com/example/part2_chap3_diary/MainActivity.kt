package com.example.part2_chap3_diary

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.edit
import java.util.*

class MainActivity : AppCompatActivity() {

    private val openButton: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.openButton)
    }

    private val changePasswordButton: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.changePasswordButton)
    }

    private val numberPicker1: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker1).apply {
            minValue = 0
            maxValue = 9
        }
    }

    private val numberPicker2: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker2).apply {
            minValue = 0
            maxValue = 9
        }
    }

    private val numberPicker3: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker3).apply {
            minValue = 0
            maxValue = 9
        }
    }

    private val numberPicker4: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker4).apply {
            minValue = 0
            maxValue = 9
        }
    }

    private val resetButton: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.resetButton)
    }

    var changePasswordMode = false

    private fun passwordFailAlert() {
        AlertDialog.Builder(this)
            .setTitle("Password Fail")
            .setMessage("비밀번호 오류")
            .setPositiveButton("확인") {_, _ -> }
            .create()
            .show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPicker1
        numberPicker2
        numberPicker3
        numberPicker4


        openButton.setOnClickListener {

            val passwordSet = getSharedPreferences("pass", Context.MODE_PRIVATE)
            val passwordFromUser = "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}${numberPicker4.value}"

            if (changePasswordMode) {
                Toast.makeText(this, "비밀번호 변경작업을 완료해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (passwordSet.getString("pass", "0000") == passwordFromUser) {
                startActivity(Intent(this, MemoActivity::class.java))
            }
            else {
                passwordFailAlert()
            }
        }

        changePasswordButton.setOnClickListener {

            val passwordSet = getSharedPreferences("pass", Context.MODE_PRIVATE)
            val passwordFromUser = "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}${numberPicker4.value}"

            if (changePasswordMode) {
                passwordSet.edit {
                    putString("pass", passwordFromUser)
                    commit()
                }
                changePasswordMode = false
                changePasswordButton.setBackgroundColor(Color.RED)
                Toast.makeText(this, "비밀번호가 변경되었습니다.", Toast.LENGTH_SHORT).show()
            }
            else {
                if (passwordSet.getString("pass", "0000") == passwordFromUser) {
                    changePasswordMode = true
                    changePasswordButton.setBackgroundColor(Color.GREEN)
                    Toast.makeText(this, "변경할 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                }
                else {
                    passwordFailAlert()
                }
            }
        }

        resetButton.setOnClickListener {
            val passwordSet = getSharedPreferences("pass", Context.MODE_PRIVATE)
            passwordSet.edit {
                putString("pass", "0000")
                commit()
            }
            Toast.makeText(this, "비밀번호가 초기화 되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }
}