package dev.maxiscoding.todoer.vms

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.maxiscoding.todoer.model.Category
import dev.maxiscoding.todoer.services.recipeService
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class RecipeState(
    var isLoading: Boolean = false,
    var categories: List<Category> = listOf(),
    val error: String? = null
)

class AppRootViewModel : ViewModel() {
    private var _categoriesState by mutableStateOf(RecipeState())
    val categoriesState: RecipeState
        get() = _categoriesState

    init {
        try {
            fetchCategories()
        } catch (e: Exception) {
            println(e.message)
        }
    }

    private fun fetchCategories() {
        _categoriesState = _categoriesState.copy(isLoading = true)

        viewModelScope.launch {
            delay(3000)
            try {
                val response = recipeService.getCategories()
                _categoriesState = _categoriesState.copy(
                    isLoading = false,
                    categories = response.categories
                )
            } catch (e: Exception) {
                _categoriesState = _categoriesState.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
}