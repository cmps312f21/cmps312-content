package qu.cmps312.shoppinglist.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import qu.cmps312.shoppinglist.entity.ShoppingItem
import qu.cmps312.shoppinglist.repository.ShoppingRepository

class ShoppingViewModel(appContext: Application) : AndroidViewModel(appContext) {
    private val shoppingRepository = ShoppingRepository(appContext)

    var selectedShoppingItem : ShoppingItem? = null

    val shoppingList = shoppingRepository.getItems()
    val shoppingItemsCount = shoppingRepository.getCount()

    fun addItem(item: ShoppingItem) = viewModelScope.launch(Dispatchers.IO) {
        shoppingRepository.addItem(item)
    }

    fun updateItem(item: ShoppingItem) = viewModelScope.launch(Dispatchers.IO) {
        shoppingRepository.updateItem(item)
    }

    fun updateQuantity(item: ShoppingItem) = viewModelScope.launch(Dispatchers.IO) {
        shoppingRepository.updateQuantity(item.id, item.quantity)
    }

    fun deleteItem(item: ShoppingItem) = viewModelScope.launch(Dispatchers.IO) {
        shoppingRepository.deleteItem(item)
    }

    val categories = shoppingRepository.getCategories()
    val categoriesMap = shoppingRepository.getCategoriesMap()

    fun getProducts(categoryId: Long) = liveData {
        emit ( shoppingRepository.getProducts(categoryId) )
    }

    fun getProductsMap(categoryId: Long) = liveData {
        emit ( shoppingRepository.getProductsMap(categoryId) )
    }

    suspend fun getCategoriesAndProductCounts() = shoppingRepository.getCategoriesAndProductCounts()
    suspend fun getCategoriesAndProducts() = shoppingRepository.getCategoriesAndProducts()
    suspend fun getCategoryNamesAndProductCounts() = shoppingRepository.getCategoryNamesAndProductCounts()
}