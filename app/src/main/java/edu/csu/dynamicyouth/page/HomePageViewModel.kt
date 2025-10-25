package edu.csu.dynamicyouth.page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.csu.dynamicyouth.api.RecordApi
import edu.csu.dynamicyouth.models.RecordStatus
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

    fun fetchInitialStatus() {
        viewModelScope.launch {
            if (_status.value != HomePageStatus.CHECKING_IN) {
                //获取最近一条记录，看看在没在登山
                val lastRecord = recordApi.getLastRecord().data
                if (lastRecord == null) {
                    //必然没在登山
                    _status.value = HomePageStatus.IDLE
                } else {
                    if (lastRecord.status == RecordStatus.Pending) {
                        _status.value = HomePageStatus.CLIMBING
                    }
                }
            }
        }
    }

    fun checkIn() {

    }
}

enum class HomePageStatus {
    CLIMBING,
    IDLE,
    CHECKING_IN
}