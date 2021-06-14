package com.example.part2_chap3_diary

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener

class MemoActivity: AppCompatActivity() {

    private val memoEditText: EditText by lazy {
        findViewById(R.id.memoEditText)
    }

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memo)

        val memo = getSharedPreferences("memo", Context.MODE_PRIVATE)

        memoEditText.setText(memo.getString("memo", ""))

        val runnable = Runnable {
            getSharedPreferences("memo", Context.MODE_PRIVATE).edit {
                putString("memo", memoEditText.text.toString())
                commit()
            }
            Log.d("MemoActivity", "Save::${memoEditText.text.toString()}")
        }

        memoEditText.addTextChangedListener {

            Log.d("MemoActivity", "TextChanged :: $it")
            handler.removeCallbacks(runnable)
            handler.postDelayed(runnable, 5000)
        }
    }
}