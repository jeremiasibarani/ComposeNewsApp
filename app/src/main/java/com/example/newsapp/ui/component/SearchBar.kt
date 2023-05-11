package com.example.newsapp.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newsapp.R

@Composable
fun SearchBar(
    modifier : Modifier = Modifier,
    placeHolderText : String,
    onSearchClicked : (String) -> Unit = {},
    @DrawableRes iconLeading : Int,
) {

    var query by remember{
        mutableStateOf("")
    }
    var showClearIcon by remember{
        mutableStateOf(false)
    }

    showClearIcon = query.isNotEmpty()

    TextField(
        value = query,
        onValueChange = {newQuery ->
            query = newQuery
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface,
            disabledIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        leadingIcon = {
            Icon(
                painterResource(id = iconLeading),
                contentDescription = null
            )
        },
        placeholder = {
            Text(text = placeHolderText)
        },
        trailingIcon = {
            if(showClearIcon){
                IconButton(onClick = { query = "" }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = null
                    )
                }
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                if(query.isNotEmpty()){
                    onSearchClicked(query)
                }
            }
        ),
        modifier = modifier
            .padding(10.dp)
            .fillMaxWidth()
            .heightIn(40.dp)
            .clip(RoundedCornerShape(10.dp))
            .border(width = 1.dp, color = Color.DarkGray, shape = RoundedCornerShape(10.dp))
    )
}

@Preview
@Composable
fun SearchBarPreview() {
    MaterialTheme{
        Surface(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
        ) {
            SearchBar(
                placeHolderText = stringResource(id = R.string.placeholder_search_bar_text),
                iconLeading = R.drawable.search_icon
            )
        }
    }
}