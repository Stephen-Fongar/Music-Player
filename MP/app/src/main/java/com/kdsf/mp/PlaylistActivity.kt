package com.kdsf.mp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kdsf.mp.databinding.ActivityPlaylistBinding
import com.kdsf.mp.databinding.AddPlaylistDialogBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView


class PlaylistActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlaylistBinding
    private lateinit var adapter: PlaylistViewAdapter
    private lateinit var seekBar: SeekBar
    private lateinit var textView: TextView

    var min = 0
    var max: Int = 100
    var current: Int = 50

    companion object{
        var musicPlaylist: MusicPlaylist = MusicPlaylist()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.pl)
        super.onCreate(savedInstanceState)

        binding = ActivityPlaylistBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.playlistRV.setHasFixedSize(true)
        binding.playlistRV.setItemViewCacheSize(13)
        binding.playlistRV.layoutManager = GridLayoutManager(this@PlaylistActivity, 2)
        adapter = PlaylistViewAdapter(this, playlistList = musicPlaylist.ref)
        binding.playlistRV.adapter = adapter

        binding.backBtnPLA.setOnClickListener{finish()}
        binding.addPlaylistBtn.setOnClickListener{ customAlertDialog() }

    }
    private fun customAlertDialog(){
        val customDialog = LayoutInflater.from(this@PlaylistActivity).inflate(R.layout.add_playlist_dialog, binding.root, false)
        val binder = AddPlaylistDialogBinding.bind(customDialog)
        val builder = MaterialAlertDialogBuilder(this)
        builder.setView(customDialog)
            .setTitle("Playlist Details")
            .setPositiveButton("ADD"){ dialog, _ ->
                val playlistName = binder.yourPlaylist.text
                val createBy = binder.yourName.text
                if(playlistName != null && createBy != null)
                    if(playlistName.isNotEmpty() && createBy.isNotEmpty())
                    {
                        addPlaylist(playlistName.toString(), createBy.toString())
                    }
                dialog.dismiss()
            }.show()
    }
    private fun addPlaylist(name: String, createBy: String){
        var playlistExist = false
        for(i in musicPlaylist.ref){
            if(name.equals(i.name)){
                playlistExist = true
                break
            }
        }
        if(playlistExist) Toast.makeText(this, "Playlist Exist!!", Toast.LENGTH_SHORT).show()
        else{
            val tempPlaylist = Playlist()
            tempPlaylist.name = name
            tempPlaylist.playlist = ArrayList()
            tempPlaylist.createBy = createBy
            val calendar = Calendar.getInstance().time
            val sdf = SimpleDateFormat("dd MM yyyy", Locale.ENGLISH)
            tempPlaylist.createOn = sdf.format(calendar)
            musicPlaylist.ref.add(tempPlaylist)
            adapter.refreshPlaylist()
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }
}