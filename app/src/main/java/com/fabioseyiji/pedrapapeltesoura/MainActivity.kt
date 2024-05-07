package com.fabioseyiji.pedrapapeltesoura

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fabioseyiji.pedrapapeltesoura.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        amb.button1v1.setOnClickListener {
            val intent = Intent(this, PlayGame::class.java)
            intent.putExtra("1v1", 1)
            startActivity(intent)
        }

        amb.button1v2.setOnClickListener{
            //TODO
        }

        amb.instructions.setOnClickListener {
            //TODO
        }
    }
}