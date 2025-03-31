package com.tanh.tourbooking.presentation.bottom_bar

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.compose.ui.util.fastForEachIndexed
import androidx.navigation.NavController
import com.tanh.tourbooking.ui.theme.TourBookingTheme
import com.tanh.tourbooking.util.Good

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun CustomBottomNavigationBar(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var selectedIndex by remember { mutableIntStateOf(0) }

    val greyToWhiteGradient = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFFE2E2E6),
            Color(0xFFF5F5F5),
            Color(0xFFFFFFFF)
        )
    )

    val list by remember { mutableStateOf(Good.listBottomBar) }

    val density = LocalDensity.current
    val parentWidth = remember { mutableStateOf(0.dp) }


        Box (
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .shadow(
                    elevation = 10.dp, shape = RoundedCornerShape(
                        topStart = 26.dp,
                        topEnd = 26.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp
                    )
                )
                .clip(
                    RoundedCornerShape(
                        topStart = 26.dp,
                        topEnd = 26.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp
                    )
                )
                .background(greyToWhiteGradient)
                .onGloballyPositioned { layoutCoordinates ->    //get bottom bar width
                    parentWidth.value = with(density) { layoutCoordinates.size.width.toDp() }
                }
        ) {
            val itemWidth = parentWidth.value / list.size
            val indicatorWidth = 40.dp

            val indicatorOffset by animateDpAsState(
                targetValue = selectedIndex * itemWidth + (itemWidth - indicatorWidth) / 2,
                label = "Indicator Animation"
            )
            //indicator
            Box(
                modifier = Modifier
                    .offset(x = indicatorOffset)   //locate indicator
                    .width(indicatorWidth)
                    .height(3.dp)
                    .align(Alignment.TopStart)
                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(50))
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                list.fastForEachIndexed { index, navItem ->
                    BottomNavigationBarItem(
                        isClicked = index == selectedIndex,
                        item = navItem,
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 8.dp)
                            .clickable {
                                selectedIndex = index
                                navController.navigate(navItem.route)
                            }
                    )
                }
            }
        }
}

data class NavigationBarItemData(
    val title: String? = null,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean = false,
    val badgeCount: Int? = null
)

@Preview(showSystemUi = true)
@Composable
fun PreviewCustomBottomNavigationBar(modifier: Modifier = Modifier) {
    TourBookingTheme {

    }
}