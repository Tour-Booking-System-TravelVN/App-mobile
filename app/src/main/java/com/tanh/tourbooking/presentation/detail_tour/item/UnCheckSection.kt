package com.tanh.tourbooking.presentation.detail_tour.item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tanh.tourbooking.R
import com.tanh.tourbooking.ui.theme.dimens
import com.tanh.tourbooking.ui.theme.greenItem
import com.tanh.tourbooking.ui.theme.lightGray
import com.tanh.tourbooking.util.toStringList

@Composable
fun UnCheckSection(
    modifier: Modifier = Modifier,
    uncheck: String
) {

    val convertCheck = uncheck.toStringList()
    Column {
        convertCheck.forEach { title ->
            UnCheckItem(title = title)
            Spacer(Modifier.height(MaterialTheme.dimens.small1))
        }
    }

}

@Composable
fun UnCheckItem(
    modifier: Modifier = Modifier,
    title: String
) {

    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.uncheck),
            contentDescription = "uncheck",
            tint = lightGray,
            modifier = Modifier.size(20.dp)
        )
        Spacer(Modifier.width(MaterialTheme.dimens.small2))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium
        )
    }

}