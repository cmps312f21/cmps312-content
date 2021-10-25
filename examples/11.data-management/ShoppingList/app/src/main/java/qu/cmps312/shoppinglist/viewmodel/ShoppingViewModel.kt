package qu.cmps312.shoppinglist.viewmodel

import android.app.Application
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import qu.cmps312.shoppinglist.entity.ShoppingItem
import qu.cmps312.shoppinglist.repository.ShoppingRepository
import kotlinx.coroutines.flow.*
import qu.cmps312.shoppinglist.entity.Category
import qu.cmps312.shoppinglist.entity.Product

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

    val categories = shoppingRepository.getCategories()
    //var selectedCategory = MutableLiveData<Long>()
    val products = mutableStateListOf<Product>()
    // When ever the category changes then get the associated products
    fun getProducts(categoryId: Long) = viewModelScope.launch(Dispatchers.IO) {
        products.clear()
        products.addAll(shoppingRepository.getProducts(categoryId))
    //val products = selectedCategory.switchMap { category ->
        //liveData {
            //emit( shoppingRepository.getProducts(category) )
        //}
    }
}