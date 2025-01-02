package com.isarthaksharma.splitespree.pages

import kotlinx.serialization.Serializable

sealed interface PageNavigation {

    @Serializable
    data object LoginPage: PageNavigation

    @Serializable
    data object SignUpPage: PageNavigation

}