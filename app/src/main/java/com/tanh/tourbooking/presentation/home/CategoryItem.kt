package com.tanh.tourbooking.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.tanh.tourbooking.data.model.dto.tour.Category
import com.tanh.tourbooking.ui.theme.TourBookingTheme

@Composable
fun CategoryItem(
    category: Category,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFECECEC))
            .padding(0.dp)
            .padding(4.dp)

    ) {
        Column(
            modifier = Modifier.align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = category.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .height(80.dp)
                    .width(100.dp)
            )

            Text(
                text = category.name,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
//            modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewCategoryItem(modifier: Modifier = Modifier) {
    TourBookingTheme {
        CategoryItem(
            category = Category(
                name = "Adventure",
                image = "https://i.ibb.co/hJFkj2nC/ninhbinh1.jpg"
            )
        )
    }
}