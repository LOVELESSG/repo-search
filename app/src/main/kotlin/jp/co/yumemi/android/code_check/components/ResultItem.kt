package jp.co.yumemi.android.code_check.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import jp.co.yumemi.android.code_check.Item
import jp.co.yumemi.android.code_check.navigation.Screen
import jp.co.yumemi.android.code_check.ui.theme.AppTheme

@Composable
fun ResultItem(
    item: Item,
    navController: NavController,
    selectTargetItem: (Item) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable {
                selectTargetItem(item)
                navController.navigate(Screen.RepoDetails.route)
            }
            .fillMaxWidth()
            .padding(16.dp, 8.dp, 16.dp, 0.dp)
    ) {
        AsyncImage(
            model = item.ownerIconUrl,
            contentDescription = "Owner Icon",
            alignment = Alignment.Center,
            modifier = Modifier
                .size(64.dp),
        )
        Text(
            text = item.name,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}


@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ResultItemPreview() {
    val testItem = Item(
        name = "Kotlin/Project",
        ownerIconUrl = "",
        language = "Kotlin",
        stargazersCount = 10390L,
        watchersCount = 2342L,
        forksCount = 8971L,
        openIssuesCount = 452L,
    )
    AppTheme {
        ResultItem(
            item = testItem,
            navController = rememberNavController(),
            selectTargetItem = {}
        )
    }
}