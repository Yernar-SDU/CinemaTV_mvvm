package com.example.cinematv_mvvm.ui.film.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinematv_mvvm.R
import com.example.cinematv_mvvm.core.Config
import com.example.cinematv_mvvm.databinding.ItemMovieBinding
import com.example.cinematv_mvvm.model.Movie
import com.example.cinematv_mvvm.ui.film.listener.OnMovieClickListener
import javax.inject.Inject

class FilmAdapter @Inject constructor(val context: Context, val listener: OnMovieClickListener): RecyclerView.Adapter<FilmAdapter.FilmViewHolder>() {
    private var _binding: ItemMovieBinding? = null
    private val binding get() = _binding!!
    private var films = arrayListOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        _binding = ItemMovieBinding.bind(view)
        return  FilmViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val currentFilm: Movie = films[position]
        holder.binding.apply {
            id.text = currentFilm.id.toString()
            Glide.with(context)
                .load(Config.IMAGE_URL + currentFilm.poster_path)
                .centerCrop()
                .placeholder(R.drawable.placeholder_png)
                .into(movieImage)
            movieName.text = currentFilm.title
        }

        holder.itemView.setOnClickListener{
            listener.onMovieClick(holder.binding.id.text.toString().toInt())
        }

    }

    override fun getItemCount(): Int {
        return films.size
    }


    fun setData(films: ArrayList<Movie>){
        this.films = films
        notifyDataSetChanged()
    }

    class FilmViewHolder(val binding: ItemMovieBinding): RecyclerView.ViewHolder(binding.root)
}