package com.tanh.tourbooking.presentation.bottom_bar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tanh.tourbooking.ui.theme.TourBookingTheme

@Composable
fun BottomNavigationBarItem(
    isClicked: Boolean,
    item: NavigationBarItemData,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = if (isClicked) item.selectedIcon else item.unselectedIcon,
            contentDescription = item.title,
            tint = if (isClicked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.size(40.dp)
        )
//        Text(
//            text = item.title ?: "",
//            color = if (isClicked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer,
//
//        )
    }
}

@Preview
@Composable
fun PreviewBottomNavigationBarItem(modifier: Modifier = Modifier) {

    TourBookingTheme {
        BottomNavigationBarItem(
            item = NavigationBarItemData(
                title = "Home",
                route = "home",
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home,
                hasNews = true,
                badgeCount = 5
            ),
            isClicked = true
        )
    }

}
