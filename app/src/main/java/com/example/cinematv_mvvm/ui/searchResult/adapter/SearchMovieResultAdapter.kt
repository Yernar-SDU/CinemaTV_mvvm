package com.example.cinematv_mvvm.ui.searchResult.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinematv_mvvm.R
import com.example.cinematv_mvvm.core.Config
import com.example.cinematv_mvvm.databinding.ItemSearchResultFilmBinding
import com.example.cinematv_mvvm.model.Movie

class SearchMovieResultAdapter(private val movieClickListener: OnMovieClickListener) :
    RecyclerView.Adapter<SearchMovieResultAdapter.MovieViewHolder>() {
    private var movies = ArrayList<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =
            ItemSearchResultFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(Config.IMAGE_URL + movies[position].poster_path)
            .placeholder(R.drawable.placeholder_png)
            .centerCrop()
            .into(holder.binding.filmImage)
        getItemViewType(position)
        holder.binding.filmName.text = movies[position].title
        holder.binding.filmYear.text = movies[position].release_date
        if (movies[position].genres.isNotEmpty()) {
            holder.binding.filmGenre.text = movies[position].genres[0].name
        } else {
            holder.binding.filmGenre.text = "Unknown genre"
        }

        val rating = movies[position].vote_average
        holder.binding.filmRating.text = rating.toString()
        var colorPath: Int = ContextCompat.getColor(holder.itemView.context, R.color.goodGrey)
        when {
            rating < 5 -> {
                colorPath = ContextCompat.getColor(holder.itemView.context, android.R.color.holo_red_light)
            }
            rating > 7.5 -> {
                colorPath = ContextCompat.getColor(holder.itemView.context, android.R.color.holo_green_light)
            }
        }
        holder.binding.filmRating.setTextColor(colorPath)

        holder.itemView.setOnClickListener {
            movieClickListener.onMovieClick(movies[position].id)
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun setData(movies: ArrayList<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(val binding: ItemSearchResultFilmBinding) :
        RecyclerView.ViewHolder(binding.root)


    interface OnMovieClickListener {
        fun onMovieClick(filmId: Int)
    }
}