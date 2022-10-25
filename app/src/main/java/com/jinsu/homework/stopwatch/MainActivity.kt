/*
* 조진수
* */

package com.jinsu.homework.stopwatch

import android.R.layout
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams
import android.widget.RelativeLayout
import android.widget.RelativeLayout.RIGHT_OF
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.jinsu.homework.stopwatch.databinding.ActivityMainBinding
import kotlinx.coroutines.NonCancellable.start
import java.text.DecimalFormat
import java.util.*
import kotlin.concurrent.timer


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var timerTask: Timer? = null
    private var isRunning = false
    private var mainTime = 0
    private var lapTime = 0
    private var lapIndex = 0
    private val formatter = DecimalFormat("00")

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

                Log.d(TAG, "btnStart clicked! and state : $isRunning")
                if (isRunning) start() else pause()
            }
            // TODO: 구간기록/초기화 버튼 리스너
            /*btnRecord.setOnClickListener {
                // 처음 : 구간 기록인데 focusable = false
                if  (isRunning) {   // not start 상태인데
                    if (repeatedTime != 0) {
                        it.isClickable = false
                    } else {    // not start 인데 시작은 했다면
                        reset()
                    }
                } else {
                    checkLapTime()
                }
            }*/



            btnRecord.setOnClickListener {
                checkLapTime()
            }
            resetBtn.setOnClickListener { reset() }
        }


    }





    @SuppressLint("ResourceType", "SetTextI18n")
    private fun checkLapTime() {
        Log.d(TAG, "checkLapTime() called!")
        val position = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1F)


        // 구간 기록 view 화면에 보이도록 설정
        with(binding) {
            txtSubTime.isVisible = true
            layoutTitle.isVisible = true
            layoutRecord.isVisible = true
        }

        if (lapIndex == 0)
            lapTime = mainTime


        lapIndex++
        binding.btnStart.text = lapIndex.toString()




        val txtIndex = TextView(this).apply {
            textSize = 16f
            text = formatter.format(lapIndex)
        }
        val txtLapTime = TextView(this).apply {
            textSize = 16f
            text = "랩타임"
        }
        val txtTotalTime = TextView(this).apply {
            textSize = 16f
            text = "전체시간"
        }




        with(binding.layoutRecord) {
            addView(txtIndex, 0)
            addView(txtLapTime, -1)
            addView(txtTotalTime, -1)
        }
        lapTime = 0
    }














    // isRunning = true 상태
    @SuppressLint("SetTextI18n")
    private fun start() {
        Log.d(TAG, "start() called!")
        with(binding) {
            btnStart.text = "중지"
            btnStart.setBackgroundResource(R.drawable.borderline_button_red)
            btnRecord.setTextColor(Color.BLACK)
        }

        timerTask = timer(period = 1000) {  // period = 1000, 1초
            mainTime++
            lapTime++
            val secTotal = formatter.format(mainTime % 60)
            val minTotal = formatter.format(mainTime / 60)
            val hourTotal = formatter.format(mainTime / 3600)

            val secLap = formatter.format(lapTime % 60)
            val minLap = formatter.format(lapTime / 60)
            val hourLap = formatter.format(lapTime / 3600)

            runOnUiThread {
                binding.txtMainTime.text = """$hourTotal:$minTotal.$secTotal"""
                binding.txtSubTime.text = """$hourLap:$minLap.$secLap"""
            }
        }
    }
    // isRunning = false 상태
    private fun pause() {
        Log.d(TAG, "pause() called!")
        with(binding.btnStart) {
            text = "계속"
            setBackgroundResource(R.drawable.borderline_button_blue)
        }
        timerTask?.cancel()
    }

    @SuppressLint("SetTextI18n")
    private fun reset() {
        Log.d(TAG, "reset() called!")


        // 초기화 부분
        timerTask?.cancel()
        mainTime = 0
        lapTime = 0
        lapIndex = 0

        // view 초기상태로 변경
        with(binding) {
            btnStart.text = "시작"
            btnStart.setBackgroundResource(R.drawable.borderline_button_blue)
            btnRecord.text = "구간기록"
            txtMainTime.post {
                txtMainTime.text = "00:00.00"

                // 구간 기록 내 view 제거
                layoutRecord.removeAllViews()
                layoutRecord.invalidate()
            }
        }

        // 구간 기록 타이틀 view invisible 상태로 변경
        with(binding) {
            txtSubTime.isVisible = false
            layoutTitle.isVisible = false
            layoutRecord.isVisible = false
        }
    }
}