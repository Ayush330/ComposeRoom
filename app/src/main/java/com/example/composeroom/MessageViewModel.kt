package com.example.composeroom

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeroom.RoomDatabase.DatabaseClass
import com.example.composeroom.RoomDatabase.EntityClass
import com.example.composeroom.RoomDatabase.MessageDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MessageViewModel() : ViewModel() {

     lateinit var  databaseInstance: DatabaseClass
     lateinit var databaseObject:MessageDAO

    var extra = MutableLiveData<List<EntityClass>>(mutableListOf())


    fun insert(data:EntityClass)
    {
        viewModelScope.launch (Dispatchers.IO){
            try {
                databaseObject.addMessage(data)
            }
            catch(e:Exception)
            {
                Log.i("Ayush","Error e: ${e.message}")
            }
        }
    }

    fun getWholeData()
    {
        Log.i("Ayush","This method was called.")
        viewModelScope.launch ( Dispatchers.IO )
        {
            try{
                databaseObject.getAll().collect{extra.postValue(it);Log.i("Ayush","data collected is ${it.size}" +
                        "and the size of extra is ${extra.value?.size?:-1}")}
            }
            catch(e:Exception)
            {
                Log.i("Ayush","Error whole: ${e.message}")
            }
        }
    }

    fun deleteData(data:EntityClass) {
        viewModelScope.launch(Dispatchers.IO) {
            databaseObject.deleteMessage(data)
        }
    }

}


