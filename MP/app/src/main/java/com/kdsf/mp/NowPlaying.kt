package com.kdsf.mp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kdsf.mp.databinding.ActivityPlayerBinding
import com.kdsf.mp.databinding.FragmentNowPlayingBinding

class NowPlaying : Fragment() {
    companion object{
        lateinit var binding: FragmentNowPlayingBinding
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_now_playing, container, false)
        binding = FragmentNowPlayingBinding.bind(view)


        binding.root.visibility = View.INVISIBLE
        binding.playPauseBtnNP.setOnClickListener{
            if(PlayerActivity.isPlaying) pauseMusic() else playMusic()
        }
        binding.prevBtnNP.setOnClickListener{
            setSongPosition(increment = false)
            PlayerActivity.musicService!!.createMediaPlayer()
            Glide.with(this)
                .load(PlayerActivity.musicListPA[PlayerActivity.songPosition].artUri)
                .apply(RequestOptions().placeholder(R.drawable.music_photo).centerCrop())
                .into(binding.songImgnNP)
            binding.songNameNP.text = PlayerActivity.musicListPA[PlayerActivity.songPosition].title
            binding.artistNameNP.text = PlayerActivity.musicListPA[PlayerActivity.songPosition].artist
            playMusic()
            PlayerActivity.musicService!!.showNotification(R.drawable.pause_icon, 1F)
            PlayerActivity.binding.playPauseBtnPA.setImageResource(R.drawable.pause_icon)
        }
        binding.nextBtnNP.setOnClickListener{
            setSongPosition(increment = true)
            PlayerActivity.musicService!!.createMediaPlayer()
            Glide.with(this)
                .load(PlayerActivity.musicListPA[PlayerActivity.songPosition].artUri)
                .apply(RequestOptions().placeholder(R.drawable.music_photo).centerCrop())
                .into(binding.songImgnNP)
            binding.songNameNP.text = PlayerActivity.musicListPA[PlayerActivity.songPosition].title
            binding.artistNameNP.text = PlayerActivity.musicListPA[PlayerActivity.songPosition].artist
            playMusic()
            PlayerActivity.musicService!!.showNotification(R.drawable.pause_icon, 1F)
            PlayerActivity.binding.playPauseBtnPA.setImageResource(R.drawable.pause_icon)
        }
        binding.root.setOnClickListener{
            val intent = Intent(requireContext(), PlayerActivity::class.java)
            intent.putExtra("index", PlayerActivity.songPosition)
            intent.putExtra("class", "Now Playing")
            ContextCompat.startActivity(requireContext(), intent, null)
        }
        return view
    }

    override fun onResume(){
        super.onResume()
        if(PlayerActivity.musicService != null){
            binding.root.visibility = View.VISIBLE
            binding.songNameNP.isSelected = true
            Glide.with(this)
                .load(PlayerActivity.musicListPA[PlayerActivity.songPosition].artUri)
                .apply(RequestOptions().placeholder(R.drawable.music_photo).centerCrop())
                .into(binding.songImgnNP)
            binding.songNameNP.text = PlayerActivity.musicListPA[PlayerActivity.songPosition].title
            binding.artistNameNP.text = PlayerActivity.musicListPA[PlayerActivity.songPosition].artist
            if(PlayerActivity.isPlaying) binding.playPauseBtnNP.setImageResource(R.drawable.pause_icon_white)
            else binding.playPauseBtnNP.setImageResource(R.drawable.play_icon_white)
        }
    }

    private fun playMusic(){
        binding.playPauseBtnNP.setImageResource(R.drawable.pause_icon_white)
        PlayerActivity.musicService!!.showNotification(R.drawable.pause_icon, 1F)
        PlayerActivity.isPlaying = true
        PlayerActivity.musicService!!.mediaPlayer!!.start()
    }

    private fun pauseMusic(){
        binding.playPauseBtnNP.setImageResource(R.drawable.play_icon_white)
        PlayerActivity.musicService!!.showNotification((R.drawable.play_icon), 0F)
        PlayerActivity.isPlaying = false
        PlayerActivity.musicService!!.mediaPlayer!!.pause()
    }
}