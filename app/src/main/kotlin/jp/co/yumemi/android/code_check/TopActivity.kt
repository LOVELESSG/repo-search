/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import jp.co.yumemi.android.code_check.ui.homepage.HomepageScreen
import jp.co.yumemi.android.code_check.ui.theme.AppTheme
import java.util.*

class TopActivity : ComponentActivity() {

    companion object {
        lateinit var lastSearchDate: Date
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                HomepageScreen()
            }
        }
    }

}

@Composable
fun MyCompose() {
    Text("hello compose")
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun TestPreview() {
    AppTheme {
        MyCompose()
    }
}
