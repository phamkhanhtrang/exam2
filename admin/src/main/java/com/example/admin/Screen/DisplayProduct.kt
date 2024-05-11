package com.example.admin.Screen

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.admin.Data.ProductData
import com.example.admin.DestinationScreen
import com.example.admin.Model
import com.example.admin.ui.theme.Purple80
import com.example.admin.ui.theme.PurpleGrey80
import com.example.admin.ui.theme.navigateTo

@Composable
fun DisplayProduct(navController: NavController,vm:Model){

    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.background(color = PurpleGrey80)) {
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = { /*TODO*/ },colors = ButtonDefaults.buttonColors(Purple80),
            modifier = Modifier
                .width(260.dp)
                .height(40.dp)) {
            Text(text = "Thêm sản phẩm",fontSize = 17.sp,
                modifier = Modifier.clickable {
                    navigateTo(navController, DestinationScreen.Add.route)

                })
        }
        Text(text = "Danh sách sản phẩm",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif)
        Spacer(modifier = Modifier.height(12.dp))

        DisplayProduct(vm  )

    }
}

@SuppressLint("UnrememberedMutableState", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DisplayProduct(vm: Model,   ){

    val productListState = remember {
        mutableStateOf(emptyList<ProductData>())
    }

    LaunchedEffect(Unit) {
        vm.fetchProductData { productList ->
            productListState.value = productList
        }
    }

    Scaffold(
        content = {
            Column {
                LazyColumn {
                    items(productListState.value) { product ->
                        ProductItem(
                              vm = vm, product = product

                        )
                    }
                }
            }
        }
    )
}

@SuppressLint("UnrememberedMutableState")
 @Composable
fun ProductItem(product: ProductData, vm: Model) {
    val context = LocalContext.current

    var name by rememberSaveable { mutableStateOf(product.name ?: "") }
    var cost by rememberSaveable { mutableStateOf(product.cost ?: "") }

    Box(modifier = Modifier.background(color = Color.White)) {
        ConstraintLayout {
            val (imageProduct, textName, textCost, iconUpdate, iconDelete) = createRefs()

            val imagePainter = rememberImagePainter(data = product.imageUrl)
            Image(
                painter = imagePainter,
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .constrainAs(imageProduct) {
                        top.linkTo(parent.top, margin = 10.dp)
                        start.linkTo(parent.start)
                    }
            )

            TextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier
                    .constrainAs(textName) {
                        top.linkTo(parent.top, margin = 10.dp)
                        start.linkTo(imageProduct.end)
                    }
                    .width(190.dp)
            )

            TextField(
                value = cost,
                onValueChange = { cost = it },
                modifier = Modifier
                    .constrainAs(textCost) {
                        top.linkTo(parent.top, margin = 70.dp)
                        start.linkTo(imageProduct.end)
                    }
                    .width(190.dp)
            )

            Icon(
                Icons.Default.Delete,
                contentDescription = null,
                modifier = Modifier
                    .constrainAs(iconDelete) {
                        top.linkTo(parent.top, margin = 10.dp)
                        start.linkTo(textName.end, margin = 10.dp)
                    }
                    .clickable {
                        product.productId?.let {
                            vm.deleteProduct(it)
                        }
                    }
            )

            Icon(
                Icons.Default.Edit,
                contentDescription = null,
                modifier = Modifier
                    .constrainAs(iconUpdate) {
                        top.linkTo(parent.top, margin = 70.dp)
                        start.linkTo(textCost.end, margin = 10.dp)
                    }
                    .clickable {
                        product.productId?.let {
                            vm.updateProduct(
                                productId = it,
                                updatedCost = cost,
                                updatedName = name,
                                updatedImageUrl = product.imageUrl ?: "",
                                context = context
                            )
                        }
                    }
            )
        }
        Divider()
    }
}


