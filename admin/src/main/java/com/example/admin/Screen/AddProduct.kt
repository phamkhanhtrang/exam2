package com.example.admin.Screen

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.admin.DestinationScreen
import com.example.admin.Model
import com.example.admin.ui.theme.Purple80
import com.example.admin.ui.theme.PurpleGrey80
import com.example.admin.ui.theme.navigateTo


@Composable
fun AddProduct(navController: NavController, vm:Model){

    var uri by remember{
        mutableStateOf<Uri?>(null)
    }

    val productPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            uri = it
        }
    )

    val context = LocalContext.current
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = PurpleGrey80),
         horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Điền thông tin sản phẩm",
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif
        )
        val nameProductState = remember {
            mutableStateOf(TextFieldValue( ))
        }
        val costProductState = remember {
            mutableStateOf(TextFieldValue())
        }
        OutlinedTextField(
            value = nameProductState.value,
            onValueChange = {
                nameProductState.value=  it
            },
            label = { Text(text = "Nhập tên sản phẩm ")})
        OutlinedTextField(
            value = costProductState.value,
            onValueChange = {
                costProductState.value=  it
            },
            label = { Text(text = "Nhập giá sản phẩm ")})
        Spacer(modifier = Modifier.height(12.dp))
        AsyncImage(model = uri, contentDescription = null, modifier = Modifier.size(200.dp))

        Button(onClick = {
            productPicker.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }, colors = ButtonDefaults.buttonColors(Purple80),
            modifier = Modifier.width(260.dp).height(40.dp)) {
            Text("Chọn ảnh sản phẩm ",
                fontSize = 17.sp
                ,color = Color.Black,
                modifier = Modifier.background(color = Purple80)
                )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = {
            uri?.let {
                vm.uploadToStorage(uri = it, context = context, type = "image") { imageUrl ->
                    // Sau khi tải ảnh lên và nhận được đường dẫn, gọi hàm addData với đường dẫn này

                    vm.addData(
                        nameProductState.value.text,
                        costProductState.value.text,
                        imageUrl,
                        context
                    )
                }
            }

        }, colors = ButtonDefaults.buttonColors(Purple80),modifier = Modifier.width(260.dp).height(40.dp)) {
            Text(text = "Thêm sản phẩm",
                color = Color.Black,
                fontSize = 17.sp
               )
        }

        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = {  },
            colors = ButtonDefaults.buttonColors(Purple80),
            modifier = Modifier.width(260.dp).height(40.dp)
            ) {
            Text(text = "Danh sách ",
                color = Color.Black,
                fontSize = 17.sp,
                modifier = Modifier.clickable {
                    navigateTo(navController, DestinationScreen.Display.route)

                })
        }

    }

}
