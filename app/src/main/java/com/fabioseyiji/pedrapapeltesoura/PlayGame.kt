package com.fabioseyiji.pedrapapeltesoura

import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import com.fabioseyiji.pedrapapeltesoura.databinding.ActivityPlayGameBinding

class PlayGame : AppCompatActivity() {

    private val binding: ActivityPlayGameBinding by lazy {
        ActivityPlayGameBinding.inflate(layoutInflater)
    }

    private var animation1: AnimationDrawable? = null
    private var animation2: AnimationDrawable? = null
    private var animation3: AnimationDrawable? = null
    private var setTime: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    private fun playAnimation() {
        val gameMode = intent.getIntExtra("1v1", 0)

        binding.apply {
            youSelectionImage.setBackgroundResource(R.drawable.animation_rpc)
            animation1 = youSelectionImage.background as AnimationDrawable
            cpu1SelectionImage.setBackgroundResource(R.drawable.animation_rpc)
            animation2 = cpu1SelectionImage.background as AnimationDrawable
            if (gameMode == 2) {
                cpu2Tv.visibility = View.VISIBLE
                cpu2SelectionImage.visibility = View.VISIBLE
                cpu2SelectionImage.setBackgroundResource(R.drawable.animation_rpc)
                animation3 = cpu2SelectionImage.background as AnimationDrawable
            }
        }

        setTime = object: CountDownTimer(3000, 1000){
            override fun onTick(p0: Long) {
                animation1?.start()
                animation2?.start()
                animation3?.start()
            }

            override fun onFinish() {
                animation1?.stop()
                animation2?.stop()
                animation3?.stop()
            }
        }.start()

    }
}