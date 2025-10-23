package com.example.tbcworks.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tbcworks.R
import com.example.tbcworks.enums.CategoryType
import com.example.tbcworks.items.ShopItem

class ShopViewModel : ViewModel() {
    companion object {
        const val BELT_NAME = "Belt suit blaze"
    }
    private var id = 0
    val allItems = listOf<ShopItem>(
        ShopItem(id = id++,
            image = R.drawable.item_girl_second,
            title = BELT_NAME,
            price = 120,
            categoryType = listOf(
                CategoryType.CATEGORY1, CategoryType.ALL
            )
        ),
        ShopItem(id = id++,
            image = R.drawable.item_girl,
            title = BELT_NAME,
            price = 120,
            categoryType = listOf(
                CategoryType.CATEGORY1, CategoryType.ALL
            )
        ),
        ShopItem(id = id++,
            image = R.drawable.item_girl_third,
            title = BELT_NAME,
            price = 120,
            categoryType = listOf(
                CategoryType.PARTY, CategoryType.ALL
            )
        ),
        ShopItem(id = id++,
            image = R.drawable.item_girl_fourth,
            title = BELT_NAME,
            price = 120,
            categoryType = listOf(
                CategoryType.CAMPING, CategoryType.ALL, CategoryType.PARTY
            )
        ),
        ShopItem(id = id++,
            image = R.drawable.item_girl_second,
            title = BELT_NAME,
            price = 120,
            categoryType = listOf(
                CategoryType.CATEGORY1, CategoryType.ALL
            )
        ),
        ShopItem(id = id++,
            image = R.drawable.item_girl,
            title = BELT_NAME,
            price = 120,
            categoryType = listOf(
                CategoryType.CATEGORY1, CategoryType.ALL
            )
        ),
        ShopItem(id = id++,
            image = R.drawable.item_girl_third,
            title = BELT_NAME,
            price = 120,
            categoryType = listOf(
                CategoryType.PARTY, CategoryType.ALL
            )
        ),
        ShopItem(id = id++,
            image = R.drawable.item_girl_fourth,
            title = BELT_NAME,
            price = 120,
            categoryType = listOf(
                CategoryType.CAMPING, CategoryType.ALL, CategoryType.PARTY
            )
        ),
        ShopItem(id = id++,
            image = R.drawable.item_girl_second,
            title = BELT_NAME,
            price = 120,
            categoryType = listOf(
                CategoryType.CATEGORY1, CategoryType.ALL
            )
        ),
        ShopItem(id = id++,
            image = R.drawable.item_girl,
            title = BELT_NAME,
            price = 120,
            categoryType = listOf(
                CategoryType.CATEGORY1, CategoryType.ALL
            )
        ),
        ShopItem(id = id++,
            image = R.drawable.item_girl_third,
            title = BELT_NAME,
            price = 120,
            categoryType = listOf(
                CategoryType.PARTY, CategoryType.ALL
            )
        ),
        ShopItem(id = id++,
            image = R.drawable.item_girl_fourth,
            title = BELT_NAME,
            price = 120,
            categoryType = listOf(
                CategoryType.CAMPING, CategoryType.ALL, CategoryType.PARTY
            )
        ),

    )


    private val _items = MutableLiveData<List<ShopItem>>(allItems)
    val items: LiveData<List<ShopItem>> get() = _items

    init {
        filterByCategory(CategoryType.ALL)
    }
    fun filterByCategory(category: CategoryType){
        _items.value = allItems.filter { it.categoryType.contains(category) }
    }
}