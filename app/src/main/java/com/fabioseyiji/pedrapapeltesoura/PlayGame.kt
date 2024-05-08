package com.fabioseyiji.pedrapapeltesoura

import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
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

    private var scoreRealPlayer = 0
    private var scoreCpu = 0

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
                setScore()
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

    private fun endGame(){
            if (scoreRealPlayer == 2 || scoreCpu == 2){
                Handler(Looper.getMainLooper()).postDelayed({
                    finish()
                }, 2000)
            }
    }

    private fun getResult(): String{
        if (gameMode == 1){
            return if (selectionRealPlayer == selectionCPU1)
                "tie"
            else if ((selectionRealPlayer == "rock" && selectionCPU1 == "scissor") ||
                    (selectionRealPlayer == "paper" && selectionCPU1 == "rock") ||
                    (selectionRealPlayer == "scissor" && selectionCPU1 == "paper"))
                "you"
            else
                "CPU"
        }
        else{
            val playerWinsCPU1 = (selectionRealPlayer == "rock" && selectionCPU1 == "scissor") ||
                    (selectionRealPlayer == "paper" && selectionCPU1 == "rock") ||
                    (selectionRealPlayer == "scissor" && selectionCPU1 == "paper")

            val playerWinsCPU2 = (selectionRealPlayer == "rock" && selectionCPU2 == "scissor") ||
                    (selectionRealPlayer == "paper" && selectionCPU2 == "rock") ||
                    (selectionRealPlayer == "scissor" && selectionCPU2 == "paper")

            return if (selectionRealPlayer == selectionCPU1 && selectionRealPlayer == selectionCPU2)
                "tie"
            else if (selectionRealPlayer != selectionCPU1 && selectionRealPlayer != selectionCPU2 && selectionCPU1 != selectionCPU2)
                "tie"
            else if (playerWinsCPU1 && playerWinsCPU2)
                "you"
            else if (playerWinsCPU1 && selectionRealPlayer == selectionCPU2)
                "tie"
            else if (playerWinsCPU2 && selectionRealPlayer == selectionCPU1)
                "tie"
            else
                "CPU"
        }
    }

    private fun setScore()
    {
        if (getResult()=="you")
        {
            scoreRealPlayer++
            Toast.makeText(this, "YOU WON!", Toast.LENGTH_SHORT).show()
        }
        else if (getResult() == "CPU"){
            scoreCpu++
            Toast.makeText(this, "CPU WON!", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this, "TIE!", Toast.LENGTH_SHORT).show()
        }
    }

     override fun onDestroy() {
        super.onDestroy()
        setTime = null
    }
}