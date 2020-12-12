package com.jagoqui.my_movie_db.ui.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.jagoqui.my_movie_db.R
import com.jagoqui.my_movie_db.data.server.Movies
import com.jagoqui.my_movie_db.databinding.MovieListItemBinding
import com.squareup.picasso.Picasso

class MoviesAdapter(
        var moviesList: ArrayList<Movies>,
        private val onItemClickListener: AdapterView.OnItemClickListener
) :
        RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.movie_list_item, parent, false)
        return MoviesViewHolder(itemView, onItemClickListener)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movie = moviesList[position]
        holder.bindMovie(movie)
    }

    override fun getItemCount(): Int = moviesList.size

    class MoviesViewHolder(
            itemView: View,
            var onItemClickListener: AdapterView.OnItemClickListener
    ) : RecyclerView.ViewHolder(itemView) {

        private val binding = MovieListItemBinding.bind(itemView)

        fun bindMovie(movie: Movies) {
            val URL_IMAGES = "https://image.tmdb.org/t/p/w500"
            if (movie.posterPath != null)
                Picasso.get().load(URL_IMAGES + movie.posterPath).into(binding.posterImageView)
            binding.titleTextView.text = movie.title
            binding.averageTextView.text = movie.voteAverage.toString()
            binding.releaseDataTextView.text = movie.releaseDate
        }
    }
}