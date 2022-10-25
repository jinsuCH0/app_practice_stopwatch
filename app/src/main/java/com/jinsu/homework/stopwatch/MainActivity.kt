/*
* 조진수
* */

package com.jinsu.homework.stopwatch

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.jinsu.homework.stopwatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    // isRunning = true -> 기록이 측정 중인 상태
    // isRunning = false -> timer 가 멈춘 상태
    private var isRunning = false
    private var lapIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Main Activity : onCreate() called!")

        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        with(binding) {
            txtSubTime.isVisible = false
            layoutTitle.isVisible = false
            layoutRecord.isVisible = false
        }

        with(binding) {
            btnStart.setOnClickListener {
                isRunning = !isRunning              // 시작 버튼을 누르면 상태가 반대 값으로 변경
                if (isRunning) start() else pause()
            }
            btnRecord.setOnClickListener {
                // 구간 기록 버튼 활성화 상태

                // 초기화 버튼 활성화 상태 :
                if  (isRunning ) {

                }
            }
        }
    }

    // isRunning = true 상태
    private fun start() {}

    // isRunning = false 상태
    private fun pause() {
        with(binding.btnStart) {
            text = "일시중지"
            setBackgroundResource(R.drawable.borderline_button_red)
        }

    }
    private fun reset() {
        with(binding) {
            txtSubTime.isVisible = false
            layoutTitle.isVisible = false
            layoutRecord.isVisible = false
        }
    }
    private fun checkLapTime() {
        with(binding) {
            txtSubTime.isVisible = false
            layoutTitle.isVisible = false
            layoutRecord.isVisible = false
        }
    }
}


/*
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
            Log.d("TAG_Thread", "Thread : run() called!")
            while(!isInterrupted) {
                try {
                    // TODO:
                } catch (e: InterruptedException) {
                    interrupt()
                    Log.d("TAG_Thread", "Thread is interrupted!")
                }
            }
        }
    }
}*/
