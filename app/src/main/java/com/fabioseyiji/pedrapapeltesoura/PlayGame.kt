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

    private var allowPlaying: Boolean = true

    private lateinit var selectionRealPlayer: String
    private lateinit var selectionCPU1: String
    private lateinit var selectionCPU2: String
    private var gameMode = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        gameMode = intent.getIntExtra("gamemode", 0)

        if (gameMode == 2) {
            binding.apply {
                cpu2Tv.visibility = View.VISIBLE
                cpu2SelectionImage.visibility = View.VISIBLE
            }
        }

        binding.rockBt.setOnClickListener {
            onPlay("rock")
        }

        binding.paperBt.setOnClickListener {
            onPlay("paper")
        }

        binding.scissorBt.setOnClickListener {
            onPlay("scissor")
        }
    }

    private fun playAnimation(gameMode: Int) {
        binding.apply {
            binding.youSelectionImage.setImageResource(0)
            binding.cpu1SelectionImage.setImageResource(0)
            youSelectionImage.setBackgroundResource(R.drawable.animation_rpc)
            animation1 = youSelectionImage.background as AnimationDrawable
            cpu1SelectionImage.setBackgroundResource(R.drawable.animation_rpc)
            animation2 = cpu1SelectionImage.background as AnimationDrawable
            if (gameMode == 2) {
                binding.cpu2SelectionImage.setImageResource(0)
                cpu2SelectionImage.setBackgroundResource(R.drawable.animation_rpc)
                animation3 = cpu2SelectionImage.background as AnimationDrawable
            }
        }

        setTime = object : CountDownTimer(3000, 1000) {
            override fun onTick(p0: Long) {
                animation1?.start()
                animation2?.start()
                animation3?.start()
            }

            override fun onFinish() {
                animation1?.stop()
                animation2?.stop()
                animation3?.stop()
                allowPlaying = true
                binding.youSelectionImage.setBackgroundResource(0)
                binding.cpu1SelectionImage.setBackgroundResource(0)
                if (this@PlayGame.gameMode == 2) {
                    binding.cpu2SelectionImage.setBackgroundResource(0)
                }
                setSelectedIcon()
                endGame()
            }
        }.start()

    }

    private fun onPlay(selection: String) {
        if (allowPlaying) {
            selectionRealPlayer = selection
            selectionCPU1 = listOf("rock", "paper", "scissor").random()
            if (gameMode == 2) {
                selectionCPU2 = listOf("rock", "paper", "scissor").random()
            }
            allowPlaying = false
            playAnimation(gameMode)
        }
    }

    private fun setSelectedIcon() {
        when (selectionRealPlayer) {
            "rock" -> binding.youSelectionImage.setImageResource(R.drawable.rock)
            "paper" -> binding.youSelectionImage.setImageResource(R.drawable.paper)
            "scissor" -> binding.youSelectionImage.setImageResource(R.drawable.scissor)
        }

        when (selectionCPU1) {
            "rock" -> binding.cpu1SelectionImage.setImageResource(R.drawable.rock)
            "paper" -> binding.cpu1SelectionImage.setImageResource(R.drawable.paper)
            "scissor" -> binding.cpu1SelectionImage.setImageResource(R.drawable.scissor)
        }

        if (gameMode == 2){
            when(selectionCPU2){
                "rock" -> binding.cpu2SelectionImage.setImageResource(R.drawable.rock)
                "paper" -> binding.cpu2SelectionImage.setImageResource(R.drawable.paper)
                "scissor" -> binding.cpu2SelectionImage.setImageResource(R.drawable.scissor)
            }
        }
    }
}