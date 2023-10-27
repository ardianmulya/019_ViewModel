package com.example.activity4

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.activity4.data.DataForm
import com.example.activity4.data.dataST
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CobaViewModel : ViewModel() {
    var namaUsr : String by mutableStateOf("")
        private set
    var noTlp : String by mutableStateOf("")
        private set
    var jenisKl : String by mutableStateOf("")
        private set
    var alamat : String by mutableStateOf("")
        private set
    var status : String by mutableStateOf("")
        private set
    var email : String by mutableStateOf("")
        private set
    private val _uiState = MutableStateFlow(DataForm())
    val uiState : StateFlow<DataForm> = _uiState.asStateFlow()
    private val _state = MutableStateFlow(dataST())
    val state : StateFlow<dataST> = _state.asStateFlow()

    fun insertData (nm:String, tlp:String,jk:String,Alamat:String,st :String,em:String){
        namaUsr= nm;
        noTlp = tlp;
        jenisKl = jk;
        alamat = Alamat;
        status = st;
        email = em;
    }

    fun setJenisK(pilihJK : String){
        _uiState.update { currentState -> currentState.copy(sex = pilihJK) }
    }

//    fun setStatus(atus : String) {
//        _uiState.update { currentState ->
//            currentState.copy(st = atus)
//        }
//    }
}