/*
* 조진수
* */

package com.jinsu.homework.stopwatch

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.jinsu.homework.stopwatch.databinding.ActivityMainBinding
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

        // 최초 실행 시 구간 기록 부분 invisible 상태로 초기화
        with(binding) {
            txtSubTime.isVisible = false
            layoutTitle.isVisible = false
            itemViewContainer.isVisible = false
        }

        // 각 버튼에 이벤트 리스너 부착
        with(binding) {
            btnStart.setOnClickListener {
                isRunning = !isRunning              // 시작 버튼을 누르면 상태가 반대 값으로 변경

                Log.d(TAG, "btnStart clicked! and state : $isRunning")
                if (isRunning) start() else pause()
            }
            /*btnRecord.setOnClickListener {
            // TODO: 구간기록/초기화 버튼 리스너
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

            // 삭제 예정
            btnRecord.setOnClickListener {
                checkLapTime()
            }
            resetBtn.setOnClickListener { reset() }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun start() {
        Log.d(TAG, "start() called!")

        // 시작버튼 클릭 시 레이아웃 초기화
        with(binding) {
            btnStart.text = "중지"
            btnStart.setBackgroundResource(R.drawable.borderline_button_red)
            btnRecord.setTextColor(Color.BLACK)
        }

        timerTask = timer(period = 1000) {  // period = 1000 = 1초
            mainTime++
            lapTime++
            val secTotal = formatter.format(mainTime % 60)
            val minTotal = formatter.format(mainTime / 60)
            val hourTotal = formatter.format(mainTime / 3600)

            val secLap = formatter.format(lapTime % 60)
            val minLap = formatter.format(lapTime / 60)
            val hourLap = formatter.format(lapTime / 3600)

            // UI 작업
            runOnUiThread {
                binding.txtMainTime.text = """$hourTotal:$minTotal.$secTotal"""
                binding.txtSubTime.text = """$hourLap:$minLap.$secLap"""
            }
        }
    }

    private fun pause() {
        Log.d(TAG, "pause() called!")

        // 중지 버튼 클릭 시 레이아웃 초기화
        with(binding.btnStart) {
            text = "계속"
            setBackgroundResource(R.drawable.borderline_button_blue)
        }
        timerTask?.cancel()
    }

    @SuppressLint("SetTextI18n")
    private fun reset() {
        Log.d(TAG, "reset() called!")

        // 초기 상태로 초기화
        timerTask?.cancel()
        mainTime = 0
        lapTime = 0
        lapIndex = 0

        // 레이아웃 초기화
        with(binding) {
            btnStart.text = "시작"
            btnStart.setBackgroundResource(R.drawable.borderline_button_blue)
            btnRecord.text = "구간기록"
            txtMainTime.post {
                txtMainTime.text = "00:00.00"

                // 구간 기록(ScrollView) 내 view 제거
                itemViewContainer.removeAllViews()
                itemViewContainer.invalidate()
            }
        }

        // 구간 기록 타이틀 view invisible 상태로 변경
        with(binding) {
            txtSubTime.isVisible = false
            layoutTitle.isVisible = false
            itemViewContainer.isVisible = false
        }
    }

    @SuppressLint("SetTextI18n")
    private fun checkLapTime() {
        Log.d(TAG, "checkLapTime() called!")

        // init
        val container = binding.itemViewContainer
        val inflater = LayoutInflater.from(this@MainActivity)
        val itemView = inflater.inflate(R.layout.item_view, container, false)
        val txtIndex = itemView.findViewById<TextView>(R.id.txtRecordIndex)
        val txtLapTime = itemView.findViewById<TextView>(R.id.txtRecordRecord)
        val txtTotalTime = itemView.findViewById<TextView>(R.id.txtRecordTime)

        if (lapIndex == 0)
            lapTime = mainTime   // 구간 기록 최초 실행 시 전체 시간과 동기화

        lapIndex++  // 구간 기록 버튼 클릭마다 순번 증가

        // 구간 기록 view 화면에 보이도록 설정
        with(binding) {
            txtSubTime.isVisible = true
            layoutTitle.isVisible = true
            itemViewContainer.isVisible = true
        }

        // ScrollView 내 Child 각 학목의 text 설정
        with(txtIndex) {
            text = formatter.format(lapIndex)
            textSize = 16f
        }
        with(txtLapTime) {
            text =
                """${formatter.format(lapTime / 3600)}:${formatter.format(lapTime / 60)}.${formatter.format(lapTime % 60)}"""
            textSize = 16f
        }
        with(txtTotalTime) {
            text =
                """${formatter.format(mainTime / 3600)}:${formatter.format(mainTime / 60)}.${formatter.format(mainTime % 60)}"""
            textSize = 16f
        }
        container.addView(itemView, 0)

        lapTime = 0 // 기록 출력 후 측정 된 구간 기록 초기화
    }
}