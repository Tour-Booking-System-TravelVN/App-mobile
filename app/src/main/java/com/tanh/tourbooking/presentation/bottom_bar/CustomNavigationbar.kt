package com.tanh.tourbooking.presentation.bottom_bar

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CustomBottomNavigationBar(
    modifier: Modifier = Modifier
) {

    Row(
        modifier = Modifier.fillMaxWidth().padding(
            horizontal = 16.dp,
            vertical = 8.dp
        ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {


    }

}

data class NavigationBarItemData(
    val title: String? = null,
    val route: String? = null,
    val selectedIcon: Icon,
    val unselectedIcon: Icon,
    val hasNews: Boolean = false,
    val badgeCount: Int? = null
)