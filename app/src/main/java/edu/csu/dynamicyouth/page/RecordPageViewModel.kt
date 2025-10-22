package edu.csu.dynamicyouth.page

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.csu.dynamicyouth.api.RecordApi
import edu.csu.dynamicyouth.models.RecordVO
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class RecordPageViewModel @Inject constructor(
    val recordApi: RecordApi
) : ViewModel() {

    private val _records = MutableStateFlow<List<RecordVO>>(emptyList())
    val records: StateFlow<List<RecordVO>> = _records

    fun fetchRecords() {
        viewModelScope.launch {
            val records = recordApi.listRecord().data
            _records.value = records ?: emptyList()
        }
    }

}