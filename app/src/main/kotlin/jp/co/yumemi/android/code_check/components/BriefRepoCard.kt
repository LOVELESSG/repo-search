package jp.co.yumemi.android.code_check.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import jp.co.yumemi.android.code_check.data.room.models.VisitHistory
import jp.co.yumemi.android.code_check.ui.theme.AppTheme
import java.time.LocalDateTime

@Composable
fun BriefRepoCard(
    visitHistoryItem: VisitHistory,
    icon: ImageVector,
    iconButtonOperation: (VisitHistory) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp, 16.dp, 0.dp)
            .clip(shape = RoundedCornerShape(12.dp))
            .background(color = MaterialTheme.colorScheme.surfaceContainerHighest)
    ) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.size(64.dp)
        ) {
            AsyncImage(
                model = visitHistoryItem.ownerIconUrl,
                contentDescription = "Owner Icon",
                alignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1.5f),
            )
        }

        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(8f)
        ) {
            Text(
                text = visitHistoryItem.name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = visitHistoryItem.name,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        IconButton(
            modifier = Modifier
                .padding(8.dp),
            onClick = {iconButtonOperation(visitHistoryItem)}
        ) {
            Icon(imageVector = icon, contentDescription = null)
        }
    }
}


@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Night Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BriefRepoCardPreview() {
    AppTheme {
        val visitHistorySample = VisitHistory(
            name = "Test Repo",
            ownerIconUrl = "",
            language = "Kotlin",
            stargazersCount = 1242L,
            watchersCount = 21112L,
            forksCount = 5345L,
            openIssuesCount = 453L,
            addTime = LocalDateTime.now()
        )
        BriefRepoCard(
            visitHistoryItem = visitHistorySample,
            icon = Icons.Default.Delete,
            iconButtonOperation = {}
        )
    }
}