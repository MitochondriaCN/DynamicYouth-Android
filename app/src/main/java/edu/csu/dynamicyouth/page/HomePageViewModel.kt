package edu.csu.dynamicyouth.page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.csu.dynamicyouth.api.RecordApi
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val recordApi: RecordApi
) : ViewModel() {

    private val _status = MutableStateFlow(HomePageStatus.IDLE)
    val status: StateFlow<HomePageStatus> = _status

    fun fetchStatus() {
        viewModelScope.launch {
            //获取最近一条记录，看看在没在登山
            val lastRecord = recordApi.getLastRecord().data
        }
    }
}

enum class HomePageStatus {
    CLIMBING,
    IDLE,
    CHECKING_IN
}