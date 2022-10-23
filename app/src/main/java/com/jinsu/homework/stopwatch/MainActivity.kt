package com.jinsu.homework.stopwatch

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.jinsu.homework.stopwatch.databinding.ActivityMainBinding
import java.lang.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var isRunning = false
    private var isPause = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("TAG_Activity", "Main Activity : onCreate() called!")

        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        with(binding) {
            btnStart.setOnClickListener {
                isRunning = !isRunning
                if (isRunning) start() else pause()
            }
            btnRecord.setOnClickListener {
                if (!isRunning) currentLabTime() else reset()
            }
        }

        with(binding) {
            txtSubTime.isVisible = false
            layoutRecord.isVisible = false
            layoutTitle.isVisible = false
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun start() {
        with(binding) {
            txtSubTime.isVisible = true
            layoutRecord.isVisible = true
            layoutTitle.isVisible = true
        }

        // isRunning 이 false 라면 (start 하지 않은 상태라면)
        if (isRunning) {
            with(binding.btnStart) {
                text = "중지"
                setBackgroundResource(R.drawable.borderline_button_red)
            }
            isRunning = true
        }
    }

    // 일시정지 버튼
    private fun pause() {
        with(binding) {
            btnStart.setBackgroundResource(R.drawable.borderline_button_blue)
            btnStart.text = "계속"
            btnRecord.text = "초기화"
        }
    }

    // 초기화 버튼
    private fun reset() {
    }

    private fun currentLabTime() {

    }


    private inner class MyThread : Thread() {
        private var threadFlag = false
        override fun run() {
            super.run()
            Log.d("TAG_THREAD", "Thread : run() called!")
        }
    }
}