package qu.cmps312.shoppinglist.viewmodel

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import qu.cmps312.shoppinglist.entity.Category
import qu.cmps312.shoppinglist.entity.ShoppingItem
import qu.cmps312.shoppinglist.repository.ShoppingRepository

class ItemViewModel(appContext: Application) : AndroidViewModel(appContext) {
    private val shoppingRepository = ShoppingRepository(appContext)
    val categories = shoppingRepository.getCategories()
    var selectedCategory = MutableLiveData<Category>()

    // When ever the category changes then get the associated products
    val products = selectedCategory.switchMap { category ->
        liveData {
            emit( shoppingRepository.getProducts(category.id) )
        }
    }

    fun addItem(item: ShoppingItem) = viewModelScope.launch(Dispatchers.IO) {
        shoppingRepository.addItem(item)
    }
}