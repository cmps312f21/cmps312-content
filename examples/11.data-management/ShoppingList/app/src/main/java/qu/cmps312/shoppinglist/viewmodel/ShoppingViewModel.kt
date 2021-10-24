package qu.cmps312.shoppinglist.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import qu.cmps312.shoppinglist.entity.ShoppingItem
import qu.cmps312.shoppinglist.repository.ShoppingRepository
import kotlinx.coroutines.flow.*

class ShoppingViewModel(appContext: Application) : AndroidViewModel(appContext) {
    private val shoppingRepository = ShoppingRepository(appContext)

    var shoppingList = shoppingRepository.getItems()

    /*init {
        viewModelScope.launch() {
            shoppingRepository.getItems().observe() { shoppingItems ->
                shoppingList.clear()
                shoppingList.addAll(shoppingItems)
            }
        }
    }*/

    fun addItem(item: ShoppingItem) = viewModelScope.launch(Dispatchers.IO) {
        shoppingRepository.addItem(item)
    }

    fun updateQuantity(item: ShoppingItem) = viewModelScope.launch(Dispatchers.IO) {
        shoppingRepository.updateQuantity(item.id, item.quantity)
        //shoppingRepository.updateItem(item)
    }

    fun deleteItem(item: ShoppingItem) = viewModelScope.launch(Dispatchers.IO) {
        shoppingRepository.deleteItem(item)
    }
}