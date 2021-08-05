package com.example.cinematv_mvvm.ui.filmDetailed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinematv_mvvm.core.Config
import com.example.cinematv_mvvm.databinding.ItemActorBinding
import com.example.cinematv_mvvm.model.Person

class ActorRecyclerAdapter(val actorClickListener: ActorClickListener):
    RecyclerView.Adapter<ActorRecyclerAdapter.ActorViewHolder>() {

    private var actors = arrayListOf<Person>()
    private var _binding: ItemActorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        _binding = ItemActorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(Config.IMAGE_URL + actors[position].profile_path)
            .centerCrop()
            .into(holder.binding.actorImage)
        holder.binding.actorName.text = actors[position].name
        holder.binding.id.text = actors[position].id.toString()

        holder.itemView.setOnClickListener{
            actorClickListener.onActorClicked(actors[position].id)
        }
    }

    override fun getItemCount(): Int {
        return actors.size
    }

    fun setData(actors: ArrayList<Person>){
        this.actors = actors
        notifyDataSetChanged()
    }

    inner class ActorViewHolder(val binding: ItemActorBinding):RecyclerView.ViewHolder(binding.root)

    interface ActorClickListener{
        fun onActorClicked(actor_id: Int)
    }
}
