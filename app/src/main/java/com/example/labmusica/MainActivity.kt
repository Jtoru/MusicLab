package com.example.labmusica

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.DividerItemDecoration
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var recyclerView : RecyclerView
    lateinit var seekBar : SeekBar
    lateinit var startBtn : Button
    lateinit var mediaPlayer: MediaPlayer
    lateinit var playButton : ImageButton
    lateinit var songTextView: TextView
    lateinit var seekBar2 : SeekBar
    lateinit var audioManager: AudioManager
    private var play = false
    private var actualPosition = 0

    lateinit var backBtn : ImageButton
    lateinit var nextBtn : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recycler_view)
        seekBar = findViewById(R.id.seekBar)
        playButton = findViewById(R.id.imageButton)
        songTextView = findViewById(R.id.textView)
        seekBar2 = findViewById(R.id.seekBar2)

        backBtn = findViewById(R.id.imageButton3)
        nextBtn = findViewById(R.id.imageButton2)

        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val maxVol = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        val currentVol = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        seekBar2.max = maxVol
        seekBar2.progress = currentVol
        seekBar2.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

        val list = mutableListOf<Item>()
        list.add(Item("Head Up","Don Diablo",R.raw.head, R.drawable.logo))
        list.add(Item("On My Mind","Don Diablo",R.raw.mind, R.drawable.logo))
        list.add(Item("Save a Little Love","Don Diablo",R.raw.save, R.drawable.logo))
        list.add(Item("Wake Me When it's Quiet","Don Diablo",R.raw.wake, R.drawable.logo))
        list.add(Item("Estrellame", "Ghandhi", R.raw.ghandi_estrellame, R.drawable.logo2))
        list.add(Item("Happier", "Marshmello", R.raw.mars_happier, R.drawable.logo3))
        list.add(Item("People Say", "Don Diablo", R.raw.people_say, R.drawable.logo))
        list.add(Item("Jackie Chan","Tiesto", R.raw.jackie_chang, R.drawable.logo4))
        list.add(Item("Happy Now", "Zedd", R.raw.happy_now, R.drawable.logo5))
        list.add(Item("Hold On & Believe", "Martin Garrix", R.raw.hold_on,R.drawable.logo6))

        val adapter = MusicAdapter(this, list)
        val layoutManager  = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            layoutManager.orientation
        )
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        mediaPlayer = MediaPlayer.create(this,list[0].songId)
        songTextView.text = "${list[0].name} - ${list[0].autor}"

        recyclerView.addOnItemClickListener(object: OnItemClickListener{
            override fun onItemClicked(position: Int, view: View) {
                println("Clicked$position")
                val songItem = list[position]
                actualPosition = position
                setTrack(songItem.songId, songItem.name + " - "+ songItem.autor)
            }

        })
        playButton.setOnClickListener {
            if(!play){
                playButton.setImageResource(R.drawable.ic_pause_black_24dp)
                mediaPlayer.start()
            }
            else{
                playButton.setImageResource(R.drawable.ic_play_arrow)
                mediaPlayer.pause()
            }
            play = !play
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser){
                    mediaPlayer.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
        Timer().scheduleAtFixedRate(object : TimerTask(){
            override fun run() {
                seekBar.progress = mediaPlayer.currentPosition
            }

        }, 0, 100)

        seekBarProccess()

        backBtn.setOnClickListener {
            if(actualPosition == 0){
                val songItem = list[0]
                setTrack(songItem.songId, songItem.name + " - "+ songItem.autor)
            }else{
                actualPosition -= 1
                val songItem = list[actualPosition]
                setTrack(songItem.songId, songItem.name + " - "+ songItem.autor)
            }
        }
        nextBtn.setOnClickListener {
            if(actualPosition==9){
                val songItem = list[9]
                setTrack(songItem.songId, songItem.name + " - "+ songItem.autor)
            }else{
                actualPosition += 1
                val songItem = list[actualPosition]
                setTrack(songItem.songId, songItem.name + " - "+ songItem.autor)
            }
        }

    }

    fun seekBarProccess(){
        seekBar.max = mediaPlayer.duration
        seekBar.progress = mediaPlayer.currentPosition

    }

    fun setTrack(sound : Int, name : String){
        songTextView.text = name
        mediaPlayer.reset()
        mediaPlayer = MediaPlayer.create(this, sound)
        mediaPlayer.start()
        playButton.setImageResource(R.drawable.ic_pause_black_24dp)
        seekBarProccess()

    }
}
