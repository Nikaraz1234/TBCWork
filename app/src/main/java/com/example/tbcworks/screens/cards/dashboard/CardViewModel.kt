import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcworks.R
import com.example.tbcworks.screens.cards.Card
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.InputStream

class CardViewModel(app: Application) : AndroidViewModel(app) {

    private var id = 4
    private val _cards = MutableStateFlow<List<Card>>(emptyList())
    val cards: StateFlow<List<Card>> = _cards

    init {
        loadCards()
    }

    private fun loadCards() {
        val context = getApplication<Application>()
        val inputStream: InputStream = context.resources.openRawResource(R.raw.cards)
        val jsonString = inputStream.bufferedReader().use { it.readText() }

        val cardList: List<Card> = Json.decodeFromString(jsonString)
        _cards.value = cardList


    }

    fun addCard(card: Card) {
        viewModelScope.launch {
            val newCard = card.copy(id = id++)
            _cards.value = _cards.value + newCard
        }

    }

    fun removeCard(cardId: Int) {
        viewModelScope.launch {
            _cards.value = _cards.value.filter { it.id != cardId }
        }

    }
}
