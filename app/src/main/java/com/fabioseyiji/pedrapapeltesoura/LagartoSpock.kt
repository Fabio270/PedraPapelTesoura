package com.fabioseyiji.pedrapapeltesoura

import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.fabioseyiji.pedrapapeltesoura.databinding.ActivityLagartoSpockBinding

class LagartoSpock : AppCompatActivity() {

    private val binding: ActivityLagartoSpockBinding by lazy {
        ActivityLagartoSpockBinding.inflate(layoutInflater)
    }

    private var animation1: AnimationDrawable? = null
    private var animation2: AnimationDrawable? = null
    private var setTime: CountDownTimer? = null

    private var allowPlaying: Boolean = true

    private lateinit var selectionRealPlayer: String
    private lateinit var selectionCPU1: String

    private var scoreRealPlayer = 0
    private var scoreCpu = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.rockBt.setOnClickListener {
            onPlay("rock")
        }

        binding.paperBt.setOnClickListener {
            onPlay("paper")
        }

        binding.scissorBt.setOnClickListener {
            onPlay("scissor")
        }

        binding.spockBt.setOnClickListener {
            onPlay("spock")
        }

        binding.lizardBt.setOnClickListener {
            onPlay("lizard")
        }
    }

    private fun playAnimation() {
        binding.apply {
            binding.youSelectionImage.setImageResource(0)
            binding.cpu1SelectionImage.setImageResource(0)
            youSelectionImage.setBackgroundResource(R.drawable.animation_spock)
            animation1 = youSelectionImage.background as AnimationDrawable
            cpu1SelectionImage.setBackgroundResource(R.drawable.animation_spock)
            animation2 = cpu1SelectionImage.background as AnimationDrawable
        }

        setTime = object : CountDownTimer(3000, 1000) {
            override fun onTick(p0: Long) {
                animation1?.start()
                animation2?.start()
            }

            override fun onFinish() {
                animation1?.stop()
                animation2?.stop()
                allowPlaying = true
                binding.youSelectionImage.setBackgroundResource(0)
                binding.cpu1SelectionImage.setBackgroundResource(0)
                setSelectedIcon()
                setScore()
                endGame()
            }
        }.start()
    }

    private fun onPlay(selection: String) {
        if (allowPlaying) {
            selectionRealPlayer = selection
            selectionCPU1 = listOf("rock", "paper", "scissor", "spock", "lizard").random()
            allowPlaying = false
            playAnimation()
        }
    }

    private fun setSelectedIcon() {
        when (selectionRealPlayer) {
            "rock" -> binding.youSelectionImage.setImageResource(R.drawable.rock)
            "paper" -> binding.youSelectionImage.setImageResource(R.drawable.paper)
            "scissor" -> binding.youSelectionImage.setImageResource(R.drawable.scissor)
            "spock" -> binding.youSelectionImage.setImageResource(R.drawable.spock)
            "lizard" -> binding.youSelectionImage.setImageResource(R.drawable.lizard)
        }

        when (selectionCPU1) {
            "rock" -> binding.cpu1SelectionImage.setImageResource(R.drawable.rock)
            "paper" -> binding.cpu1SelectionImage.setImageResource(R.drawable.paper)
            "scissor" -> binding.cpu1SelectionImage.setImageResource(R.drawable.scissor)
            "spock" -> binding.cpu1SelectionImage.setImageResource(R.drawable.spock)
            "lizard" -> binding.cpu1SelectionImage.setImageResource(R.drawable.lizard)
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
        return if (selectionRealPlayer == selectionCPU1)
            "tie"
        else if ((selectionRealPlayer == "rock" && selectionCPU1 == "scissor") ||
            (selectionRealPlayer == "rock" && selectionCPU1 == "lizard") ||
            (selectionRealPlayer == "paper" && selectionCPU1 == "rock") ||
            (selectionRealPlayer == "paper" && selectionCPU1 == "spock") ||
            (selectionRealPlayer == "scissor" && selectionCPU1 == "paper")||
            (selectionRealPlayer == "scissor" && selectionCPU1 == "lizard")||
            (selectionRealPlayer == "lizard" && selectionCPU1 == "paper") ||
            (selectionRealPlayer == "lizard" && selectionCPU1 == "spock") ||
            (selectionRealPlayer == "spock" && selectionCPU1 == "rock") ||
            (selectionRealPlayer == "spock" && selectionCPU1 == "scissor"))
            "you"
        else
            "CPU"
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