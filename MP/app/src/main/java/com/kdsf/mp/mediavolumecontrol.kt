package com.kdsf.mp

import androidx.appcompat.app.AppCompatActivity
import android.media.AudioManager
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import com.kdsf.mp.R
import android.widget.SeekBar.OnSeekBarChangeListener

class mediavolumecontrol : AppCompatActivity() {
    var mAudio: AudioManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAudio = getSystemService(AUDIO_SERVICE) as AudioManager
        val volume = findViewById<View>(R.id.volume) as SeekBar
        initControls(volume, AudioManager.STREAM_MUSIC)
    }

    private fun initControls(seek: SeekBar, stream: Int) {
        seek.max = mAudio!!.getStreamMaxVolume(stream)
        seek.progress = mAudio!!.getStreamVolume(stream)
        seek.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(bar: SeekBar, progress: Int, fromUser: Boolean) {
                mAudio!!.setStreamVolume(stream, progress, AudioManager.FLAG_PLAY_SOUND)
            }

            override fun onStartTrackingTouch(bar: SeekBar) {}
            override fun onStopTrackingTouch(bar: SeekBar) {}
        })
    }
}