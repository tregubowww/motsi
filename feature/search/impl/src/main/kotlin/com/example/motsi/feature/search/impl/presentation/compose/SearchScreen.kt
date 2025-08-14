package com.example.motsi.feature.search.impl.presentation.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.motsi.core.common.models.presentation.LoadingState
import com.example.motsi.core.navigation.presentation.compose.LocalAppNavController
import com.example.motsi.core.ui.R
import com.example.motsi.core.ui.designsystem.appbar.searchappbar.SearchAppBar
import com.example.motsi.core.ui.theming.Tokens
import com.example.motsi.core.ui.utils.LifecycleEffect
import com.example.motsi.feature.search.impl.models.domain.SearchScreenModel
import com.example.motsi.feature.search.impl.presentation.SearchClickHandler
import com.example.motsi.feature.search.impl.presentation.SearchViewModel
import kotlin.math.sqrt
import kotlin.random.Random

@Composable
internal fun SearchScreen(
    viewModel: SearchViewModel,
    hideSplashScreen: () -> Unit,
    clickHandler: SearchClickHandler,
    bottomNavBar: @Composable () -> Unit,
    searchQuery: String
) {
    val screenState by viewModel.screenState.collectAsState()
    LifecycleEffect(onCreate = { viewModel.initViewModel() })
    when (val state = screenState.loadingState) {
        is LoadingState.Loading -> {
            //            Loading()
        }

        is LoadingState.Success -> {
            hideSplashScreen.invoke()
            Success(
                model = state.data,
                viewModel = viewModel,
                bottomNavBar = bottomNavBar,
                clickHandler = clickHandler,
                searchQuery = searchQuery
            )
        }

        is LoadingState.Error -> {
//            Error()
        }

        else -> {
//            nothing
        }
    }
}


@Composable
private fun Success(
    model: SearchScreenModel,
    viewModel: SearchViewModel,
    bottomNavBar: @Composable () -> Unit,
    clickHandler: SearchClickHandler,
    searchQuery: String
) {
    val navController = LocalAppNavController.current

    Scaffold(
        modifier = Modifier,
        topBar = {
            SearchAppBar(
                onSearchFieldClick = {
                    clickHandler.onSearchFieldClick(
                        navController = navController,
                        searchQuery = searchQuery,
                        appBarHint = model.appbar.titleSearchField,
                    )
                },
                hint = model.appbar.titleSearchField,
                textSearch = searchQuery,
                onTextChange = {},
                isEnabled = false
            )
        },
        bottomBar = bottomNavBar
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            ListActivity(viewModel, clickHandler)
        }
    }

}

@Composable
private fun ListActivity(viewModel: SearchViewModel, clickHandler: SearchClickHandler,){
    val listActivityState by viewModel.listActivityState.collectAsState()
    val navController = LocalAppNavController.current

    val imageTestList24 = listOf(
        "https://i.pinimg.com/originals/f3/61/95/f361952c68bce101dc70b0f8b562261b.jpg",
        "https://avatars.mds.yandex.net/i?id=ec80c6319f7175571da7e6254d48a0d5_l-5523828-images-thumbs&n=13",
        "https://30.img.avito.st/image/1/1.wSbO7La1bc-0RaPHwNPaC7pNb89wR6PHtEVvzQ.5Uh8dCPmjlr9y-7hgAlwovr96SI8HFejuCXMPz2wfMo",
        "https://masterpiecer-images.s3.yandex.net/5fdc38a0a98a51d:upscaled",
        "https://avatars.mds.yandex.net/i?id=5c2e37a6223644551a546cca13f23796_l-9181224-images-thumbs&n=13",
        "https://i.pinimg.com/originals/f4/c0/ab/f4c0abf084b5705ebacb4afa83f08d1c.jpg",
        "https://masterpiecer-images.s3.yandex.net/68c2a64b706f11ee94492ab2a9c6ab46:upscaled",
        "https://s00.yaplakal.com/pics/pics_preview/5/5/4/18242455.jpg",
        "https://avatars.mds.yandex.net/i?id=0f9cb718367f4e8617be4f4a346ad451_l-5232689-images-thumbs&n=13",
        "https://avatars.mds.yandex.net/i?id=37ac64235c1ae9c8b6312e52579a86c865d9dbea-9895871-images-thumbs&ref=rim&n=33&w=426&h=250",
        "https://i.pinimg.com/originals/f1/25/bd/f125bd952c23454fc282730871f90948.jpg",
        "https://avatars.mds.yandex.net/i?id=a2ee534f3c28fc183b600c1199f500b7-5595218-images-thumbs&ref=rim&n=13&w=1080&h=1350",
        "https://i.pinimg.com/originals/f3/61/95/f361952c68bce101dc70b0f8b562261b.jpg",
        "https://avatars.mds.yandex.net/i?id=ec80c6319f7175571da7e6254d48a0d5_l-5523828-images-thumbs&n=13",
        "https://30.img.avito.st/image/1/1.wSbO7La1bc-0RaPHwNPaC7pNb89wR6PHtEVvzQ.5Uh8dCPmjlr9y-7hgAlwovr96SI8HFejuCXMPz2wfMo",
        "https://masterpiecer-images.s3.yandex.net/5fdc38a0a98a51d:upscaled",
        "https://avatars.mds.yandex.net/i?id=5c2e37a6223644551a546cca13f23796_l-9181224-images-thumbs&n=13",
        "https://i.pinimg.com/originals/f4/c0/ab/f4c0abf084b5705ebacb4afa83f08d1c.jpg",
        "https://masterpiecer-images.s3.yandex.net/68c2a64b706f11ee94492ab2a9c6ab46:upscaled",
        "https://s00.yaplakal.com/pics/pics_preview/5/5/4/18242455.jpg",
        "https://avatars.mds.yandex.net/i?id=0f9cb718367f4e8617be4f4a346ad451_l-5232689-images-thumbs&n=13",
        "https://avatars.mds.yandex.net/i?id=37ac64235c1ae9c8b6312e52579a86c865d9dbea-9895871-images-thumbs&ref=rim&n=33&w=426&h=250",
        "https://i.pinimg.com/originals/f1/25/bd/f125bd952c23454fc282730871f90948.jpg",
        "https://avatars.mds.yandex.net/i?id=a2ee534f3c28fc183b600c1199f500b7-5595218-images-thumbs&ref=rim&n=13&w=1080&h=1350",
    )

    val imageTestList50 = listOf(
        "https://i.pinimg.com/originals/f3/61/95/f361952c68bce101dc70b0f8b562261b.jpg",
        "https://avatars.mds.yandex.net/i?id=ec80c6319f7175571da7e6254d48a0d5_l-5523828-images-thumbs&n=13",
        "https://30.img.avito.st/image/1/1.wSbO7La1bc-0RaPHwNPaC7pNb89wR6PHtEVvzQ.5Uh8dCPmjlr9y-7hgAlwovr96SI8HFejuCXMPz2wfMo",
        "https://masterpiecer-images.s3.yandex.net/5fdc38a0a98a51d:upscaled",
        "https://avatars.mds.yandex.net/i?id=5c2e37a6223644551a546cca13f23796_l-9181224-images-thumbs&n=13",
        "https://i.pinimg.com/originals/f4/c0/ab/f4c0abf084b5705ebacb4afa83f08d1c.jpg",
        "https://masterpiecer-images.s3.yandex.net/68c2a64b706f11ee94492ab2a9c6ab46:upscaled",
        "https://s00.yaplakal.com/pics/pics_preview/5/5/4/18242455.jpg",
        "https://avatars.mds.yandex.net/i?id=0f9cb718367f4e8617be4f4a346ad451_l-5232689-images-thumbs&n=13",
        "https://avatars.mds.yandex.net/i?id=37ac64235c1ae9c8b6312e52579a86c865d9dbea-9895871-images-thumbs&ref=rim&n=33&w=426&h=250",
        "https://i.pinimg.com/originals/f1/25/bd/f125bd952c23454fc282730871f90948.jpg",
        "https://avatars.mds.yandex.net/i?id=a2ee534f3c28fc183b600c1199f500b7-5595218-images-thumbs&ref=rim&n=13&w=1080&h=1350",
        "https://i.pinimg.com/originals/f3/61/95/f361952c68bce101dc70b0f8b562261b.jpg",
        "https://avatars.mds.yandex.net/i?id=ec80c6319f7175571da7e6254d48a0d5_l-5523828-images-thumbs&n=13",
        "https://30.img.avito.st/image/1/1.wSbO7La1bc-0RaPHwNPaC7pNb89wR6PHtEVvzQ.5Uh8dCPmjlr9y-7hgAlwovr96SI8HFejuCXMPz2wfMo",
        "https://masterpiecer-images.s3.yandex.net/5fdc38a0a98a51d:upscaled",
        "https://avatars.mds.yandex.net/i?id=5c2e37a6223644551a546cca13f23796_l-9181224-images-thumbs&n=13",
        "https://i.pinimg.com/originals/f4/c0/ab/f4c0abf084b5705ebacb4afa83f08d1c.jpg",
        "https://masterpiecer-images.s3.yandex.net/68c2a64b706f11ee94492ab2a9c6ab46:upscaled",
        "https://s00.yaplakal.com/pics/pics_preview/5/5/4/18242455.jpg",
        "https://avatars.mds.yandex.net/i?id=0f9cb718367f4e8617be4f4a346ad451_l-5232689-images-thumbs&n=13",
        "https://avatars.mds.yandex.net/i?id=37ac64235c1ae9c8b6312e52579a86c865d9dbea-9895871-images-thumbs&ref=rim&n=33&w=426&h=250",
        "https://i.pinimg.com/originals/f1/25/bd/f125bd952c23454fc282730871f90948.jpg",
        "https://avatars.mds.yandex.net/i?id=a2ee534f3c28fc183b600c1199f500b7-5595218-images-thumbs&ref=rim&n=13&w=1080&h=1350",
        "https://i.pinimg.com/originals/f3/61/95/f361952c68bce101dc70b0f8b562261b.jpg",
        "https://avatars.mds.yandex.net/i?id=ec80c6319f7175571da7e6254d48a0d5_l-5523828-images-thumbs&n=13",
        "https://30.img.avito.st/image/1/1.wSbO7La1bc-0RaPHwNPaC7pNb89wR6PHtEVvzQ.5Uh8dCPmjlr9y-7hgAlwovr96SI8HFejuCXMPz2wfMo",
        "https://masterpiecer-images.s3.yandex.net/5fdc38a0a98a51d:upscaled",
        "https://avatars.mds.yandex.net/i?id=5c2e37a6223644551a546cca13f23796_l-9181224-images-thumbs&n=13",
        "https://i.pinimg.com/originals/f4/c0/ab/f4c0abf084b5705ebacb4afa83f08d1c.jpg",
        "https://masterpiecer-images.s3.yandex.net/68c2a64b706f11ee94492ab2a9c6ab46:upscaled",
        "https://s00.yaplakal.com/pics/pics_preview/5/5/4/18242455.jpg",
        "https://avatars.mds.yandex.net/i?id=0f9cb718367f4e8617be4f4a346ad451_l-5232689-images-thumbs&n=13",
        "https://avatars.mds.yandex.net/i?id=37ac64235c1ae9c8b6312e52579a86c865d9dbea-9895871-images-thumbs&ref=rim&n=33&w=426&h=250",
        "https://i.pinimg.com/originals/f1/25/bd/f125bd952c23454fc282730871f90948.jpg",
        "https://avatars.mds.yandex.net/i?id=a2ee534f3c28fc183b600c1199f500b7-5595218-images-thumbs&ref=rim&n=13&w=1080&h=1350",
        "https://i.pinimg.com/originals/f3/61/95/f361952c68bce101dc70b0f8b562261b.jpg",
        "https://avatars.mds.yandex.net/i?id=ec80c6319f7175571da7e6254d48a0d5_l-5523828-images-thumbs&n=13",
        "https://30.img.avito.st/image/1/1.wSbO7La1bc-0RaPHwNPaC7pNb89wR6PHtEVvzQ.5Uh8dCPmjlr9y-7hgAlwovr96SI8HFejuCXMPz2wfMo",
        "https://masterpiecer-images.s3.yandex.net/5fdc38a0a98a51d:upscaled",
        "https://avatars.mds.yandex.net/i?id=5c2e37a6223644551a546cca13f23796_l-9181224-images-thumbs&n=13",
        "https://i.pinimg.com/originals/f4/c0/ab/f4c0abf084b5705ebacb4afa83f08d1c.jpg",
        "https://masterpiecer-images.s3.yandex.net/68c2a64b706f11ee94492ab2a9c6ab46:upscaled",
        "https://s00.yaplakal.com/pics/pics_preview/5/5/4/18242455.jpg",
        "https://avatars.mds.yandex.net/i?id=0f9cb718367f4e8617be4f4a346ad451_l-5232689-images-thumbs&n=13",
        "https://avatars.mds.yandex.net/i?id=37ac64235c1ae9c8b6312e52579a86c865d9dbea-9895871-images-thumbs&ref=rim&n=33&w=426&h=250",
        "https://i.pinimg.com/originals/f1/25/bd/f125bd952c23454fc282730871f90948.jpg",
        "https://avatars.mds.yandex.net/i?id=a2ee534f3c28fc183b600c1199f500b7-5595218-images-thumbs&ref=rim&n=13&w=1080&h=1350",
        "https://i.pinimg.com/originals/f1/25/bd/f125bd952c23454fc282730871f90948.jpg",
        "https://avatars.mds.yandex.net/i?id=a2ee534f3c28fc183b600c1199f500b7-5595218-images-thumbs&ref=rim&n=13&w=1080&h=1350",

        )

    val imageTestList100 = listOf(
        "https://i.pinimg.com/originals/f3/61/95/f361952c68bce101dc70b0f8b562261b.jpg",
        "https://avatars.mds.yandex.net/i?id=ec80c6319f7175571da7e6254d48a0d5_l-5523828-images-thumbs&n=13",
        "https://30.img.avito.st/image/1/1.wSbO7La1bc-0RaPHwNPaC7pNb89wR6PHtEVvzQ.5Uh8dCPmjlr9y-7hgAlwovr96SI8HFejuCXMPz2wfMo",
        "https://masterpiecer-images.s3.yandex.net/5fdc38a0a98a51d:upscaled",
        "https://avatars.mds.yandex.net/i?id=5c2e37a6223644551a546cca13f23796_l-9181224-images-thumbs&n=13",
        "https://i.pinimg.com/originals/f4/c0/ab/f4c0abf084b5705ebacb4afa83f08d1c.jpg",
        "https://masterpiecer-images.s3.yandex.net/68c2a64b706f11ee94492ab2a9c6ab46:upscaled",
        "https://s00.yaplakal.com/pics/pics_preview/5/5/4/18242455.jpg",
        "https://avatars.mds.yandex.net/i?id=0f9cb718367f4e8617be4f4a346ad451_l-5232689-images-thumbs&n=13",
        "https://avatars.mds.yandex.net/i?id=37ac64235c1ae9c8b6312e52579a86c865d9dbea-9895871-images-thumbs&ref=rim&n=33&w=426&h=250",
        "https://i.pinimg.com/originals/f1/25/bd/f125bd952c23454fc282730871f90948.jpg",
        "https://avatars.mds.yandex.net/i?id=a2ee534f3c28fc183b600c1199f500b7-5595218-images-thumbs&ref=rim&n=13&w=1080&h=1350",
        "https://i.pinimg.com/originals/f3/61/95/f361952c68bce101dc70b0f8b562261b.jpg",
        "https://avatars.mds.yandex.net/i?id=ec80c6319f7175571da7e6254d48a0d5_l-5523828-images-thumbs&n=13",
        "https://30.img.avito.st/image/1/1.wSbO7La1bc-0RaPHwNPaC7pNb89wR6PHtEVvzQ.5Uh8dCPmjlr9y-7hgAlwovr96SI8HFejuCXMPz2wfMo",
        "https://masterpiecer-images.s3.yandex.net/5fdc38a0a98a51d:upscaled",
        "https://avatars.mds.yandex.net/i?id=5c2e37a6223644551a546cca13f23796_l-9181224-images-thumbs&n=13",
        "https://i.pinimg.com/originals/f4/c0/ab/f4c0abf084b5705ebacb4afa83f08d1c.jpg",
        "https://masterpiecer-images.s3.yandex.net/68c2a64b706f11ee94492ab2a9c6ab46:upscaled",
        "https://s00.yaplakal.com/pics/pics_preview/5/5/4/18242455.jpg",
        "https://avatars.mds.yandex.net/i?id=0f9cb718367f4e8617be4f4a346ad451_l-5232689-images-thumbs&n=13",
        "https://avatars.mds.yandex.net/i?id=37ac64235c1ae9c8b6312e52579a86c865d9dbea-9895871-images-thumbs&ref=rim&n=33&w=426&h=250",
        "https://i.pinimg.com/originals/f1/25/bd/f125bd952c23454fc282730871f90948.jpg",
        "https://avatars.mds.yandex.net/i?id=a2ee534f3c28fc183b600c1199f500b7-5595218-images-thumbs&ref=rim&n=13&w=1080&h=1350",
        "https://i.pinimg.com/originals/f3/61/95/f361952c68bce101dc70b0f8b562261b.jpg",
        "https://avatars.mds.yandex.net/i?id=ec80c6319f7175571da7e6254d48a0d5_l-5523828-images-thumbs&n=13",
        "https://30.img.avito.st/image/1/1.wSbO7La1bc-0RaPHwNPaC7pNb89wR6PHtEVvzQ.5Uh8dCPmjlr9y-7hgAlwovr96SI8HFejuCXMPz2wfMo",
        "https://masterpiecer-images.s3.yandex.net/5fdc38a0a98a51d:upscaled",
        "https://avatars.mds.yandex.net/i?id=5c2e37a6223644551a546cca13f23796_l-9181224-images-thumbs&n=13",
        "https://i.pinimg.com/originals/f4/c0/ab/f4c0abf084b5705ebacb4afa83f08d1c.jpg",
        "https://masterpiecer-images.s3.yandex.net/68c2a64b706f11ee94492ab2a9c6ab46:upscaled",
        "https://s00.yaplakal.com/pics/pics_preview/5/5/4/18242455.jpg",
        "https://avatars.mds.yandex.net/i?id=0f9cb718367f4e8617be4f4a346ad451_l-5232689-images-thumbs&n=13",
        "https://avatars.mds.yandex.net/i?id=37ac64235c1ae9c8b6312e52579a86c865d9dbea-9895871-images-thumbs&ref=rim&n=33&w=426&h=250",
        "https://i.pinimg.com/originals/f1/25/bd/f125bd952c23454fc282730871f90948.jpg",
        "https://avatars.mds.yandex.net/i?id=a2ee534f3c28fc183b600c1199f500b7-5595218-images-thumbs&ref=rim&n=13&w=1080&h=1350",
        "https://i.pinimg.com/originals/f3/61/95/f361952c68bce101dc70b0f8b562261b.jpg",
        "https://avatars.mds.yandex.net/i?id=ec80c6319f7175571da7e6254d48a0d5_l-5523828-images-thumbs&n=13",
        "https://30.img.avito.st/image/1/1.wSbO7La1bc-0RaPHwNPaC7pNb89wR6PHtEVvzQ.5Uh8dCPmjlr9y-7hgAlwovr96SI8HFejuCXMPz2wfMo",
        "https://masterpiecer-images.s3.yandex.net/5fdc38a0a98a51d:upscaled",
        "https://avatars.mds.yandex.net/i?id=5c2e37a6223644551a546cca13f23796_l-9181224-images-thumbs&n=13",
        "https://i.pinimg.com/originals/f4/c0/ab/f4c0abf084b5705ebacb4afa83f08d1c.jpg",
        "https://masterpiecer-images.s3.yandex.net/68c2a64b706f11ee94492ab2a9c6ab46:upscaled",
        "https://s00.yaplakal.com/pics/pics_preview/5/5/4/18242455.jpg",
        "https://avatars.mds.yandex.net/i?id=0f9cb718367f4e8617be4f4a346ad451_l-5232689-images-thumbs&n=13",
        "https://avatars.mds.yandex.net/i?id=37ac64235c1ae9c8b6312e52579a86c865d9dbea-9895871-images-thumbs&ref=rim&n=33&w=426&h=250",
        "https://i.pinimg.com/originals/f1/25/bd/f125bd952c23454fc282730871f90948.jpg",
        "https://avatars.mds.yandex.net/i?id=a2ee534f3c28fc183b600c1199f500b7-5595218-images-thumbs&ref=rim&n=13&w=1080&h=1350",
        "https://i.pinimg.com/originals/f1/25/bd/f125bd952c23454fc282730871f90948.jpg",
        "https://avatars.mds.yandex.net/i?id=a2ee534f3c28fc183b600c1199f500b7-5595218-images-thumbs&ref=rim&n=13&w=1080&h=1350",
        "https://i.pinimg.com/originals/f3/61/95/f361952c68bce101dc70b0f8b562261b.jpg",
        "https://avatars.mds.yandex.net/i?id=ec80c6319f7175571da7e6254d48a0d5_l-5523828-images-thumbs&n=13",
        "https://30.img.avito.st/image/1/1.wSbO7La1bc-0RaPHwNPaC7pNb89wR6PHtEVvzQ.5Uh8dCPmjlr9y-7hgAlwovr96SI8HFejuCXMPz2wfMo",
        "https://masterpiecer-images.s3.yandex.net/5fdc38a0a98a51d:upscaled",
        "https://avatars.mds.yandex.net/i?id=5c2e37a6223644551a546cca13f23796_l-9181224-images-thumbs&n=13",
        "https://i.pinimg.com/originals/f4/c0/ab/f4c0abf084b5705ebacb4afa83f08d1c.jpg",
        "https://masterpiecer-images.s3.yandex.net/68c2a64b706f11ee94492ab2a9c6ab46:upscaled",
        "https://s00.yaplakal.com/pics/pics_preview/5/5/4/18242455.jpg",
        "https://avatars.mds.yandex.net/i?id=0f9cb718367f4e8617be4f4a346ad451_l-5232689-images-thumbs&n=13",
        "https://avatars.mds.yandex.net/i?id=37ac64235c1ae9c8b6312e52579a86c865d9dbea-9895871-images-thumbs&ref=rim&n=33&w=426&h=250",
        "https://i.pinimg.com/originals/f1/25/bd/f125bd952c23454fc282730871f90948.jpg",
        "https://avatars.mds.yandex.net/i?id=a2ee534f3c28fc183b600c1199f500b7-5595218-images-thumbs&ref=rim&n=13&w=1080&h=1350",
        "https://i.pinimg.com/originals/f3/61/95/f361952c68bce101dc70b0f8b562261b.jpg",
        "https://avatars.mds.yandex.net/i?id=ec80c6319f7175571da7e6254d48a0d5_l-5523828-images-thumbs&n=13",
        "https://30.img.avito.st/image/1/1.wSbO7La1bc-0RaPHwNPaC7pNb89wR6PHtEVvzQ.5Uh8dCPmjlr9y-7hgAlwovr96SI8HFejuCXMPz2wfMo",
        "https://masterpiecer-images.s3.yandex.net/5fdc38a0a98a51d:upscaled",
        "https://avatars.mds.yandex.net/i?id=5c2e37a6223644551a546cca13f23796_l-9181224-images-thumbs&n=13",
        "https://i.pinimg.com/originals/f4/c0/ab/f4c0abf084b5705ebacb4afa83f08d1c.jpg",
        "https://masterpiecer-images.s3.yandex.net/68c2a64b706f11ee94492ab2a9c6ab46:upscaled",
        "https://s00.yaplakal.com/pics/pics_preview/5/5/4/18242455.jpg",
        "https://avatars.mds.yandex.net/i?id=0f9cb718367f4e8617be4f4a346ad451_l-5232689-images-thumbs&n=13",
        "https://avatars.mds.yandex.net/i?id=37ac64235c1ae9c8b6312e52579a86c865d9dbea-9895871-images-thumbs&ref=rim&n=33&w=426&h=250",
        "https://i.pinimg.com/originals/f1/25/bd/f125bd952c23454fc282730871f90948.jpg",
        "https://avatars.mds.yandex.net/i?id=a2ee534f3c28fc183b600c1199f500b7-5595218-images-thumbs&ref=rim&n=13&w=1080&h=1350",
        "https://i.pinimg.com/originals/f3/61/95/f361952c68bce101dc70b0f8b562261b.jpg",
        "https://avatars.mds.yandex.net/i?id=ec80c6319f7175571da7e6254d48a0d5_l-5523828-images-thumbs&n=13",
        "https://30.img.avito.st/image/1/1.wSbO7La1bc-0RaPHwNPaC7pNb89wR6PHtEVvzQ.5Uh8dCPmjlr9y-7hgAlwovr96SI8HFejuCXMPz2wfMo",
        "https://masterpiecer-images.s3.yandex.net/5fdc38a0a98a51d:upscaled",
        "https://avatars.mds.yandex.net/i?id=5c2e37a6223644551a546cca13f23796_l-9181224-images-thumbs&n=13",
        "https://i.pinimg.com/originals/f4/c0/ab/f4c0abf084b5705ebacb4afa83f08d1c.jpg",
        "https://masterpiecer-images.s3.yandex.net/68c2a64b706f11ee94492ab2a9c6ab46:upscaled",
        "https://s00.yaplakal.com/pics/pics_preview/5/5/4/18242455.jpg",
        "https://avatars.mds.yandex.net/i?id=0f9cb718367f4e8617be4f4a346ad451_l-5232689-images-thumbs&n=13",
        "https://avatars.mds.yandex.net/i?id=37ac64235c1ae9c8b6312e52579a86c865d9dbea-9895871-images-thumbs&ref=rim&n=33&w=426&h=250",
        "https://i.pinimg.com/originals/f1/25/bd/f125bd952c23454fc282730871f90948.jpg",
        "https://avatars.mds.yandex.net/i?id=a2ee534f3c28fc183b600c1199f500b7-5595218-images-thumbs&ref=rim&n=13&w=1080&h=1350",
        "https://i.pinimg.com/originals/f3/61/95/f361952c68bce101dc70b0f8b562261b.jpg",
        "https://avatars.mds.yandex.net/i?id=ec80c6319f7175571da7e6254d48a0d5_l-5523828-images-thumbs&n=13",
        "https://30.img.avito.st/image/1/1.wSbO7La1bc-0RaPHwNPaC7pNb89wR6PHtEVvzQ.5Uh8dCPmjlr9y-7hgAlwovr96SI8HFejuCXMPz2wfMo",
        "https://masterpiecer-images.s3.yandex.net/5fdc38a0a98a51d:upscaled",
        "https://avatars.mds.yandex.net/i?id=5c2e37a6223644551a546cca13f23796_l-9181224-images-thumbs&n=13",
        "https://i.pinimg.com/originals/f4/c0/ab/f4c0abf084b5705ebacb4afa83f08d1c.jpg",
        "https://masterpiecer-images.s3.yandex.net/68c2a64b706f11ee94492ab2a9c6ab46:upscaled",
        "https://s00.yaplakal.com/pics/pics_preview/5/5/4/18242455.jpg",
        "https://avatars.mds.yandex.net/i?id=0f9cb718367f4e8617be4f4a346ad451_l-5232689-images-thumbs&n=13",
        "https://avatars.mds.yandex.net/i?id=37ac64235c1ae9c8b6312e52579a86c865d9dbea-9895871-images-thumbs&ref=rim&n=33&w=426&h=250",
        "https://i.pinimg.com/originals/f1/25/bd/f125bd952c23454fc282730871f90948.jpg",
        "https://avatars.mds.yandex.net/i?id=a2ee534f3c28fc183b600c1199f500b7-5595218-images-thumbs&ref=rim&n=13&w=1080&h=1350",
        "https://i.pinimg.com/originals/f1/25/bd/f125bd952c23454fc282730871f90948.jpg",
        "https://avatars.mds.yandex.net/i?id=a2ee534f3c28fc183b600c1199f500b7-5595218-images-thumbs&ref=rim&n=13&w=1080&h=1350",

        )

    val imageTestList5 = listOf(
        "https://i.pinimg.com/originals/f3/61/95/f361952c68bce101dc70b0f8b562261b.jpg",
        "https://avatars.mds.yandex.net/i?id=ec80c6319f7175571da7e6254d48a0d5_l-5523828-images-thumbs&n=13",
        "https://30.img.avito.st/image/1/1.wSbO7La1bc-0RaPHwNPaC7pNb89wR6PHtEVvzQ.5Uh8dCPmjlr9y-7hgAlwovr96SI8HFejuCXMPz2wfMo",
        "https://masterpiecer-images.s3.yandex.net/5fdc38a0a98a51d:upscaled",
        "https://avatars.mds.yandex.net/i?id=5c2e37a6223644551a546cca13f23796_l-9181224-images-thumbs&n=13",
    )

    val imageTestList10 = listOf(
        "https://i.pinimg.com/originals/f3/61/95/f361952c68bce101dc70b0f8b562261b.jpg",
        "https://avatars.mds.yandex.net/i?id=ec80c6319f7175571da7e6254d48a0d5_l-5523828-images-thumbs&n=13",
        "https://30.img.avito.st/image/1/1.wSbO7La1bc-0RaPHwNPaC7pNb89wR6PHtEVvzQ.5Uh8dCPmjlr9y-7hgAlwovr96SI8HFejuCXMPz2wfMo",
        "https://masterpiecer-images.s3.yandex.net/5fdc38a0a98a51d:upscaled",
        "https://avatars.mds.yandex.net/i?id=5c2e37a6223644551a546cca13f23796_l-9181224-images-thumbs&n=13",
        "https://i.pinimg.com/originals/f4/c0/ab/f4c0abf084b5705ebacb4afa83f08d1c.jpg",
        "https://masterpiecer-images.s3.yandex.net/68c2a64b706f11ee94492ab2a9c6ab46:upscaled",
        "https://s00.yaplakal.com/pics/pics_preview/5/5/4/18242455.jpg",
        "https://avatars.mds.yandex.net/i?id=0f9cb718367f4e8617be4f4a346ad451_l-5232689-images-thumbs&n=13",
        "https://avatars.mds.yandex.net/i?id=37ac64235c1ae9c8b6312e52579a86c865d9dbea-9895871-images-thumbs&ref=rim&n=33&w=426&h=250",
    )

    when (val state = listActivityState.loadingState) {
        is LoadingState.Loading -> {
//            Loading() шимиризация
        }

        is LoadingState.Success -> {
            val navController = LocalAppNavController.current
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(16.dp)
                    .clickable(onClick = { clickHandler.onActivityClick(navController) }),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxSize(), text = " детальная инфа по активности "
                )
            }
        }

        is LoadingState.Error -> {
            Text(
                modifier = Modifier
                    .fillMaxSize(), text = state.error.message
            )
//            Error() загрузка из кэша

            UserActivityCircle(
                clickHandler = clickHandler,
                userActivityList = imageTestList24
            )
        }

        else -> {
//            nothing
        }
    }
}

@SuppressLint("RestrictedApi")
@Composable
internal fun UserActivityCircle(
    clickHandler: SearchClickHandler,
    userActivityList: List<String>
) {
    val navController = LocalAppNavController.current

    // Размеры экрана
    val density = LocalDensity.current
    val boxWidthPx =
        with(density) { (LocalConfiguration.current.screenWidthDp.dp - 40.dp).toPx() }
    val boxHeightPx = with(density) { 400.dp.toPx() }

    // Храним информацию о занятых областях
    val occupiedAreas = remember { mutableListOf<Rect>() }

    // Рассчитываем базовый размер на основе площади и количества иконок
    val totalArea = boxWidthPx * boxHeightPx
    val avgArea = totalArea / userActivityList.size.coerceAtLeast(1)
    val baseSize = sqrt(avgArea * 0.4f) // 40% от средней площади

    Column(
        modifier = Modifier
            .padding(20.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .background(
                    color = Tokens.BackgroundSecondary.getColor(),
                    shape = RoundedCornerShape(12.dp)
                )
        ) {
            Box(
                modifier = Modifier
                    .padding(end = 8.dp, bottom = 8.dp)
                    .background(
                        color = Tokens.IconPrimary.getColor(),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .size(width = 80.dp, height = 50.dp)
                    .align(Alignment.BottomEnd),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_downhill_skiing_38),
                    contentDescription = "icon activity",
                    tint = Tokens.IconSecondary.getColor()
                )
            }

            // Размеры фиксированной иконки
            val fixedIconWidth = with(density) { 104.dp.toPx() } // С учетом отступа 8dp
            val fixedIconHeight = with(density) { 74.dp.toPx() } // С учетом отступа 8dp

            // Добавляем занятую область иконки
            occupiedAreas.add(
                Rect(
                    left = boxWidthPx - fixedIconWidth,
                    top = boxHeightPx - fixedIconHeight,
                    right = boxWidthPx,
                    bottom = boxHeightPx
                )
            )

            //Отрисовка динамических иконок
            userActivityList.forEach { imageUrl ->
                val randomIconSize = with(density) {
                    (baseSize * (0.8f + Random.nextFloat() * 0.5f)).toDp().toPx()
                }
                val reducedRadius = randomIconSize / 2 * 0.8f // Наложение 20%

                // Функция проверки попадания в запрещенные зоны
                fun isPositionValid(circleCenter: Offset): Boolean {
                    val circleRect = Rect(
                        left = circleCenter.x - reducedRadius,
                        top = circleCenter.y - reducedRadius,
                        right = circleCenter.x + reducedRadius,
                        bottom = circleCenter.y + reducedRadius
                    )

                    return occupiedAreas.all { occupiedRect ->
                        !occupiedRect.overlaps(circleRect)
                    }
                }

                // Генерируем случайные позиции
                var center: Offset
                var attempts = 0

                do {
                    center = Offset(
                        x = reducedRadius + Random.nextFloat() * (boxWidthPx - randomIconSize),
                        y = reducedRadius + Random.nextFloat() * (boxHeightPx - randomIconSize)
                    )
                    attempts++
                } while (!isPositionValid(center) && attempts < 100)

                // Добавляем в список занятых областей
                val newCircleRect = Rect(
                    left = center.x - reducedRadius,
                    top = center.y - reducedRadius,
                    right = center.x + reducedRadius,
                    bottom = center.y + reducedRadius
                )
                occupiedAreas.add(newCircleRect)

                // Координаты для верхнего левого угла
                val randomX = center.x - reducedRadius
                val randomY = center.y - reducedRadius

                Box(
                    modifier = Modifier
                        .layout { measurable, constraints ->
                            val placeable = measurable.measure(constraints)
                            layout(constraints.maxWidth, constraints.maxHeight) {
                                placeable.placeRelative(
                                    x = randomX.toInt(), y = randomY.toInt()
                                )
                            }
                        }
                        .size(with(density) { randomIconSize.toDp() })
                ) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable(onClick = {
                                clickHandler.onActivityClick(navController)
                            })
                            .border(
                                width = 1.dp,
                                color = Tokens.IconPrimary.getColor(),
                                shape = CircleShape
                            )
                    )
                }
            }
        }
    }
}