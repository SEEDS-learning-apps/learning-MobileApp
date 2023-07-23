import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chat_bot.data.CardItem
import com.example.chat_bot.R

class CustomCardAdapter(private val cardItems: List<CardItem>) : RecyclerView.Adapter<CustomCardAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
        private val textView: TextView = itemView.findViewById(R.id.textView)

        fun bind(cardItem: CardItem) {
            imageView.setImageResource(cardItem.imageResource)
            textView.text = cardItem.text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.flashcards_detail, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cardItem = cardItems[position]
        holder.bind(cardItem)
    }

    override fun getItemCount(): Int {
        return cardItems.size
    }
}
