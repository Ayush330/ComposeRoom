package com.example.composeroom

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composeroom.RoomDatabase.DatabaseClass
import com.example.composeroom.RoomDatabase.EntityClass
import com.example.composeroom.RoomDatabase.MessageDAO
import com.example.composeroom.ui.theme.ComposeRoomTheme
import kotlinx.coroutines.*


class MainActivity : ComponentActivity()
{
    var Name = mutableStateOf("")
    private lateinit var nameArray:Array<String>
    private lateinit var dao:MessageDAO
    val myModel: MessageViewModel by viewModels()
    private lateinit var tempStorage:MutableState<List<EntityClass>>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeRoomTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = Color.Yellow,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()) {
                    Greeting(Name.value)
                    //listAllMessage()
                }
            }
        }




        val database = DatabaseClass.getDatabase(this@MainActivity)
        dao = database.messageDAO()


        myModel.databaseInstance = database
        myModel.databaseObject = dao

        /**Observing the data**/

        myModel.getWholeData()

        tempStorage = mutableStateOf(myModel.extra.value?: listOf(EntityClass(0,"Empty","Empty")))

        myModel.extra.observe(this){ newMessage->
            Log.i("Ayush","Data Changed ${newMessage.size}")
            tempStorage.value = newMessage
        }

    }




    @Composable
    fun Greeting(name: String) {
        Column(modifier = Modifier
            .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
               verticalArrangement = Arrangement.Top) {
            //Text(text = "Hello $name!",modifier = Modifier.padding(24.dp))


            //remember { Name }

            OutlinedTextField(value = Name.value,
                          onValueChange = {Name.value = it },
                label = {Text("label")})

            Spacer(modifier=Modifier.padding(15.dp))

            Button(onClick = {
                myModel.insert(EntityClass(0,Name.value,"Ayush"))
                Name.value = ""
            }) {
                Text("Change name")

            }

            Spacer(modifier = Modifier.padding(15.dp))



            if(tempStorage.value.isEmpty())
            {
                Text("Nothing to show here")
            }

            else
            {
                listAllMessage()
            }


        }

    }

    @Composable
    fun listAllMessage()
    {
        Log.i("Ayush","Previewing  Here ${tempStorage.value.size}")
            LazyColumn(Modifier.fillMaxSize()) {

                items(tempStorage.value) { item ->
                    Card(modifier = Modifier.padding(10.dp), elevation = 8.dp) {
                        Row(Modifier.padding(end = 5.dp),verticalAlignment = Alignment.CenterVertically) {
                            Text(text = item.id.toString(), fontSize = 25.sp,modifier = Modifier.padding(10.dp))
                            Text(text = item.message, fontSize = 18.sp,modifier = Modifier.padding(10.dp))
                            Button(onClick = { myModel.deleteData(item) }) {
                                Text(text="Delete")
                            }
                        }
                    }
                }
            }

    }


    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        ComposeRoomTheme {
            Greeting("Android")
        }
    }
}