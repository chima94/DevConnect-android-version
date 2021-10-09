package com.example.profilecomponent

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntSizeAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.Facebook
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.funkymuse.composed.core.textStyle
import com.google.accompanist.insets.navigationBarsPadding
import kotlin.math.ceil
import kotlin.math.max


val imageUri = "https://images.unsplash.com/photo-1628373383885-4be0bc0172fa?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1301&q=80"
val initialimageFloat = 170f

fun Modifier.horizontalGradientBackground(
    colors: List<Color>
) = gradientBackground(colors) { gradientColors, size ->
    Brush.horizontalGradient(
        colors = gradientColors,
        startX = 0f,
        endX = size.width
    )
}

fun Modifier.gradientBackground(
    colors: List<Color>,
    brushProvider: (List<Color>, Size) -> Brush
): Modifier = composed {
    var size by remember { mutableStateOf(Size.Zero) }
    val gradient = remember(colors, size) { brushProvider(colors, size) }
    drawWithContent {
        size = this.size
        drawRect(brush = gradient)
        drawContent()
    }
}


@Composable
fun ProfileUI() {
    val scrollState = rememberScrollState(0)
    Scaffold(
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .semantics { testTag = "Profile Screen" }
            ){
                TopBackground()
                TopAppBarView(scroll = scrollState.value.toFloat())
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(state = scrollState)
                ){
                    Spacer(modifier = Modifier.height(100.dp))
                    TopScrollingContent(scrollState = scrollState)
                    BottomScrollingContent()
                }
            }
        }
    )
}


@Composable
fun TopScrollingContent(scrollState: ScrollState){
    val visibilityChangeFloat = scrollState.value > initialimageFloat - 20
    Row{
        AnimatedImage(scroll = scrollState.value.toFloat())
        Column(
            modifier = Modifier
                .padding(start = 8.dp, top = 48.dp)
                .alpha(animateFloatAsState(if (visibilityChangeFloat) 0f else 1f).value)
        ) {
            Text(
                text = "Chima Nwakigwe",
                style = MaterialTheme.typography.h6.copy(fontSize = 18.sp),
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "Android developer at Nodexihub",
                style = MaterialTheme.typography.h6.copy(fontSize = 14.sp),
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
    }
}



@Composable
fun AnimatedImage(scroll: Float){
    val dynamicAnimationSizeValue = (initialimageFloat - scroll).coerceIn(36f, initialimageFloat)
    Image(
        painter = rememberImagePainter(data = imageUri),
        contentScale = ContentScale.Crop,
        contentDescription = null,
        modifier = Modifier
            .padding(start = 16.dp)
            .size(animateDpAsState(Dp(dynamicAnimationSizeValue)).value)
            .clip(CircleShape)
    )
}





@Composable
private fun TopBackground(){
    val gradient = listOf(MaterialTheme.colors.primaryVariant, MaterialTheme.colors.secondary)
    Spacer(
        modifier = Modifier
            .height(150.dp)
            .fillMaxWidth()
            .horizontalGradientBackground(gradient)
    )
}





@Composable
fun TopAppBarView(scroll: Float){
    if(scroll > initialimageFloat + 5){
        TopAppBar(
            title = {
                Text(text = "Chima Nwakigwe")
            },
            navigationIcon = {
                Image(
                    painter = rememberImagePainter(data = imageUri),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(vertical = 4.dp, horizontal = 8.dp)
                        .size(32.dp)
                        .clip(CircleShape)
                )
            },
            actions = {
                //Icon()
            },
            backgroundColor = MaterialTheme.colors.primaryVariant
        )
    }
}


@Composable
fun BottomScrollingContent(){
    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.surface)
            .padding(8.dp)
    ) {
        SocialRow()
        Text(
            text = stringResource(com.example.strings.R.string.about_me),
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(start = 8.dp, top = 12.dp)
        )
        Divider(modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp))
        Text(
            text = stringResource(id = com.example.strings.R.string.about_section),
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(8.dp)
        )
        SkillSection()
        Text(
            text = stringResource(com.example.strings.R.string.more_info),
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(start = 8.dp, top = 16.dp)
        )
        Divider(modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp))
        EducationSection()
        ExperienceSection()
        ProjectSection()

    }
}



@Composable
fun EducationSection(){
    Box(modifier = Modifier
        .fillMaxWidth()
        .size(50.dp)
    ){
        Card(elevation = 1.dp, modifier = Modifier.padding(start = 8.dp, end = 8.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(com.example.strings.R.string.education),
                    style = MaterialTheme.typography.body1,
                )
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.ArrowRight, contentDescription = null)
                }
            }
        }
    }

}




@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExperienceSection(){
    Box(modifier = Modifier
        .fillMaxWidth()
        .size(50.dp)){
        Card(elevation = 1.dp, modifier = Modifier.padding(start = 8.dp, end = 8.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(com.example.strings.R.string.experience),
                    style = MaterialTheme.typography.body1,
                )
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.ArrowRight, contentDescription = null)
                }
            }
        }
    }
}


@Composable
fun ProjectSection(){
    Box(modifier = Modifier
        .fillMaxWidth()
        .size(150.dp)
    )
    {
        Card(elevation = 1.dp, modifier = Modifier.padding(start = 8.dp, end = 8.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(com.example.strings.R.string.projects),
                    style = MaterialTheme.typography.body1,
                )
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.ArrowRight, contentDescription = null)
                }
            }
        }
    }
}






@Composable
fun SkillSection(){
    val skills = listOf<String>("Kotlin", "Java", "Nodejs", "Jetpack Compose", "Python", "machine learning")
    Text(
        text = stringResource(com.example.strings.R.string.my_skills),
        style = MaterialTheme.typography.h6,
        modifier = Modifier.padding(start = 8.dp, top = 16.dp)
    )
    Divider(modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp))
    StaggeredGrid(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 8.dp)
    ) {
        skills.forEach { skill ->
            SkillTag(text = skill)
        }
    }
}



@Composable
fun SkillTag(text: String){
    val tagModifier = Modifier
        .padding(4.dp)
        .clip(RoundedCornerShape(4.dp))
        .background(MaterialTheme.colors.primaryVariant.copy(alpha = 0.2f))
        .padding(horizontal = 8.dp, vertical = 4.dp)
    
    Text(
        text = text,
        color = MaterialTheme.colors.secondary,
        modifier = tagModifier,
        style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold)
    )
}


@Composable
fun SocialRow(){
    Card(elevation = 5.dp, modifier = Modifier.padding(8.dp)){
        val context = LocalContext.current
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 16.dp)
        ){
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = com.example.drawables.R.drawable.ic_github_square_brands),
                    contentDescription = stringResource(com.example.strings.R.string.github)
                )
            }


            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = com.example.drawables.R.drawable.ic_instagram),
                    contentDescription = stringResource(com.example.strings.R.string.instagram)
                )
            }


            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = com.example.drawables.R.drawable.ic_linkedin_brands),
                    contentDescription = stringResource(id = com.example.strings.R.string.linkedin)
                )
            }


            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = com.example.drawables.R.drawable.ic_twitter_square_brands),
                    contentDescription = stringResource(com.example.strings.R.string.twitter),

                )
            }

            IconButton(
                onClick = { /*TODO*/ },
            ) {
                Icon(
                    imageVector = Icons.Filled.Facebook,
                    contentDescription = stringResource(com.example.strings.R.string.facebook),
                    modifier = Modifier
                        .size(45.dp)
                    )
            }
        }
    }
}





@Composable
private fun StaggeredGrid(
    modifier: Modifier = Modifier,
    rows: Int = 3,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        val rowWidths = IntArray(rows) { 0 } // Keep track of the width of each row
        val rowHeights = IntArray(rows) { 0 } // Keep track of the height of each row

        // Don't constrain child views further, measure them with given constraints
        val placeables = measurables.mapIndexed { index, measurable ->
            val placeable = measurable.measure(constraints)

            // Track the width and max height of each row
            val row = index % rows
            rowWidths[row] += placeable.width
            rowHeights[row] = max(rowHeights[row], placeable.height)

            placeable
        }

        // Grid's width is the widest row
        val width = rowWidths.maxOrNull()?.coerceIn(constraints.minWidth, constraints.maxWidth)
            ?: constraints.minWidth
        // Grid's height is the sum of each row
        val height = rowHeights.sum().coerceIn(constraints.minHeight, constraints.maxHeight)

        // y co-ord of each row
        val rowY = IntArray(rows) { 0 }
        for (i in 1 until rows) {
            rowY[i] = rowY[i - 1] + rowHeights[i - 1]
        }
        layout(width, height) {
            // x co-ord we have placed up to, per row
            val rowX = IntArray(rows) { 0 }
            placeables.forEachIndexed { index, placeable ->
                val row = index % rows
                placeable.place(
                    x = rowX[row],
                    y = rowY[row]
                )
                rowX[row] += placeable.width
            }
        }
    }
}
