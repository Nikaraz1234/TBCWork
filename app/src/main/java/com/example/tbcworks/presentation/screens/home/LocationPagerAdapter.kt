import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcworks.R
import coil.load
import com.example.tbcworks.databinding.ItemLocationLayoutBinding
import com.example.tbcworks.presentation.common.DiffCallback
import com.example.tbcworks.presentation.screens.home.LocationModel

class LocationPagerAdapter :
    ListAdapter<LocationModel, LocationPagerAdapter.LocationViewHolder>(DiffCallback()) {

    inner class LocationViewHolder(val binding: ItemLocationLayoutBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bind(location: LocationModel) = with(binding) {
            tvName.text = location.location
            tvDesc.text = location.title
            tvAltitude.text = location.altitude.toString()

            val imageUrl = location.image
            ivBackground.load(imageUrl) {
                placeholder(R.drawable.img_placeholder)
                error(R.drawable.img_placeholder)
                crossfade(true)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val binding = ItemLocationLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val location = getItem(position)
        holder.bind(location)

    }
}
