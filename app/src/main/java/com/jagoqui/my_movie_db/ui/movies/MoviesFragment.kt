package com.jagoqui.my_movie_db.ui.movies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jagoqui.my_movie_db.R
import com.jagoqui.my_movie_db.data.ApiService
import com.jagoqui.my_movie_db.data.server.ListMovies
import com.jagoqui.my_movie_db.data.server.Movies
import com.jagoqui.my_movie_db.databinding.FragmentMoviesBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesFragment : Fragment(), AdapterView.OnItemClickListener {

    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var  binding : FragmentMoviesBinding
    private var listMovies = ArrayList<Movies>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        moviesViewModel =
                ViewModelProvider(this).get(MoviesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_movies, container, false)

        moviesViewModel.text.observe(viewLifecycleOwner, Observer {

        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentMoviesBinding.bind(view)

        binding.moviesRecyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.VERTICAL,
            false
        )
        loadList()
    }

    private fun loadList() {
        val apiKey = "71ff62eb0510fb08bc9991eaeeeb021a"

        ApiService.create()
            .getTopRated(apiKey)
            .enqueue(object : Callback<ListMovies>{
                override fun onResponse(call: Call<ListMovies>, response: Response<ListMovies>) {
                    if(response.isSuccessful){
                        listMovies = response.body()?.movies as ArrayList<Movies>
                        val adapter = MoviesAdapter(listMovies, this@MoviesFragment)
                        binding.moviesRecyclerView.adapter = adapter
                    }
                }

                override fun onFailure(call: Call<ListMovies>, t: Throwable) {
                    t.message?.let { Log.d("Error", it) }
                }
            })
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        TODO("Not yet implemented")
    }
}