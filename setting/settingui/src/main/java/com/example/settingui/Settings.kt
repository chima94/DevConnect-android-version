package com.example.settingui

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material.Switch
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.funkymuse.composed.core.context
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun Settings(){

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = rememberInsetsPaddingValues(
            insets = LocalWindowInsets.current.statusBars
        )
    ){
        item {
            DarkTheme(){}
        }
        item {
            Github()
        }
        item {
            License()
        }
        item {
            VersionNumber()
        }
        item {
            Logout()
        }
        item {
            DeleteAccount()
        }
    }
}


@Composable
fun VersionNumber() {
    TitleWithSubtitleTextItem(
        titleText = stringResource(id = com.example.strings.R.string.version),
        subtitleText = context.getVersionName()
    )
}


fun Context.getVersionName(): String = packageManager.getPackageInfo(packageName, 0).versionName


@Composable
fun License() {
    TitleWithSubtitleTextItem(
        titleText = stringResource(com.example.strings.R.string.license_title),
        subtitleText = stringResource(com.example.strings.R.string.license)
    )
}

@Composable
private fun TitleWithSubtitleTextItem(titleText: String, subtitleText: String) {
    SettingsItems(
        modifier = Modifier
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Text(
                text = titleText, modifier =
                Modifier.padding(horizontal = 8.dp)
            )

            Text(
                text = subtitleText,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(top = 4.dp),
                fontSize = 12.sp, color = Color.Gray
            )

        }
    }
}


@Composable
fun Github() {
    val context = LocalContext.current
    SettingsItems(modifier = Modifier
        .clickable {

        }
        .padding(vertical = 8.dp)) {
        Text(
            text = stringResource(id = com.example.strings.R.string.github_page),
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
}



@Composable
fun DeleteAccount() {
    val context = LocalContext.current
    SettingsItems(modifier = Modifier
        .clickable {

        }
        .padding(vertical = 8.dp)) {
        Text(
            text = stringResource(com.example.strings.R.string.delete_account),
            modifier = Modifier.padding(horizontal = 8.dp),
            color = Color.Red
        )
    }
}



@Composable
fun Logout() {
    val context = LocalContext.current
    SettingsItems(modifier = Modifier
        .clickable {

        }
        .padding(vertical = 8.dp)) {
        Text(
            text = stringResource(id = com.example.strings.R.string.logout),
            modifier = Modifier.padding(horizontal = 8.dp),
        )
    }
}



@Composable
fun DarkTheme(
    darkThemeFlow: StateFlow<Boolean> = MutableStateFlow(false),
    changeTheme: (theme: Boolean) -> Unit
){
    val condition = darkThemeFlow.collectAsState().value
    Switch(text = com.example.strings.R.string.dark_theme, callBack = changeTheme , condition = condition)
}



@Composable
fun SettingsItems(
    modifier : Modifier = Modifier,
    item: @Composable (BoxScope) -> Unit
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp)
    ){
        item(this)
    }
}



@Composable
private fun Switch(
    @StringRes text: Int,
    callBack: (condition: Boolean) -> Unit,
    condition: Boolean
){
    SettingsItems(
        modifier = Modifier
            .clickable { callBack(!condition) }
            .padding(top = 8.dp)
    ) {
        CheckBoxWithText(
            text = text,
            isChecked = condition,
            checkChanged ={
                callBack(it)
            }
        )
    }
}



@Composable
fun CheckBoxWithText(
    @StringRes text: Int,
    isChecked: Boolean,
    checkChanged: (Boolean) -> Unit
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ){
        Text(
            text = stringResource(id = text),
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .align(Alignment.CenterStart)
                .padding(start = 8.dp),
            textAlign = TextAlign.Start
        )
        
        Switch(
            checked = isChecked,
            onCheckedChange = checkChanged,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(start = 8.dp, end = 4.dp)
        )
    }
}