package com.example.themeal.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.themeal.base.BaseViewModel
import com.example.themeal.data.model.FavoriteMeal
import com.example.themeal.data.model.MealCollapse
import com.example.themeal.data.repository.Repository

class FavoriteViewModel(
    private val favRepo: Repository.FavoriteMealRepository,
    private val mealRepo: Repository.MealRepository
) : BaseViewModel() {

    private val _listMeal = MutableLiveData<List<MealCollapse>>(mutableListOf())
    val listMeal: LiveData<List<MealCollapse>> get() = _listMeal

    private val _listFavorite = MutableLiveData<List<FavoriteMeal>>(mutableListOf())
    val listFavorite: LiveData<List<FavoriteMeal>> get() = _listFavorite

    private val _isInsertSuccess = MutableLiveData<Boolean>()
    val isInsertSuccess: LiveData<Boolean> get() = _isInsertSuccess

    private val _isDeleteSuccess = MutableLiveData<Boolean>()
    val isDeleteSuccess: LiveData<Boolean> get() = _isDeleteSuccess

    private val _listSearchResult = MutableLiveData<List<MealCollapse>>()
    val listSearchResult: LiveData<List<MealCollapse>> get() = _listSearchResult

    fun getAllFavoriteMeal() {
        launchAsync(
            request = { favRepo.getFavoriteMealId() },
            onSuccess = {
                _listFavorite.value = it
                getMeal()
            }
        )
    }

    fun insertNewFavorite(meal: FavoriteMeal) {
        launchAsync(
            request = { favRepo.insertFavoriteMeal(meal) },
            onSuccess = {
                _isInsertSuccess.value = true
                getAllFavoriteMeal()
            },
            onError = {
                _isInsertSuccess.value = false
            }
        )
    }

    fun deleteFavorite(meal: FavoriteMeal) {
        launchAsync(
            request = {
                favRepo.deleteFavoriteMeal(meal)
            },
            onSuccess = {
                _isDeleteSuccess.value = true
                getAllFavoriteMeal()
            },
            onError = {
                _isDeleteSuccess.value = false
            }
        )
    }

    private fun getMeal() {
        val listFav = _listFavorite.value as MutableList<FavoriteMeal>
        _listMeal.value = mutableListOf()
        for (fav in listFav) {
            launchAsync(
                request = { mealRepo.getMealById(fav.mealId) },
                onSuccess = {
                    val list = _listMeal.value as MutableList<MealCollapse>
                    it.meals?.first()?.mapToMealCollapse()?.let { meal ->
                        list.add(meal)
                        list.distinct()
                    }
                    _listMeal.value = list
                }
            )
        }
    }

    fun searchMealFavorite(text: String) {
        val listFavorite = _listMeal.value as MutableList<MealCollapse>
        _listSearchResult.value = listFavorite.filter { it.name?.contains(text) == true }
    }
}
