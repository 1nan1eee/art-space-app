package com.example.artspaceapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.artspaceapp.R
import com.example.artspaceapp.models.Artwork

class ArtworkViewModel : ViewModel() {
    private val _artworks = MutableLiveData<List<Artwork>>()
    val artworks: LiveData<List<Artwork>> get() = _artworks

    private val _currentIndex = MutableLiveData<Int>(0)
    val currentIndex: LiveData<Int> get() = _currentIndex

    init {
        loadArtworks()
    }

    private fun loadArtworks() {
        _artworks.value = listOf(
            Artwork(R.drawable.a_girl_with_peaches, "Девочка с персиками", "В.А. Серов (1887г.)"),
            Artwork(R.drawable.christ_in_the_desert, "Христос в пустыне", "И.Н. Крамской (1872г.)"),
            Artwork(R.drawable.kursistka, "Курсистка", "Н.А. Ярошенко (1883г.)"),
            Artwork(R.drawable.rooks_back, "Грачи прилетели", "А.К. Саврасов (1871г.)"),
            Artwork(R.drawable.rainbow, "Радуга", "А.И. Куинджи (1905г.)")
        )
    }

    fun nextArtwork() {
        if (_artworks.value != null && _currentIndex.value!! < (_artworks.value!!.size - 1)) {
            _currentIndex.value = _currentIndex.value!! + 1
        }
    }

    fun previousArtwork() {
        if (_currentIndex.value!! > 0) {
            _currentIndex.value = _currentIndex.value!! - 1
        }
    }

    fun getCurrentArtwork(): Artwork? {
        return _artworks.value?.get(_currentIndex.value ?: 0)
    }
}
