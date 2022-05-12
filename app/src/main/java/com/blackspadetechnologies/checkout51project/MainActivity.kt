package com.blackspadetechnologies.checkout51project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.blackspadetechnologies.checkout51project.info.Offer
import com.blackspadetechnologies.checkout51project.ui.theme.Checkout51ProjectTheme
import java.math.RoundingMode
import java.text.DecimalFormat

class MainActivity : ComponentActivity() {
    val c51ViewModel: C51ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Checkout51ProjectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    CheckoutScreen()
                }
            }
        }
        c51ViewModel.fetchCheckout(this)
    }
}

@Preview
@Composable
fun CheckoutScreen(viewModel: C51ViewModel = viewModel()) {

    val c51 = viewModel.c51StateData.collectAsState().value

    Checkout51ProjectTheme {
        Scaffold(bottomBar = {
            BottomAppBar(
                backgroundColor = MaterialTheme.colors.primary,
                elevation = 0.dp
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier,
                        text = "Checkout 51",
                        fontSize = 35.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

        }) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(bottom = 50.dp),
                color = MaterialTheme.colors.primaryVariant
            ) {
                ListOfCards(checkouts = c51)
            }
        }

    }

}

@Composable
fun ListOfCards(checkouts: List<Offer>) {
    LazyColumn {
        items(checkouts) { checkout ->
            CheckoutCard(checkout = checkout)
        }
    }
}

@Composable
private fun CheckoutCard(checkout: Offer) {
    val df = DecimalFormat("0.00")
    val newNumber = df.format(checkout.cash_back)

    Card(
        modifier = Modifier
            .width(500.dp)
            .requiredHeight(200.dp)
            .padding(16.dp),
        backgroundColor = MaterialTheme.colors.primary
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            ImageHolder(checkout.image_url)
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {
                DescHolder(title = "Name", field = checkout.name)
                DescHolder(title = "Cash Back", field = "$${newNumber}")
            }

        }
    }
}

@Composable
private fun DescHolder(title: String, field: Any) {
    Column(
        modifier = Modifier
            .padding(start = 16.dp)
    ) {
        Text(
            text = "$title:",
            modifier = Modifier,
            fontWeight = FontWeight.Bold,
            fontSize = 10.sp
        )
        Text(
            text = field.toString(),
            modifier = Modifier.padding(bottom = 10.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
    }
}


@OptIn(ExperimentalCoilApi::class)
@Composable
private fun ImageHolder(image: String) {
    Surface(
        modifier = Modifier
            .size(150.dp),
        border = BorderStroke(
            width = 10.dp,
            color = MaterialTheme.colors.primaryVariant
        ),
        elevation = 6.dp,
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
    ) {
        val painter = rememberImagePainter(data = image)
        val paintState = painter.state
        Image(
            painter = painter,
            contentDescription = "Items",
            modifier = Modifier.size(135.dp),
            contentScale = ContentScale.Crop
        )
        if (paintState is ImagePainter.State.Loading) {
            CircularProgressIndicator()
        }
    }
}