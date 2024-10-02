package com.sujay.shoppinglistapp

import android.app.AlertDialog
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ShoppingItem(var id:Int,
                        var name:String,
                        var quantity:Int,
                        var isEditing:Boolean=false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListApp(){
    var sItems by remember { mutableStateOf(listOf<ShoppingItem>()) }
    var showDialogue by remember{ mutableStateOf(false)}
    var itemName by remember { mutableStateOf("")}
    var itemQuantity by remember { mutableStateOf("")}
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { showDialogue = true },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Add Item")
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(sItems) {
                item->
                if(item.isEditing) {
                    ShoppingListEditor(item = item, onEditComplete ={
                        editedName,editedQuantity ->
                        sItems=sItems.map{it.copy(isEditing = false)}
                        var editedItem=sItems.find{it.id==item.id}
                        editedItem?.let {
                            it.name=editedName
                            it.quantity=editedQuantity
                        }
                    } )
                } else  {
                    ShoppingListItem(
                        item =item ,
                        onEditClick = {
                            sItems=sItems.map{it.copy(isEditing = it.id==item.id)}
                                      },
                        onDeleteClick = {
                        sItems=sItems-item
                    })
                }
            }

        }}

        if (showDialogue) {
            AlertDialog(onDismissRequest = { showDialogue = false },
                confirmButton = {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ){
                                    Button(onClick = {
                                        if(itemName.isNotBlank()) {
                                            val newItem = ShoppingItem(
                                                id= sItems.size+1,
                                                name=itemName,
                                                quantity = itemQuantity.toInt(),
                                                isEditing = false
                                            )
                                            sItems = sItems+newItem
                                            itemName=""
                                            itemQuantity=""
                                            showDialogue=false
                                        }
                                    }) {
                                        Text("Add")
                                    }
                                    Button(onClick = {showDialogue=false}) {
                                        Text("Cancel")
                                    }
                                }
                },
                title = { Text("Add Shopping Item") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = itemName,
                            onValueChange = { itemName = it },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        )
                        OutlinedTextField(
                            value = itemQuantity,
                            onValueChange = { itemQuantity = it },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        )
                    }
                }
            )

        }
    }


@Composable
fun ShoppingListEditor(item: ShoppingItem,onEditComplete: (String,Int) -> Unit) {
    var editedName by remember { mutableStateOf(item.name) }
    var editedQuantity by remember { mutableStateOf(item.quantity.toString()) }
    var isEditing by remember { mutableStateOf(item.isEditing) }

    Row(modifier = Modifier
        .fillMaxWidth()
//        .background(Color.White)
        .padding(8.dp)
//        .background(Color.Black)
        .border(
            border = BorderStroke(3.dp, color = Color.Black),
            shape = RoundedCornerShape(20)),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically


    ){
        Column() {
            BasicTextField(
//                textStyle = LocalTextStyle.current.copy(color = Color.White),
                textStyle= androidx.compose.ui.text.TextStyle(fontSize = 18.sp),
                value = editedName,
                onValueChange = { editedName = it },
                singleLine = true,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(8.dp),

            )
//        }
//        Column(){
            BasicTextField(
//                textStyle = LocalTextStyle.current.copy(color = Color.White),
                textStyle= androidx.compose.ui.text.TextStyle(fontSize = 18.sp),
                value=editedQuantity, onValueChange = {editedQuantity=it}, singleLine = true, modifier = Modifier
                .wrapContentSize()
                .padding(8.dp))
        }
        Button(onClick = {isEditing=false; onEditComplete(editedName,editedQuantity.toIntOrNull()?:1)}){
            Text("Save")
        }
    }
}

@Composable
fun ShoppingListItem(
    item: ShoppingItem,
    onEditClick:()->Unit,
    onDeleteClick:()->Unit,
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .border(
                    border = BorderStroke(3.dp, color = Color.Black),
                    shape = RoundedCornerShape(20)
                ),
            horizontalArrangement = Arrangement.SpaceAround

        ){
            Column(){
                Text(text = item.name, modifier = Modifier.padding(8.dp))
                Text(text = "Qty : ${item.quantity}", modifier = Modifier.padding(8.dp))
            }

            Row(modifier = Modifier.padding(8.dp)) {
                IconButton(onClick = {onEditClick()}) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = null)
                }
                IconButton(onClick = {onDeleteClick()}) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                }
            }

        }

    }
